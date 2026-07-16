# Reporte Sesión 30: Benchmarking y análisis de resultados

## 1. Objetivo

Comparar el rendimiento de `/benchmark/baseline` y `/benchmark/optimizado` bajo condiciones equivalentes para determinar si la optimización mejora el desempeño de manera medible y consistente.

## 2. Ambiente de prueba

- **Equipo:** Windows, Java 24 (compatible con Java 17), Spring Boot 3.2.11
- **Herramienta de carga:** k6 v2.1.0
- **Análisis:** Python 3
- **Fecha:** 15/07/2026

## 3. Diseño del benchmark

| Elemento | Baseline | Optimizado |
|---|---|---|
| Endpoint | `/benchmark/baseline` | `/benchmark/optimizado` |
| Usuarios virtuales | 20 | 20 |
| Duración | 60s (+ 15s ramp-up/down) | 60s (+ 15s ramp-up/down) |
| Repeticiones | 3 | 3 |
| Métricas | p95, p99, RPS, error rate | p95, p99, RPS, error rate |

**Justificación de la diferencia:** El baseline itera 5,000 registros y filtra en memoria (ineficiente). El optimizado devuelve una lista predefinida de 5 elementos (eficiente).

## 4. Resultados resumidos

### 4.1 Detalle por ejecución

| Versión | Run | p95 (ms) | p99/max (ms) | RPS | Error rate |
|---|---|---:|---:|---:|---:|
| Baseline | run1 | 5.93 | 26.69 | 16.74 | 0% |
| Baseline | run2 | 7.99 | 34.94 | 16.68 | 0% |
| Baseline | run3 | 9.64 | 26.28 | 16.68 | 0% |
| Optimizado | run1 | 4.33 | 15.40 | 16.76 | 0% |
| Optimizado | run2 | 4.59 | 21.20 | 16.73 | 0% |
| Optimizado | run3 | 4.60 | 15.64 | 16.72 | 0% |

### 4.2 Promedios

| Versión | p95 promedio | p95 desviación | RPS promedio | Error rate |
|---|---:|---:|---:|---:|
| Baseline | **7.85 ms** | 1.86 ms | 16.70 req/s | 0% |
| Optimizado | **4.51 ms** | 0.15 ms | 16.74 req/s | 0% |

### 4.3 Mejora porcentual

| Métrica | Baseline | Optimizado | Mejora |
|---|---:|---:|---:|
| **p95** | 7.85 ms | 4.51 ms | **42.58%** |

## 5. Análisis

- **¿Qué métrica cambió más?** El p95 bajó 42.58% (de 7.85 ms a 4.51 ms). Es la métrica con mayor variación entre versiones.
- **¿La mejora fue consistente en las 3 ejecuciones?** Sí. El optimizado mostró p95 entre 4.33-4.60 ms (rango de 0.27 ms), mientras que el baseline varió entre 5.93-9.64 ms (rango de 3.71 ms). El optimizado es más estable.
- **¿Hubo errores?** No. 0% de error rate en las 6 ejecuciones.
- **¿Existe evidencia suficiente para recomendar el cambio?** Sí. La mejora supera el umbral del 20% y es consistente.

## 6. Conclusión técnica

**Se recomienda aceptar la optimización.** La versión optimizada reduce el p95 en un 42.58% sin aumento de errores y con menor variabilidad entre ejecuciones (desviación 0.15 ms vs 1.86 ms del baseline). La causa raíz de la mejora es evitar la iteración innecesaria de 5,000 registros en memoria, devolviendo directamente los resultados precalculados.

## 7. Evidencias

- Scripts k6: `evidencia/sesion30/benchmark_baseline.js`, `benchmark_optimizado.js`
- Resultados: `evidencia/sesion30/resultados_baseline/`, `resultados_optimizado/`
- CSV: `evidencia/sesion30/resumen_benchmark.csv`
- Script Python: `evidencia/sesion30/analizar_benchmark.py`
- Rama: `feature/sesion30_benchamrk_zelaya_anali`
