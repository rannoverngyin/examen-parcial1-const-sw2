# Reporte técnico — Sesión 28

## Pruebas de carga y métricas

**Curso:** Construcción de Software II  
**Unidad:** Rendimiento y optimización  
**Estudiante:** Hidalgo Dairon  
**Repositorio:** `examen-parcial1-const-sw2`  
**Rama:** `feature/sesion28-pruebas-carga-hidalgo-dairon`  
**Sistema operativo:** Windows 11  
**Java:** 24.0.2  
**Maven:** 3.9.15  
**Herramienta de carga:** k6  
**Endpoint evaluado:** `GET /carga/productos`  

---

## 1. Objetivo

Diseñar y ejecutar una prueba de carga controlada sobre una API Spring Boot, interpretar latencia, throughput, errores y checks funcionales, y comparar una configuración base con una configuración optimizada mediante métricas reproducibles.

---

## 2. Escenario de prueba

La API simula la consulta de un catálogo de productos utilizado por un dashboard académico.

El servicio incluye un retraso configurable para representar una dependencia lenta sin utilizar servicios externos.

### Endpoint principal

```text
GET http://localhost:8080/carga/productos
```

### Endpoints auxiliares

```text
GET http://localhost:8080/carga/health
GET http://localhost:8080/carga/metricas
```

### Configuración base

```properties
app.carga.delay-ms=120
```

### Configuración optimizada

```properties
app.carga.delay-ms=20
```

---

## 3. Perfil de carga

### 3.1 Smoke test

| Parámetro | Valor |
|---|---:|
| Usuarios virtuales | 1 |
| Duración | 10 segundos |
| Pausa entre solicitudes | 1 segundo |

### 3.2 Prueba progresiva

| Etapa | Duración | Usuarios virtuales |
|---:|---:|---:|
| Ramp-up inicial | 10 segundos | 0 → 10 |
| Periodo estable | 20 segundos | 10 |
| Segundo ramp-up | 10 segundos | 10 → 30 |
| Periodo estable | 20 segundos | 30 |
| Ramp-down | 10 segundos | 30 → 0 |

---

## 4. Thresholds definidos

| Métrica | Criterio |
|---|---|
| `http_req_failed` | Menor al 1 % |
| `http_req_duration p(95)` | Menor a 500 ms |
| `http_req_duration p(99)` | Menor a 800 ms |
| `checks` | Mayor al 99 % |

Los thresholds fueron definidos antes de ejecutar la prueba para contar con criterios objetivos de aceptación.

---

## 5. Hipótesis inicial

La configuración base presentará una latencia mayor debido al retraso de 120 ms aplicado en cada solicitud.

Al reducir el retraso a 20 ms, se espera disminuir el p50, p95 y p99, mantener la tasa de errores en 0 %, conservar el 100 % de checks exitosos y aumentar el throughput.

---

## 6. Resultado del smoke test

| Métrica | Resultado | Umbral | Estado |
|---|---:|---:|---|
| Checks | 100.00 % | Mayor al 99 % | Cumplido |
| p95 | 141.66 ms | Menor a 500 ms | Cumplido |
| Errores HTTP | 0.00 % | Menor al 1 % | Cumplido |
| Solicitudes | 9 | — | Correcto |

El smoke test confirmó que la API, la URL, los checks y los thresholds funcionaban correctamente antes de ejecutar la carga progresiva.

---

## 7. Resultados de la prueba de carga

| Versión | p50 | p95 | p99 | RPS | Errores | Checks |
|---|---:|---:|---:|---:|---:|---:|
| Base | 125.55 ms | 138.07 ms | 141.08 ms | 15.37 | 0.00 % | 100.00 % |
| Optimizada | 21.57 ms | 25.04 ms | 34.61 ms | 16.80 | 0.00 % | 100.00 % |

---

## 8. Variación entre versiones

| Métrica | Base | Optimizada | Variación |
|---|---:|---:|---:|
| p50 | 125.55 ms | 21.57 ms | 82.82 % menor |
| p95 | 138.07 ms | 25.04 ms | 81.86 % menor |
| p99 | 141.08 ms | 34.61 ms | 75.47 % menor |
| RPS | 15.37 | 16.80 | 9.30 % mayor |
| Errores | 0.00 % | 0.00 % | Sin variación |
| Checks | 100.00 % | 100.00 % | Sin degradación |

---

## 9. Cálculo de mejora del p95

Se utilizó la siguiente fórmula:

```text
Mejora p95 (%) =
((p95_base - p95_optimizado) / p95_base) × 100
```

Sustituyendo los valores obtenidos:

