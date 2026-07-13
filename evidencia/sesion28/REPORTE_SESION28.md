# Reporte técnico - Sesión 28

## 1. Escenario
- **Endpoint:** `GET http://localhost:8080/carga/productos` (simula la obtención del catálogo de productos).
- **Equipo y sistema operativo:** Windows 10 Home/Pro, CPU de 4 núcleos (arquitectura x64), JVM Java 17 (Oracle Corporation 17.0.12).
- **Perfil/configuración:**
  - Versión Base: `app.carga.delay-ms=120` (120 ms de retardo artificial en el servicio).
  - Versión Optimizada: `app.carga.delay-ms=20` (20 ms de retardo artificial en el servicio).
  - Versión Reto (50 VUs): `app.carga.delay-ms=20` (20 ms de retardo artificial en el servicio).
- **Etapas de carga:**
  - **Prueba de 30 VUs (Base y Optimizada):**
    - 0s a 10s: Rampa ascendente de 0 a 10 VUs.
    - 10s a 30s: 10 VUs constantes.
    - 30s a 40s: Rampa ascendente de 10 a 30 VUs.
    - 40s a 60s: 30 VUs constantes.
    - 60s a 70s: Rampa descendente de 30 a 0 VUs. (Duración total: 70s).
  - **Prueba de 50 VUs (Reto):**
    - 0s a 10s: Rampa ascendente de 0 a 50 VUs.
    - 10s a 40s: 50 VUs constantes (durante 30 segundos).
    - 40s a 50s: Rampa descendente de 50 a 0 VUs. (Duración total: 50s).
- **Umbrales:**
  - `http_req_failed`: Tasa de fallos < 1% (`rate<0.01`).
  - `http_req_duration`:
    - Para 30 VUs: `p(95)<500` y `p(99)<800`.
    - Para 50 VUs (Reto): `p(95)<700` (umbral específico).
  - `checks`: Tasa de acierto de validaciones funcionales > 99% (`rate>0.99`).
- **Commit evaluado:** `5d6f99f11e1e024b5fdd5a25ca55b3697954f988`.

## 2. Resultados
| Versión | p50 | p95 | p99 | RPS | Errores | CPU Pico | Memoria Pico |
|---|---:|---:|---:|---:|---:|---:|---:|
| **Base** (120ms - 30 VUs) | 124.07 ms | 146.58 ms | 179.86 ms | 15.39 req/s | 0.00% | 0.94% | 126.72 MB |
| **Optimizada** (20ms - 30 VUs) | 24.76 ms | 35.60 ms | 52.08 ms | 16.88 req/s | 0.00% | 1.79% | 132.68 MB |
| **Reto** (20ms - 50 VUs) | 26.26 ms | 113.77 ms | 243.09 ms | 38.51 req/s | 0.00% | 3.66% | 135.56 MB |

### Análisis de Rendimiento (Throughput)
El volumen total de peticiones (RPS) está limitado principalmente por la instrucción `sleep(1)` en el bucle del script de k6. 
* Con 120 ms de retardo, cada iteración dura aproximadamente `1120 ms`, limitando el throughput máximo.
* Con 20 ms de retardo, cada iteración dura aproximadamente `1020 ms`, lo que permite un incremento en la tasa de peticiones atendidas (de 15.39 a 16.88 RPS, un incremento de **~9.68%**).
* Para el Reto de 50 VUs (duración de 50s, con promedio de 40 VUs concurrentes debido a las rampas), el throughput general subió a **38.51 req/s** (+128.14% en comparación con la versión optimizada de 30 VUs que promedia 17.14 VUs concurrentes).

## 3. Hallazgos
- **Síntoma principal:** El tiempo de respuesta disminuyó de manera drástica al reducir el retardo del servicio. Las latencias p95 y p99 bajaron en más de un **70%** entre la versión Base y la Optimizada. Con el Reto de 50 VUs, la latencia típica (p50) apenas se modificó (+1.5 ms), pero las latencias de cola (p95 y p99) crecieron debido al encolamiento inicial bajo mayor concurrencia.
- **Hipótesis de cuello de botella:** El cuello de botella en la versión Base era el retardo artificial (`Thread.sleep(120)`) inyectado en el método `listarProductos()`. No se evidenciaron limitaciones a nivel de hardware (CPU/Memoria), ya que el consumo de recursos de la JVM se mantuvo sumamente bajo en ambas pruebas.
- **Evidencia que la sustenta:**
  1. El consumo de CPU pico fue de apenas **0.94%** en la versión Base y **1.79%** en la versión Optimizada (sobre un procesador de 4 núcleos). Esto demuestra que el procesador estuvo casi inactivo durante la mayor parte del tiempo, esperando la finalización de los hilos durmientes.
  2. Al reducir el retardo de 120 ms a 20 ms, la latencia media (`avg`) bajó de **127.67 ms** a **26.38 ms**, casi en la misma proporción del cambio del retardo artificial.

