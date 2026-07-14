# Reporte de Pruebas de Carga - Sesión 28

## 1. Resumen Ejecutivo
Este documento presenta el análisis comparativo del rendimiento de la API desarrollado bajo entornos de simulación de carga utilizando Grafana k6. Se evaluó el comportamiento de la aplicación en dos escenarios: un estado base con un retardo simulado alto y un estado optimizado tras mitigar dicho retardo en el backend.

---

## 2. Configuración del Entorno de Pruebas
- **Herramienta de Prueba:** Grafana k6 (vía Docker)
- **Script de Pruebas:** `scripts/s28-load.js`
- **Escenario de Carga:** 
  - Fase 1: Ramp-up de 0 a 10 VUs (15s)
  - Fase 2: Estabilización a 10 VUs (15s)
  - Fase 3: Ramp-up de 10 a 30 VUs (15s)
  - Fase 4: Estabilización a 30 VUs (15s)
  - Fase 5: Ramp-down de 30 a 0 VUs (10s)
- **Duración Total:** ~1 minuto y 10 segundos

---

## 3. Cuadro Comparativo de Resultados

| Métrica | Caso Base (Alta Latencia) | Caso Optimizado (Baja Latencia) | Impacto / Diferencia |
| :--- | :---: | :---: | :---: |
| **Peticiones Totales (http_reqs)** | 1073 | 1175 | +9.51% (Mayor rendimiento) |
| **Rendimiento (RPS)** | 15.21 req/s | 16.73 req/s | +1.52 req/s |
| **Tiempo de Respuesta Promedio (avg)** | 133.36 ms | 32.80 ms | -75.40% (Más rápido) |
| **Mediana del Tiempo (p50)** | 133.03 ms | 33.00 ms | -75.19% |
| **Percentil 95 (p95)** | 141.56 ms | 39.91 ms | -71.81% |
| **Tiempo de Respuesta Máximo (max)** | 511.25 ms | 123.03 ms | -75.94% |
| **Tasa de Errores (http_req_failed)** | 0.00% | 0.00% | Sin variación (0 errores) |
| **Éxito de Checks (status 200 & body)** | 100.00% (2146/2146) | 100.00% (2350/2350) | Estable |

---

## 4. Análisis de Resultados

### Comportamiento Base
Con la API configurada en su estado inicial, la latencia media se situó en **133.03 ms**, logrando procesar un total de **1073 peticiones**. El sistema se comportó de manera totalmente estable (0% errores), pero el rendimiento total se encontraba acotado por el retraso artificial del backend.

### Comportamiento Optimizado
Al reducir el retraso de la API a 20 ms, se observó una mejora drástica e inmediata en la experiencia del usuario:
- **Reducción de Latencia:** El percentil 95 (p95) disminuyó de **141.56 ms** a **39.91 ms**. Esto significa que el 95% de los usuarios experimentaron tiempos de respuesta ultra rápidos, por debajo de los 40 ms.
- **Incremento de Capacidad (Throughput):** Al responder más rápido, las VUs (usuarios virtuales) completaron sus iteraciones en menor tiempo (el promedio de duración de iteración bajó de **1.13 s** a **1.03 s**), permitiendo procesar **102 peticiones adicionales** en el mismo periodo de tiempo.

---

## 5. Conclusiones y Recomendaciones
1. **La optimización fue exitosa:** La reducción del delay interno eliminó el cuello de botella de procesamiento, garantizando tiempos de respuesta excelentes sin comprometer la integridad de las respuestas ni generar fallos de conexión.
2. **Recomendación:** Mantener la monitorización constante en entornos productivos para identificar si existen bloqueos de base de datos o llamadas a APIs de terceros que puedan introducir latencias similares a las simuladas en el escenario base.