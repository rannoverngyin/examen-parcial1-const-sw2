# Reporte Sesión 30: Benchmarking y análisis de resultados

## 1. Objetivo
Comparar el rendimiento de `/benchmark/baseline` y `/benchmark/optimizado` bajo condiciones equivalentes.

## 2. Ambiente de prueba
- **Equipo:** PC Intel Core i7
- **Sistema operativo:** Windows 11
- **Java:** 17
- **Spring Boot:** 3.5.14
- **Herramienta de carga:** k6 v0.51.0
- **Fecha y hora:** Julio 2026

## 3. Diseño del benchmark
- Usuarios virtuales: 20
- Duración: 60 segundos por prueba (+15s ramp-up / +15s ramp-down)
- Número de repeticiones: 3 por endpoint
- Endpoints evaluados: `/benchmark/baseline` y `/benchmark/optimizado`

## 4. Resultados resumidos

| Versión | p95 promedio (ms) | Tiempo promedio (ms) | RPS promedio | Error rate |
| :--- | :---: | :---: | :---: | :---: |
| **Baseline** | 1.41 ms | 0.94 ms | 16.54 req/s | 0 % |
| **Optimizado** | 1.11 ms | 0.67 ms | 16.60 req/s | 0 % |

## 5. Análisis
- **¿Qué métrica cambió más?** El p95 se redujo de 1.41 ms a 1.11 ms, una mejora del 21.8 %.
- **¿La mejora fue consistente en las 3 ejecuciones?** Sí. La desviación estándar del p95 en baseline (0.04 ms) fue baja y en optimizado (0.07 ms) también se mantuvo estable, indicando resultados reproducibles.
- **¿Hubo errores?** No. Todas las solicitudes se completaron exitosamente (0 % de error en ambos casos).
- **¿Existe evidencia suficiente para recomendar el cambio?** Sí, la mejora porcentual del 21.8 % supera el umbral del 20 %.

## 6. Conclusión técnica
Se recomienda implementar la versión optimizada dado que reduce significativamente los tiempos de respuesta sin comprometer la estabilidad del sistema ni generar tasas de error.

## 7. Evidencias
- CSV de resumen generado en `evidencia/sesion30/resumen_benchmark.csv`
- Gráfico comparativo `evidencia/sesion30/grafico_p95.png`
- Resultados individuales en `evidencia/sesion30/resultados_baseline/` y `resultados_optimizado/`
