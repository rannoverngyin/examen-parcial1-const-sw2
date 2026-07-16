# Reporte -- Sesión 29

## 1. Objetivo e hipótesis
La API debe sostener la carga mixta (consultas, registros, pico de reportes)
sin superar p95 de 700 ms en consultas, p95 de 1000 ms en escrituras y 2% de
errores globales.

## 2. Entorno y versión evaluada
- Commit evaluado:
- Equipo y sistema operativo:
- BASE_URL:

## 3. Modelo de carga
- Escenarios y ejecutores:
  - `consultas` — ramping-vus (0→10→25→0 VUs)
  - `registros` — constant-arrival-rate (5 it/s, 120s)
  - `pico_reportes` — ramping-arrival-rate (2→20→0 it/s)
- Ramp-up, duración y tasas: ver tabla de perfil temporal de la guía (sección 3.2)
- Datos y variables de entorno: nombres únicos vía `runId` + `__VU` + `__ITER`; `BASE_URL` parametrizable

## 4. Umbrales
| Métrica | Umbral |
|---|---|
| http_req_failed | < 2% |
| checks | > 98% |
| http_req_duration{endpoint:listado} | p95 < 700 ms |
| http_req_duration{endpoint:total} | p95 < 500 ms |
| http_req_duration{endpoint:registro} | p95 < 1000 ms |
| http_req_duration{endpoint:reporte} | p95 < 1500 ms |
| successful_writes | > 97% |
| business_errors | < 10 |

## 5. Resultados
| Escenario | p95 | p99 | Error | RPS | Dropped iterations |
|---|--:|--:|--:|--:|--:|
| Consultas | | | | | |
| Registros | | | | | |
| Pico reportes | | | | | |

## 6. Recursos observados
- CPU al inicio / durante el pico / al finalizar:
- Memoria al inicio / durante el pico / al finalizar:
- Observaciones de GC (si aplica):

## 7. Cuello de botella e hipótesis
-

## 8. Recomendaciones priorizadas
1.
2.
3.

## 9. Evidencias y comandos
- `escenarios-avanzados.js`
- `evidencia/salida-k6.txt`
- `evidencia/resumen-sesion29.json`
- `evidencia/captura-recursos.png`
- `evidencia/backend.log`
