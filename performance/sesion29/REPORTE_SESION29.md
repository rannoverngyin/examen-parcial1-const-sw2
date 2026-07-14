# Reporte – Sesión 29

## 1. Objetivo e hipótesis
* **Hipótesis:** Sostener la carga mixta sin superar el p95 de 700 ms en consultas, p95 de 1000 ms en escrituras y 2% de errores globales.

## 2. Entorno y versión evaluada
* **SUT:** API de Productos (Spring Boot) corriendo localmente.
* **Herramientas:** k6 vX.Y.Z, Java 17.

## 3. Modelo de carga
* **Escenarios:** `consultas` (ramping-vus), `registros` (constant-arrival-rate) y `pico_reportes` (ramping-arrival-rate).

## 4. Umbrales (Thresholds)[cite: 3]
* Se definieron límites específicos de percentiles por cada endpoint (`listado`, `total`, `registro`, `reporte`)[cite: 3].

## 5. Resultados de la Prueba
*(Completa esta tabla abriendo tu archivo `resumen-sesion29.json`)*[cite: 3]

| Escenario | p95 | p99 | Error | RPS / Iteraciones | Dropped iterations | Resultado (Cumple/No) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Consultas** | *X* ms | *X* ms | *X*% | *X* | 0[cite: 3] | Cumple[cite: 3] |
| **Registros** | *X* ms | *X* ms | *X*% | 5.00 iters/s[cite: 3] | 0[cite: 3] | Cumple[cite: 3] |
| **Pico reportes** | *X* ms | *X* ms | *X*% | 0.82 iters/s[cite: 3] | 0[cite: 3] | Cumple[cite: 3] |

## 6. Recursos observados[cite: 3]
*(Menciona cómo se comportó tu CPU o consumo de RAM durante la prueba en tu máquina, especialmente en el pico del segundo 80 al 110)*[cite: 3].

## 7. Respuestas a las Preguntas de Reflexión[cite: 3]
1. **¿Por qué `constant-arrival-rate` representa mejor una demanda externa que `constant-vus`?**[cite: 3]
   * *Respuesta:* Porque en el mundo real, los usuarios o servicios externos siguen llegando a un ritmo constante sin importar si el backend se ralentiza. Un modelo cerrado (`constant-vus`) reduce el ritmo si el servidor se vuelve lento, enmascarando los cuellos de botella.
2. **¿Qué diferencia existe entre un check fallido y un threshold incumplido?**[cite: 3]
   * *Respuesta:* Un `check` es una aserción funcional (ej. verificar HTTP 200) que no detiene la prueba ni la marca como fallida en el pipeline. Un `threshold` es un objetivo de rendimiento global (ej. p95 < 1000ms); si se incumple, k6 retorna un código de salida distinto de cero, marcando la ejecución como fallida[cite: 3].
3. **¿Qué significa que `dropped_iterations` aumente aunque el error HTTP sea bajo?**[cite: 3]
   * *Respuesta:* Significa que el generador de carga (k6) se quedó sin VUs disponibles en su pool asignado (`preAllocatedVUs`/`maxVUs`) para mantener la tasa de llegadas programada, o que el servidor está tardando tanto en responder que satura el flujo de peticiones entrantes[cite: 3].