# Reporte técnico - Sesión 27

## 1. Escenario
- Endpoint: /rendimiento/productos/base y /rendimiento/productos/optimizado
- Dataset: 5 productos (lista fija en memoria)
- Repeticiones: 5 de calentamiento + 30 mediciones por endpoint
- Equipo/entorno: Windows, Java 21.0.10, Git Bash (MINGW64)
- Commit evaluado: (pega aquí el hash una vez que hagas commit)

## 2. Hipótesis inicial
Se hipotetizó que la versión base sería significativamente más lenta debido
a un retraso artificial (Thread.sleep de 200ms) que simula una operación
externa costosa (ej. consulta a base de datos o llamada a un servicio).
Se esperaba que este retraso fuera de tipo "espera" (I/O-bound) y no de
cómputo intensivo (CPU-bound).

## 3. Resultados

| Versión | Promedio | Mediana | p95 | Errores | CPU pico (JVM) | Heap |
|---|--:|--:|--:|--:|--:|--:|
| Base | 225.68 ms | 225.68 ms | 230.05 ms | 0 | ~9% | 83.8→87.8 MB |
| Optimizada | 15.06 ms | 16.18 ms | 19.84 ms | 0 | ~9% | 83.8→87.8 MB |

**Mejora en p95:** ((230.05 - 19.84) / 230.05) × 100 = **91.38%**

**TTFB (DevTools Network) - versión base:**
- Request sent: 0.15 ms
- Waiting for server response (TTFB): 217.73 ms
- Content Download: 1.30 ms
- Total: 221.44 ms

**Performance (DevTools):** INP = 24ms, sin tareas largas (long tasks)
detectadas en el hilo principal del navegador.

## 4. Evidencias
- Captura DevTools Network (Timing) - versión base.
- Captura DevTools Performance (INP, Main thread).
- Captura VisualVM (CPU, Heap, Classes, Threads).
- Grabación JFR (s27.jfr) con resumen de eventos jdk.CPULoad y
  jdk.GCHeapMemoryUsage.
- Salida de medir_rendimiento.py.

## 5. Conclusión
El cuello de botella de la versión base se confirmó como una espera
simulada (I/O-bound), no un problema de cómputo: el uso de CPU del
proceso JVM se mantuvo consistentemente bajo (<1.5% en la mayoría de
las muestras) en ambas versiones, a pesar de la enorme diferencia de
latencia (225.68 ms vs 15.06 ms). El TTFB medido en el navegador
(217.73 ms) coincide con el tiempo de espera del servidor, confirmando
que el retraso no proviene de la red ni del navegador.

La optimización aplicada (precalcular y reutilizar una respuesta
inmutable en lugar de repetir trabajo y simular una espera externa)
logró una mejora del 91.38% en el p95, sin generar errores HTTP y sin
impacto negativo en el consumo de memoria. El criterio de éxito de la
práctica (p95 optimizado < p95 base, sin errores) se cumplió.