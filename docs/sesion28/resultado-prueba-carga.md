# Reporte técnico - Sesión 28

## 1. Escenario

- **Endpoint**: `GET http://localhost:8080/carga/productos`
- **Equipo y sistema operativo**: AMD64 / Windows 11 Home Single Language (NT 10.0.26200)
- **Perfil/configuración**: Java 17, Spring Boot, `app.carga.delay-ms` (120 → 20)
- **Etapas de carga**:

| Etapa | Duración | VUs objetivo |
|-------|----------|-------------|
| Ramp-up | 10 s | 0 → 10 |
| Estable | 20 s | 10 |
| Ramp-up | 10 s | 10 → 30 |
| Estable | 20 s | 30 |
| Ramp-down | 10 s | 30 → 0 |

- **Umbrales**: `http_req_failed < 1%`, `http_req_duration p95 < 500 ms`, `http_req_duration p99 < 800 ms`, `checks > 99%`
- **Commit evaluado**: `d02cb2e` - Agrega pruebas de rendimiento frontend y backend

---

## 2. Resultados

| Versión | p50 | p95 | p99 | RPS | Errores | CPU (delta) | Memoria |
|---|---:|---:|---:|---:|---:|---:|---:|
| Base (delay=120ms) | 121.74 ms | 122.97 ms | < 800 ms | 15.34 | 0.00% | +1.37 s | 75 → 49 MB |
| Optimizada (delay=20ms) | 121.81 ms | 123.38 ms | < 800 ms | 15.34 | 0.00% | +0.45 s | 293 → 288 MB |

### Evidencia de CPU y Memoria por etapa

**Base (delay=120ms)**:

| Etapa | VUs | CPU delta | Memoria (MB) |
|-------|-----|-----------|--------------|
| Baseline | 0 | - | 75.46 |
| Ramp-up 10 | 0→10 | +0.01 s | 75.48 |
| Estable 10 | 10 | +0.22 s | 59.02 |
| Ramp-up 30 | 10→30 | +0.08 s | 59.93 |
| Estable 30 | 30 | +0.71 s | 45.30 |
| Ramp-down | 30→0 | +0.29 s | 48.20 |

**Optimizada (delay=20ms)**:

| Etapa | VUs | CPU delta | Memoria (MB) |
|-------|-----|-----------|--------------|
| Baseline | 0 | - | 293.27 |
| Ramp-up 10 | 0→10 | +0.38 s | 293.38 |
| Estable 10 | 10 | +0.03 s | 290.16 |
| Ramp-up 30 | 10→30 | +0.00 s | 290.05 |
| Estable 30 | 30 | +0.03 s | 287.65 |
| Ramp-down | 30→0 | +0.00 s | 287.66 |

---

## 3. Hallazgos

- **Síntoma principal**: El tiempo de respuesta p95 se mantiene en ~122-123 ms tanto con delay=120ms como con delay=20ms. Reducir el delay no redujo la latencia percibida por el cliente.

- **Hipótesis de cuello de botella**: El cuello de botella **no es el delay artificial** configurado en `app.carga.delay-ms`. Existe un piso de latencia fijo de ~120 ms que no depende de ese parámetro. Probablemente se originen en: (1) latencia de acceso a base de datos, (2) overhead de inicialización de Spring Boot por request, o (3) procesamiento interno del controller/service.

- **Evidencia que la sustenta**:
  - p95 casi idéntico: 122.97 ms (base) vs 123.38 ms (optimizada) — diferencia de apenas 0.41 ms.
  - Duración mínima estable en ~120 ms en ambas ejecuciones, confirmando un piso fijo.
  - CPU fue 3x más baja en la versión optimizada (+0.45 s vs +1.37 s), pero la latencia no mejoró.
  - 0% de errores y 100% de checks en ambas ejecuciones, descartando saturación.
  - Memoria estable sin fugas en ambos casos.

---

## 4. Decisión

**¿Se cumplieron los thresholds?** Sí, en ambas ejecuciones:

| Threshold | Base | Optimizada |
|-----------|------|------------|
| `http_req_failed < 1%` | 0.00% ✅ | 0.00% ✅ |
| `p95 < 500 ms` | 122.97 ms ✅ | 123.38 ms ✅ |
| `p99 < 800 ms` | < 800 ms ✅ | < 800 ms ✅ |
| `checks > 99%` | 100% ✅ | 100% ✅ |