## 4. Decisión
Los umbrales (thresholds) de k6 se **cumplieron al 100%** en todas las ejecuciones:
- Tasa de errores: 0% (Límite: <1%).
- Latencia p95 (Optimizada 30 VUs): 35.60 ms (Límite: <500 ms).
- Latencia p95 (Reto 50 VUs): 113.77 ms (Límite: <700 ms).
- Latencia p99 (Optimizada 30 VUs): 52.08 ms (Límite: <800 ms).
- Validación de checks funcionales: 100% de éxito (Límite: >99%).

**Recomendación:** Se recomienda aprobar los cambios y proceder a integrar el servicio. El sistema responde de manera óptima bajo la carga progresiva planteada y no presenta fugas de memoria o saturación de CPU.

---

## 5. Reto: Comparación de Escalamiento con 50 VUs

### Comportamiento del Sistema bajo 50 VUs
El sistema demostró un comportamiento altamente estable con 50 VUs, escalando de manera **proporcional en rendimiento (throughput)** sin llegar a un punto de saturación crítica:
* **Escalamiento del Throughput (RPS):** El número de peticiones por segundo promedio aumentó de 16.88 req/s (con un promedio de 17.14 VUs en el escenario de 30 VUs) a 38.51 req/s (con un promedio de 40 VUs en el escenario de 50 VUs). Esto representa un escalamiento lineal casi perfecto (la variación de VUs promedio fue de **+133.3%** y la de RPS fue de **+128.1%**).
* **Comportamiento de la Latencia:** El percentil 50 (latencia del usuario típico) se mantuvo extremadamente estable, pasando de **24.76 ms** (con 30 VUs) a **26.26 ms** (con 50 VUs). Sin embargo, el percentil 95 experimentó una degradación, subiendo de **35.60 ms** a **113.77 ms** (+219.58%), y el percentil 99 subió a **243.09 ms**. Esto indica que, aunque no hay saturación de recursos duros (CPU del sistema apenas subió a **3.66%** y memoria a **135.56 MB**), la concurrencia adicional empieza a generar encolamiento menor a nivel de Tomcat (hilos) o scheduling del sistema operativo.

### Explicación Teórica: ¿Por qué más VUs no siempre produce mayor Throughput?
Una creencia común es que a mayor cantidad de Usuarios Virtuales (VUs), mayor será el rendimiento global. Sin embargo, esto no siempre ocurre debido a tres fenómenos clave:
1. **Saturación y Encolamiento (Leyes de Amdahl y Universal Scalability Law):** Si el servidor o alguna de sus dependencias (base de datos, red, I/O) alcanza el 100% de utilización, añadir más VUs no aumentará el procesamiento. En su lugar, las peticiones adicionales quedarán esperando en una cola, lo que aumentará drásticamente la latencia (`http_req_duration`) mientras que las peticiones completadas por segundo (RPS) se estabilizarán (meseta) o incluso disminuirán debido al costo de gestionar dicha cola.
2. **Sobrecarga de Context Switching (Cambio de Contexto):** En arquitecturas clásicas de hilos dedicados (como Tomcat servlet thread-per-request), cada nueva conexión activa requiere un hilo del sistema operativo. Si la cantidad de hilos excede los núcleos físicos disponibles, la CPU pasa más tiempo guardando y restaurando el estado de los hilos (context switching) que ejecutando código útil, disminuyendo el throughput efectivo.
3. **Pacing y Bloqueos de Sincronización:** Cuando las peticiones compiten por recursos bloqueados (como bloqueos de tablas de base de datos o bloques `synchronized` en Java), los hilos de los VUs quedan bloqueados en estado de espera. La latencia de esas peticiones aumenta y, por tanto, la tasa individual de peticiones por VU disminuye, reduciendo la velocidad de procesamiento total del sistema.

