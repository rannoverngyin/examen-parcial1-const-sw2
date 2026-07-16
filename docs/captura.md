# Entregables y Guía de Ejecución - Sesión 30: Benchmarking y Análisis de Resultados

---

## 1. Lista de Entregables Requeridos

## 1. **Código Java (Spring Boot):**
   - Servicio `BenchmarkService.java`.
![alt text](image-1.png)

   - Controlador REST `BenchmarkController.java`.
![alt text](image-2.png)

## 2. **Scripts de Carga k6:**
   - Script para la prueba baseline: `evidencia/sesion30/benchmark_baseline.js`.
![alt text](image-3.png)

   - Script para la prueba optimizada: `evidencia/sesion30/benchmark_optimizado.js`.
![alt text](image-4.png)

## 3. **Archivos de Resultados en Carpeta de Evidencias:**
# Correr 3 ejecuciones para Baseline
docker run --rm -i -v "${PWD}:/scripts" -w /scripts grafana/k6 run --summary-export evidencia/sesion30/resultados_baseline/run1.json evidencia/sesion30/benchmark_baseline.js

docker run --rm -i -v "${PWD}:/scripts" -w /scripts grafana/k6 run --summary-export evidencia/sesion30/resultados_baseline/run2.json evidencia/sesion30/benchmark_baseline.js

docker run --rm -i -v "${PWD}:/scripts" -w /scripts grafana/k6 run --summary-export evidencia/sesion30/resultados_baseline/run3.json evidencia/sesion30/benchmark_baseline.js

# Correr 3 ejecuciones para Optimizado
docker run --rm -i -v "${PWD}:/scripts" -w /scripts grafana/k6 run --summary-export evidencia/sesion30/resultados_optimizado/run1.json evidencia/sesion30/benchmark_optimizado.js

docker run --rm -i -v "${PWD}:/scripts" -w /scripts grafana/k6 run --summary-export evidencia/sesion30/resultados_optimizado/run2.json evidencia/sesion30/benchmark_optimizado.js

docker run --rm -i -v "${PWD}:/scripts" -w /scripts grafana/k6 run --summary-export evidencia/sesion30/resultados_optimizado/run3.json evidencia/sesion30/benchmark_optimizado.js



   - 3 salidas `.json` y `.txt` en `evidencia/sesion30/resultados_baseline/`.
   - 3 salidas `.json` y `.txt` en `evidencia/sesion30/resultados_optimizado/`.

![alt text](image-5.png)

## 4. **Scripts Python:**
   - Script de consolidación de métricas: `evidencia/sesion30/analizar_benchmark.py`.
![alt text](image-6.png)

   - Script de generación del gráfico: `evidencia/sesion30/generar_grafico.py`.
![alt text](image-7.png)

## 5. **Reportes y Resúmenes:**
   - Tabla consolidada de datos: `evidencia/sesion30/resumen_benchmark.csv`.
![alt text](image-8.png)
   - Imagen del gráfico exportado: `evidencia/sesion30/grafico_p95.png`.
![alt text](image-9.png)
   - Reporte técnico de análisis: `evidencia/sesion30/REPORTE_SESION30.md`.

# 4. Resultados Resumidos

### Tabla de métricas principales

| Versión     | p95 promedio | p99 promedio | RPS promedio | Error rate |
|-------------|--------------|--------------|--------------|------------|
| **Baseline**   | 16.24 ms    | 35.50 ms     | 16.62 req/s | 0.0 %     |
| **Optimizado** | 18.18 ms    | 38.20 ms     | 16.60 req/s | 0.0 %     |

**Mejora porcentual p95:** `-11.92 %`

---

# 5. Interpretación Técnica

| Métrica     | Baseline promedio | Optimizado promedio | Cambio esperado          | Interpretación técnica |
|-------------|-------------------|---------------------|--------------------------|------------------------|
| **p95**     | 16.24 ms         | 18.18 ms           | Debe disminuir          | **SIN MEJORA**: La versión optimizada no redujo la latencia respecto al baseline. |
| **p99**     | 35.50 ms          | 38.20 ms            | Debe disminuir          | **ESTABLE**: La cola larga se mantuvo por debajo del umbral mínimo de registro. |
| **RPS**     | 16.62 req/s      | 16.60 req/s        | Puede aumentar/mantener | **SE MAN
TIENE**: Ambas versiones procesaron una tasa de peticiones equivalente. |
| **Error rate** | 0.00 %        | 0.00 %             | Debe mantenerse bajo    | **EXCELENTE CONFIABILIDAD**: Sin presencia de fallos en la respuesta HTTP. |

---

**Observaciones generales:**
- No se observó mejora en la latencia (p95).
- El sistema mantiene excelente confiabilidad (0 % errores) y estabilidad en throughput.
## 6. **Gestión de Versiones:**
   - Commit subido a la rama de trabajo y Pull Request en GitHub.

---

## 2. Código Fuente de los Scripts

### 2.1. Script Python para Análisis (`evidencia/sesion30/analizar_benchmark.py`)
![alt text](image.png)