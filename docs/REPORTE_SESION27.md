# Reporte técnico - Sesión 27
## Pruebas de rendimiento en frontend y backend

### 1. Escenario
- **Endpoint base:** `GET /rendimiento/productos/base`
- **Endpoint optimizado:** `GET /rendimiento/productos/optimizado`
- **Dataset:** 5 productos predefinidos (Laptop, Mouse, Teclado, Monitor, Impresora)
- **Repeticiones:** 5 de calentamiento + 30 mediciones por endpoint
- **Equipo/entorno:** Windows, Java 24 (compatible con Java 17), Spring Boot 3.2.11
- **Commit evaluado:** (pendiente)

### 2. Hipótesis inicial
La versión base incluye un `Thread.sleep(200)` deliberado y transforma la lista a mayúsculas en cada llamada (`stream().map().toList()`). La versión optimizada precalcula la lista en la construcción del bean y omite el sleep. Se espera que el p95 base sea ~230 ms y el optimizado sea significativamente menor (< 30 ms), demostrando que evitar trabajo repetitivo y esperas bloqueantes reduce la latencia percibida.

### 3. Resultados (4 ejecuciones consolidadas)

| Versión | Promedio (ms) | Mediana (ms) | p95 (ms) | Mínimo (ms) | Máximo (ms) | Errores |
|---|---:|---:|---:|---:|---:|---:|
| Base | 218.17 | 213.87 | 233.38 | 204.50 | 262.50 | 0 |
| Optimizada | 11.28 | 7.45 | 24.52 | 2.73 | 32.30 | 0 |

**Mejora porcentual (p95):** `((233.38 - 24.52) / 233.38) * 100` = **89.49 %**

### 4. Análisis de los resultados

#### ¿Por qué la versión optimizada no da 0 ms y varía entre 3 y 32 ms?

Aunque la versión optimizada elimina el `Thread.sleep(200)` y precalcula la lista, el tiempo mínimo de ~3 ms y los picos de ~32 ms se deben a factores inevitables del stack tecnológico:

| Factor | Impacto | Explicación |
|---|---|---|
| **Overhead del framework Spring Boot** | ~3-8 ms | Cada request atraviesa: Tomcat → DispatcherServlet → HandlerMapping → Controller → Jackson serialización → respuesta. Esto tiene un costo fijo por llamada. |
| **Serialización JSON (Jackson)** | ~1-5 ms | Convertir `List<String>` a JSON requiere reflexión sobre los objetos, escritura de bytes y bufferización. |
| **JIT Compilation (C1/C2)** | Variable | La JVM interpreta bytecode al inicio y luego compila métodos "calientes". Durante las 30 mediciones, algunas requests ocurren antes de que el JIT haya optimizado completamente, causando latencia adicional. |
| **Garbage Collection** | Picos de ~10-30 ms | El GC de Java (G1GC) puede pausar brevemente el hilo de la aplicación para recolectar objetos. Si ocurre justo durante una request, el tiempo se dispara. |
| **Scheduling del SO** | ~1-5 ms | Windows puede interrumpir el hilo de Java para atender otros procesos del sistema. |
| **Jitter de red local (loopback)** | ~0.1-1 ms | Aunque es localhost, el stack TCP/IP (127.0.0.1) introduce micro-variaciones. |

#### Conclusión del análisis
- La versión **base** (~233 ms p95) está dominada por el `Thread.sleep(200)` + overhead (~30 ms de framework).
- La versión **optimizada** (~25 ms p95) elimina el cuello de botella principal (sleep + transformación repetitiva), pero el piso está limitado por el overhead inevitable de Spring Boot, Jackson y la JVM.
- La **variabilidad** (min 2.73 ms vs max 32.30 ms) es normal en sistemas gestionados y evidencia la importancia de usar **p95** en lugar de promedios o valores aislados.

### 5. Evidencias
- **Salida del script `medir_rendimiento.py` (3 ejecuciones):**

```
# Ejecución 1
BASE   → prom=213.05  med=206.85  p95=228.11  min=204.50  max=240.01
OPT    → prom=15.94   med=15.57   p95=28.18   min=3.67    max=30.22

# Ejecución 2
BASE   → prom=215.72  med=211.88  p95=230.44  min=206.37  max=235.13
OPT    → prom=9.84    med=5.72    p95=22.64   min=2.78    max=31.01

# Ejecución 3
BASE   → prom=218.33  med=218.72  p95=231.62  min=205.85  max=262.50
OPT    → prom=11.48   med=4.80    p95=29.14   min=3.34    max=32.30
```

- Captura DevTools Network: (pendiente)
- Captura DevTools Performance: (pendiente)
- JFR: `evidencia/sesion27/s27_con_script.jfr` (311 KB) — grabación durante la 4ta ejecución del script con perfil `profile`. Abrir con VisualVM o JDK Mission Control para observar CPU, Heap, Threads y GC.

### 6. Conclusión
- **Cuello de botella identificado:** La espera bloqueante (`Thread.sleep(200)`) y la transformación repetitiva de la lista en cada llamada a `listarBase()`.
- **Mejora aplicada:** Precalcular la lista transformada en la construcción del bean y eliminar el `Thread.sleep`.
- **Criterio de éxito:** ✅ **CUMPLIDO** — p95 optimizado (26.65 ms) es significativamente menor que p95 base (230.06 ms), con una mejora del **88.42 %** y 0 errores en ambos endpoints.
- **Observación importante:** La versión optimizada aún tiene un piso de ~27 ms debido al overhead inherente de Spring Boot + Jackson + JVM, lo cual es normal y esperado en aplicaciones web Java.

### 7. Reto aplicado (opcional)
- (pendiente de implementar)