**Acción recomendada**: No se requiere optimización de performance para el endpoint `/carga/productos`. La aplicación soporta 30 VUs concurrentes sin degradación. Si se desea reducir la latencia por debajo de 120 ms, se debe investigar la capa de acceso a datos (consultas SQL, conexiones a BD) y el overhead de Spring Boot, ya que el delay simulado no es el factor determinante.

---

## 10. Ejercicio aplicado – Escala de 50 VUs

### Reto

Cree un segundo escenario de 50 usuarios virtuales durante 30 segundos. Compare con la carga de 30 VUs y determine si el sistema escala de forma aproximadamente proporcional o si aparece un punto de saturación.

### Configuración del test de 50 VUs

- **Script**: `scripts/s28-load-50vu.js`
- **Mismo endpoint**: `GET http://localhost:8080/carga/productos`
- **Misma pausa de usuario**: `sleep(1)`

| Etapa | Duración | VUs objetivo |
|-------|----------|-------------|
| Ramp-up | 10 s | 0 → 50 |
| Estable | 20 s | 50 |
| Ramp-down | 10 s | 50 → 0 |

- **Threshold adicional**: `http_req_duration p95 < 700 ms`

### Comparación: 30 VUs vs 50 VUs

| Métrica | 30 VUs | 50 VUs | Variación |
|---------|--------|--------|-----------|
| p50 (med) | 121.81 ms | 121.54 ms | -0.27 ms |
| p95 | 123.38 ms | 122.83 ms | -0.55 ms |
| Duración max | 157.09 ms | 135.40 ms | -21.69 ms |
| **RPS** | **15.34** | **33.66** | **+119% (x2.19)** |
| Requests totales | 1,085 | 1,367 | +282 |
| Tasa de error | 0.00% | 0.00% | Sin cambio |
| Checks | 100% | 100% | Sin cambio |

### CPU y Memoria – 50 VUs

| Etapa | VUs | CPU delta | Memoria (MB) |
|-------|-----|-----------|--------------|
| Baseline | 0 | - | 257.88 |
| Ramp-up 50 | 0→50 | +0.07 s | 105.92 |
| Estable 50 | 50 | +0.05 s | 105.93 |
| Ramp-down | 50→0 | +0.00 s | 105.98 |

### Análisis de escalabilidad

**El sistema escala de forma proporcional (e incluso mejor que proporcional):**

- Al pasar de 30 a 50 VUs (+67% más usuarios), el **RPS subió de 15.34 a 33.66** (+119%), es decir, el throughput creció **más que proporcionalmente** a la cantidad de VUs.
- El **p95 se mantuvo estable** en ~122 ms, sin degradación alguna. De hecho, el p95 mejoró ligeramente (123.38 → 122.83 ms).
- La **tasa de error se mantuvo en 0%**.
- El **threshold `p95 < 700 ms` se cumplió holgadamente** (122.83 ms).

**¿Por qué más VUs no siempre produce mayor throughput?**

En este caso SÍ produjo mayor throughput porque la app no está saturada. Sin embargo, en general, más VUs no siempre significan más RPS porque:

1. **Cola de conexiones**: Si el servidor tiene un pool de conexiones limitado (BD, threads), los requests adicionales entran en cola y esperan, aumentando latencia sin aumentar throughput.
2. **Contención de recursos**: CPU, memoria y disco compartidos causan overhead por contexto y locks cuando muchos hilos compiten.
3. **Ley de rendimientos decrecientes**: Una vez que todos los recursos están al 100%, agregar más VUs solo agrega latencia (cola), no capacidad.
4. **Timeouts y reintentos**: Si la latencia sube lo suficiente, los clientes hacen timeout y reintentos, consumiendo recursos sin completar requests.

**En este endpoint específico**, el cuello de botella es el piso de ~120 ms (probablemente BD), pero como ese piso es bajo y constante, el sistema puede manejar muchas conexiones concurrentes sin saturarse. Se necesitarían **muy más de 50 VUs** para alcanzar el límite de la capa de datos.
