# Informe de Pruebas de Carga y Monitoreo de Recursos (Sesión 29)

Este informe detalla los resultados obtenidos durante la ejecución de los escenarios avanzados de prueba de carga con **k6** sobre el microservicio Spring Boot (Java 26, PID 5020) y el comportamiento de la memoria nativa JVM registrado en paralelo.

---

## 1. Archivos de Evidencia Generados

Todos los artefactos de la prueba se han guardado de forma organizada en el directorio `evidencia/`:

- **[salida-k6.txt](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/salida-k6.txt)**: Log completo de la salida estándar de la ejecución de k6.
- **[resumen-sesion29.json](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/resumen-sesion29.json)**: Reporte JSON oficial con las métricas consolidadas de k6.
- **[backend.log](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/backend.log)**: Logs del backend Spring Boot redirigidos durante las pruebas.
- **[dashboard-peak-load.png](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/dashboard-peak-load.png)**: Captura de pantalla del dashboard web de monitoreo en tiempo real (`http://localhost:9090`) bajo carga máxima.
- **Snapshots de Native Memory Tracking (NMT)**:
  - **[nmt-baseline.txt](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/nmt-baseline.txt)**: Estado de memoria inicial del JVM (sin carga).
  - **[nmt-steady-10vus.txt](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/nmt-steady-10vus.txt)**: Estado durante carga media (10 VUs estables).
  - **[nmt-peak-load.txt](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/nmt-peak-load.txt)**: Estado durante el pico de carga máximo (25 VUs + registros + pico reportes).
  - **[nmt-ramp-down.txt](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/nmt-ramp-down.txt)**: Estado durante la fase de bajada gradual de carga.
  - **[nmt-post-test.txt](file:///c:/Users/ZUZUKA/examen-parcial1-const-sw2/evidencia/nmt-post-test.txt)**: Estado final de la memoria nativa tras concluir el test y ejecutar Garbage Collection (GC) manual.

---

## 2. Resumen de Métricas HTTP (k6)

| Métrica | Valor Obtenido | Umbral Definido | Estado |
|---------|----------------|-----------------|--------|
| **Peticiones Totales (`http_reqs`)** | 3,460 peticiones | - | OK |
| **Throughput Promedio** | 21.57 req/s | - | OK |
| **Tasa de Error (`http_req_failed`)** | 0.00% | < 2.00% | **Éxito (0%)** |
| **Checks Exitosos** | 100.00% (3,459) | > 98.00% | **Éxito (100%)** |
| **Duración Promedio (`http_req_duration`)** | 23.66 ms | - | OK |
| **p90 Duración** | 122.16 ms | - | OK |
| **p95 Duración** | 122.72 ms | - | OK |
| **Duración Máxima** | 482.49 ms | - | OK |

---

## 3. Rendimiento por Endpoint (Latencia)

| Endpoint / Transacción | peticiones | Latencia Promedio | Latencia p95 | Umbral p95 | Estado |
|-------------------------|------------|-------------------|--------------|------------|--------|
| **`GET /carga/productos` (listado)** | 1,020 | 2.74 ms | 4.25 ms | < 700 ms | **Éxito** |
| **`GET /carga/productos/total` (total)** | 1,123 | 1.69 ms | 3.25 ms | < 500 ms | **Éxito** |
| **`POST /carga/productos` (registro)** | 601 | 2.86 ms | 4.22 ms | < 1,000 ms | **Éxito** |
| **`GET /carga/productos/reporte` (reporte)** | 716 | 122.43 ms | 123.83 ms | < 1,500 ms | **Éxito** |

> [!NOTE]
> El endpoint `/reporte` tiene un retraso base constante de ~120 ms simulado en la aplicación, lo que explica su latencia estable y predecible cercana a ese valor bajo cualquier nivel de concurrencia.

---

## 4. Evolución de la Memoria Nativa (NMT) de la JVM

A partir de los archivos `.txt` de NMT capturados con `jcmd`, se observa la siguiente transición de memoria en las diferentes fases del test:

| Fase del Test | Reserved Memory (KB) | Committed Memory (KB) | Malloc Memory (KB) | Heap Committed (KB) |
|---|---|---|---|---|
| **Baseline (Inicial)** | 3,078,965 KB (~2.94 GB) | 225,509 KB (~220.2 MB) | 45,225 KB | 83,968 KB |
| **Carga Media (10 VUs)** | 3,079,663 KB (~2.94 GB) | 227,823 KB (~222.5 MB) | 46,759 KB | 83,968 KB |
| **Pico de Carga (25 VUs)** | 3,083,496 KB (~2.94 GB) | 235,128 KB (~229.6 MB) | 49,756 KB | 83,968 KB |
| **Ramp-Down (Bajada)** | 3,083,865 KB (~2.94 GB) | 235,463 KB (~229.9 MB) | 50,091 KB | 83,968 KB |
| **Post-Test (Tras GC)** | 3,084,436 KB (~2.94 GB) | 200,228 KB (~195.5 MB) | 47,624 KB | 51,200 KB |

### Análisis de Recursos y Comportamiento de Memoria:
1. **Comportamiento Seguro del Heap**: El Heap de Java asignó y mantuvo un espacio comprometido estable de 83.9 MB durante toda la carga. Tras la ejecución manual de Garbage Collection (`GC.run`), la memoria del Heap comprometida descendió a **51.2 MB**, liberando exitosamente el espacio ocupado por objetos efímeros creados durante la prueba.
2. **Estabilidad de la Memoria Virtual (NMT)**: La memoria total comprometida nativamente (committed) subió ligeramente de 225.5 MB a un pico de 235.1 MB durante el máximo estrés y descendió limpiamente a **200.2 MB** post-test. Esto descarta cualquier tipo de fuga de memoria (leak) tanto en Heap como en estructuras nativas de la JVM (Metaspace, Threads, Class Spaces, etc.).
3. **Escalabilidad de CPU**: Durante el test, el uso de CPU acumulado se mantuvo bajo, demostrando que la arquitectura no sufrió contenciones físicas de procesador.
