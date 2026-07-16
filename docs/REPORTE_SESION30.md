# Reporte Sesión 30: Benchmarking y análisis de resultados

## 1. Objetivo
Comparar el rendimiento de /benchmark/baseline y /benchmark/optimizado bajo condiciones equivalentes.

## 2. Ambiente de prueba
- Equipo: PC local (Windows CMD)
- Sistema operativo: Windows 10/11
- Java: 17
- Spring Boot: 3.x
- Herramienta de carga: k6 (v0.51.0 o superior)
- Fecha y hora: 16 de Julio de 2026, 00:30 AM

## 3. Diseño del benchmark
- Usuarios virtuales: 20 [cite: 45]
- Duración: 60 segundos por prueba (+15s ramp-up / +15s ramp-down) [cite: 45]
- Número de repeticiones: 3 por endpoint [cite: 45]
- Endpoints evaluados: `/benchmark/baseline` y `/benchmark/optimizado` [cite: 45]

## 4. Resultados resumidos
*(Métricas promedio calculadas a partir de las salidas reales de tus consolas de k6)*

| Versión | p95 promedio | p99 promedio | RPS promedio | Error rate |
| :--- | :---: | :---: | :---: | :---: |
| **Baseline** | 1.54 ms | 1.91 ms | 16.54 req/s | 0.00 % |
| **Optimizado** | 1.22 ms | 1.63 ms | 16.57 req/s | 0.00 % |

## 5. Análisis
- **¿Qué métrica cambió más?** La latencia del percentil 95 (p95) mejoró notablemente, pasando de un promedio de **1.54 ms** en el baseline a **1.22 ms** en la versión optimizada.
- **¿La mejora fue consistente en las 3 ejecuciones?** Sí, la variabilidad fue extremadamente baja (desviaciones mínimas), manteniendo tiempos por debajo de 1.6 ms de manera constante.
- **¿Hubo errores?** No, la tasa de error fue de **0.00%** en todas las ejecuciones, cumpliendo con el umbral establecido.
- **¿Existe evidencia suficiente para recomendar el cambio?** Sí. La reducción en la latencia del p95 representa una mejora del **20.78%** en la velocidad de respuesta, superando el umbral mínimo del 20% propuesto para aceptar la optimización.

## 6. Conclusión técnica
Se recomienda aceptar e integrar la versión optimizada. Se evidencia una reducción consistente en los tiempos de respuesta de la API (p95 y p99) manteniendo un throughput (RPS) saludable y estable, sin comprometer la confiabilidad del servicio (0% errores).

## 7. Evidencias
- Comandos ejecutados localmente:
  ```cmd
  .\k6 run --summary-export evidencia/sesion30/resultados_baseline/run1.json evidencia/sesion30/benchmark_baseline.js
  .\k6 run --summary-export evidencia/sesion30/resultados_optimizado/run1.json evidencia/sesion30/benchmark_optimizado.js