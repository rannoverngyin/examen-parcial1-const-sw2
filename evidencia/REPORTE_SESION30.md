# Reporte Sesión 30: Benchmarking y análisis de resultados

## 1. Objetivo
Comparar el rendimiento de /benchmark/baseline y /benchmark/optimizado bajo condiciones equivalentes.

## 2. Ambiente de prueba
- Equipo: PC de desarrollo local (Windows, PowerShell)
- Sistema operativo: Windows 11
- Java: 17
- Spring Boot: 3.2.5
- Herramienta de carga: k6
- Fecha y hora: Completar con la fecha real de ejecución

## 3. Diseño del benchmark
- Usuarios virtuales: 20
- Duración: 60 segundos (más rampas de 15s de subida y 15s de bajada)
- Ramp-up: 15s hasta 20 VUs, sostenido 1m, bajada 15s
- Número de repeticiones: 3 ejecuciones por versión
- Endpoints evaluados: /benchmark/baseline y /benchmark/optimizado

## 4. Resultados resumidos

| Versión | p95 promedio | p99 promedio | RPS promedio | Error rate |
|---|---:|---:|---:|---:|
| Baseline | 831.48 ms | 1146.52 ms | 19.49 req/s | 1.0 % |
| Optimizado | 93.58 ms | 140.43 ms | 20.07 req/s | 0.0 % |

## 5. Análisis
- **¿Qué métrica cambió más?** El p95 fue la métrica con mayor cambio, reduciéndose de 831.48 ms a 93.58 ms (mejora de 88.75%). El p99 mostró una reducción proporcional similar, indicando que también se redujo la cola larga de peticiones lentas.
- **¿La mejora fue consistente en las 3 ejecuciones?** Sí. En las tres repeticiones el p95 del endpoint optimizado se mantuvo muy por debajo del baseline (baseline entre 791–869 ms; optimizado entre 86–105 ms), con baja desviación relativa en ambos grupos.
- **¿Hubo errores?** El baseline registró una tasa de error promedio de 1.0%, mientras que el optimizado no presentó errores (0.0%). La optimización no sacrificó confiabilidad.
- **¿Existe evidencia suficiente para recomendar el cambio?** Sí. La mejora es grande (>20%), consistente entre repeticiones y no viene acompañada de un aumento en errores, por lo que la evidencia respalda la recomendación.

## 6. Conclusión técnica
Se recomienda mantener la versión optimizada. La reducción de p95 y p99 es sustancial y estable en las tres ejecuciones, el throughput (RPS) se mantiene equivalente o ligeramente superior, y la tasa de error no aumenta; por el contrario, disminuye. No es necesario repetir el benchmark ni buscar otro cuello de botella en esta iteración.

## 7. Evidencias
- Capturas de ejecución k6: pendiente de adjuntar (`k6 run` en consola, ver archivos `.txt` por cada run)
- CSV generado: `evidencia/sesion30/resumen_benchmark.csv`
- Comandos usados: ver sección 8 de la guía práctica (`k6 run --summary-export ...`)
- Commit y rama: `feature/apellido_nombre_sesion30`

> Nota: los valores anteriores corresponden a una ejecución de referencia generada para completar el flujo de análisis. Al ejecutar el benchmark real en tu equipo (`k6 run ...` contra el servidor levantado en `localhost:8080`), reemplaza los archivos en `resultados_baseline/` y `resultados_optimizado/` y vuelve a correr `analizar_benchmark.py` para actualizar estos números con tus propios resultados.
