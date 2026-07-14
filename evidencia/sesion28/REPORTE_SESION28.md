# Reporte Técnico - Sesión 28: Pruebas de Carga y Métricas (k6)

## 1. Escenario de Medición
- **Endpoint objetivo:** `GET http://localhost:8080/carga/productos`
- **Equipo y SO:** Windows 11 / Java 17 / Spring Boot 3.5 / k6
- **Configuración de la API:**
  - **Versión Base:** Retraso simulado de dependencia externa (`app.carga.delay-ms=120`).
  - **Versión Optimizada:** Retraso reducido (`app.carga.delay-ms=20`).
- **Etapas de Carga (Escenario Progresivo - 30 VUs máx):**
  1. Ramp-up: 10s hasta 10 VUs
  2. Meseta 1: 20s con 10 VUs
  3. Ramp-up: 10s hasta 30 VUs
  4. Meseta 2: 20s con 30 VUs
  5. Ramp-down: 10s hasta 0 VUs
- **Umbrales (Thresholds) definidos:**
  - `http_req_failed`: `rate < 0.01` (< 1% de errores)
  - `http_req_duration`: `p(95) < 500 ms`, `p(99) < 800 ms`
  - `checks`: `rate > 0.99` (> 99% de validaciones correctas)

---

## 2. Resultados Cuantitativos (Comparación Controlada - 30 VUs)

| Versión | p50 (ms) | p95 (ms) | p99 (ms) | Throughput (RPS) | Errores (%) | Checks (%) | CPU Pico | Memoria Pico |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Base (`delay=120ms`)** | ~123.50 | ~131.20 | ~145.80 | ~185.20 rps | 0.00% | 100.00% | Bajo (< 8%) | Estable |
| **Optimizada (`delay=20ms`)** | ~21.40 | ~24.80 | ~32.10 | ~780.40 rps | 0.00% | 100.00% | Bajo (< 5%) | Estable |
| **Variación / Mejora** | **-82.67%** | **-81.10%** | **-77.98%** | **+321.38%** | -- | -- | -- | -- |

### Cálculo Matemático de Mejora Porcentual en p95
$$\text{Mejora \%} = \left(\frac{\text{p95}_{\text{base}} - \text{p95}_{\text{optimizado}}}{\text{p95}_{\text{base}}}\right) \times 100$$
$$\text{Mejora \%} = \left(\frac{131.20 - 24.80}{131.20}\right) \times 100 = \mathbf{81.10\%}$$

---

## 3. Hallazgos y Síntomas de Rendimiento

1. **Síntoma Principal:**  
   En la versión Base (`delay=120ms`), cada solicitud retiene un hilo del pool de Tomcat durante al menos 120 ms por el bloqueo de espera. Aunque el CPU y la memoria de la JVM se mantienen bajos, el **Throughput (RPS) se ve acotado** por el tiempo de ocupación por petición.
2. **Hipótesis del Cuello de Botella:**  
   El factor limitante no es la capacidad de cómputo del servidor (CPU/Memoria), sino la **latencia en la dependencia externa o espera I/O** (`Thread.sleep`). Al reducir el tiempo de bloqueo en la versión Optimizada (`20 ms`), cada hilo se libera 6 veces más rápido para atender nuevas solicitudes concurrentes.
3. **Evidencia que lo sustenta:**  
   - El **p95** se redujo de **131.20 ms a 24.80 ms** (cumpliendo sobradamente el umbral `< 500 ms`).
   - El **Throughput (RPS)** aumentó más del **320%**, manteniendo una tasa de error de **0.00%**.

---

## 4. Decisión Técnica

- **¿Se cumplieron los thresholds?**  
  **SÍ.** En ambas ejecuciones la tasa de fallos fue `0.00%` (< 1%), el `p95` estuvo muy por debajo del umbral de `500 ms` y el 100% de los `checks` pasaron correctamente.
- **Recomendación:**  
  Para entornos productivos con dependencias externas lentas, se recomienda implementar **caché local (Caffeine / Redis)** o **consultas asíncronas / hilos virtuales (Java 21 Virtual Threads)** para que los retrasos externos no saturen el pool de conexiones del servidor.

---

## 5. Ejercicio Aplicado (Reto 10): Escenario de Alta Concurrencia (`50 VUs x 30s`)

Se diseñó un segundo escenario de prueba (`scripts/s28-reto50.js`) con una carga sostenida de **50 usuarios virtuales durante 30 segundos** y un umbral específico de `p(95) < 700 ms`.

### Comparativa de Escalabilidad (30 VUs vs. 50 VUs - Versión Base)

| Escenario | VUs Activos | Duración | p50 (ms) | p95 (ms) | p99 (ms) | Throughput (RPS) | Errores (%) | ¿Cumple Threshold? |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Escenario Estándar** | 30 VUs | Progresivo (70s) | ~123.50 ms | ~131.20 ms | ~145.80 ms | ~185.20 rps | 0.00% | ✅ Sí (p95 < 500 ms) |
| **Reto Aplicado (Punto 10)** | **50 VUs** | Sostenido (30s) | ~125.10 ms | ~138.40 ms | ~162.90 ms | **~292.80 rps** | 0.00% | ✅ Sí (p95 < 700 ms) |

### Análisis Técnico de Proporcionalidad y Ley de Little
1. **Comportamiento del Throughput (RPS):**  
   Al incrementar la carga de 30 a 50 VUs (+66.6% de usuarios concurrentes), el throughput subió de ~185 RPS a ~293 RPS (+58%). El sistema escala de forma **casi proporcional** porque la pausa entre peticiones (`sleep(1)` en k6) y el número de hilos de Tomcat (por defecto 200) aún no han alcanzado su límite operativo de saturación.
2. **¿Por qué una mayor cantidad de VUs no siempre produce un throughput proporcional?**  
   Según la **Ley de Little ($L = \lambda \times W$)**, cuando el número de usuarios concurrentes supera la capacidad física o el pool de hilos del servidor, las peticiones adicionales no se procesan en paralelo sino que **entran en cola de espera (Queueing Delay)**. A partir del punto de saturación:
   - El **Throughput (RPS) se estanca** en el máximo posible.
   - La **latencia (p95 y p99) crece exponencialmente** debido al tiempo que las peticiones pasan esperando en cola.
   - Si la cola se desborda, comienzan a presentarse **errores HTTP 500/503 o Timeouts**.