```text
Mejora p95 (%) =
((138.07 - 25.04) / 138.07) × 100
```

Resultado:

```text
Mejora p95 = 81.86 %
```

La versión optimizada redujo el p95 en **81.86 %** respecto a la versión base.

---

## 10. Interpretación de métricas

### p50

La mediana disminuyó de **125.55 ms** a **21.57 ms**. Esto indica que el usuario típico recibió una respuesta considerablemente más rápida en la versión optimizada.

### p95

El p95 disminuyó de **138.07 ms** a **25.04 ms**. El 95 % de las solicitudes optimizadas terminó por debajo de 25.04 ms.

### p99

El p99 disminuyó de **141.08 ms** a **34.61 ms**, lo que evidencia una mejora también en los casos de mayor latencia.

### Throughput

El throughput aumentó de **15.37** a **16.80 solicitudes por segundo**, equivalente a una mejora aproximada de **9.30 %**.

### Errores y checks

Ambas versiones registraron:

- **0.00 % de errores HTTP**;
- **100.00 % de checks exitosos**.

La optimización mejoró la latencia y el throughput sin comprometer la funcionalidad.

---

## 11. Hallazgos

### Síntoma principal

La versión base presentó tiempos de respuesta cercanos al retraso configurado de 120 ms.

### Hipótesis de cuello de botella

El principal cuello de botella se encontraba en la espera artificial aplicada dentro del servicio.

### Evidencia

- p50 base de 125.55 ms.
- p95 base de 138.07 ms.
- p99 base de 141.08 ms.
- Reducción del p95 a 25.04 ms después de disminuir el retraso.
- Incremento del throughput de 15.37 a 16.80 RPS.
- Ausencia de errores en ambas ejecuciones.
- Checks funcionales al 100 %.

---

## 12. CPU y memoria

La guía solicita complementar las métricas de k6 con observación de CPU y memoria.

| Momento | CPU | Memoria |
|---|---:|---:|
| Inicio | Pendiente de registrar | Pendiente de registrar |
| 10 VUs | Pendiente de registrar | Pendiente de registrar |
| 30 VUs | Pendiente de registrar | Pendiente de registrar |

Estas métricas deben obtenerse mediante el Administrador de tareas, VisualVM, JFR o herramientas equivalentes. No se debe interpretar un pico aislado, sino la tendencia durante la carga.

---

## 13. Evaluación de thresholds

| Criterio | Base | Optimizada |
|---|---|---|
| Errores menores al 1 % | Cumplido | Cumplido |
| Checks mayores al 99 % | Cumplido | Cumplido |
| p95 menor a 500 ms | Cumplido | Cumplido |
| p99 menor a 800 ms | Cumplido | Cumplido |
| Resultado final | **APROBADO** | **APROBADO** |

---

## 14. Decisión técnica

Los thresholds fueron cumplidos en ambas versiones.

La configuración optimizada es técnicamente preferible porque:

- reduce el p50 en 82.82 %;
- reduce el p95 en 81.86 %;
- reduce el p99 en 75.47 %;
- aumenta el throughput en 9.30 %;
- mantiene 0 % de errores;
- conserva 100 % de checks exitosos.

Se recomienda mantener la configuración optimizada y continuar evaluando el comportamiento del sistema con cargas mayores para identificar un posible punto de saturación.

---

## 15. Evidencias

Las evidencias de la práctica se almacenan en:

```text
evidencia/sesion28/
```

Archivos generados:

- `smoke-test.txt`
- `carga-base.txt`
- `carga-optimizada.txt`
- `resumen-base.json`
- `resumen-optimizado.json`
- `REPORTE_SESION28.md`

Capturas pendientes o complementarias:

- salida del smoke test;
- ejecución de la carga base;
- ejecución de la carga optimizada;
- CPU y memoria durante 10 VUs;
- CPU y memoria durante 30 VUs.

---

## 16. Conclusión

La prueba de carga permitió evaluar el comportamiento de la API con hasta 30 usuarios virtuales bajo un escenario progresivo y reproducible.

La versión base obtuvo un p95 de **138.07 ms**, mientras que la versión optimizada alcanzó un p95 de **25.04 ms**. Esto representa una mejora de **81.86 %**.

El p99 también se redujo de **141.08 ms** a **34.61 ms**, y el throughput aumentó de **15.37** a **16.80 solicitudes por segundo**.

Ambas versiones mantuvieron **0 % de errores HTTP** y **100 % de checks exitosos**. Por lo tanto, la optimización mejoró el rendimiento sin afectar la funcionalidad y todos los thresholds definidos fueron cumplidos.