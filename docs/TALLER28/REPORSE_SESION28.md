# Reporte técnico - Sesión 28

## 1. Escenario
- **Endpoint:** `GET http://localhost:8080/carga/productos`
- **Equipo y sistema operativo:** Equipo local con Windows y PowerShell.
- **Perfil/configuración:** Aplicación Spring Boot ejecutada localmente con Java 17. La versión base utilizó `app.carga.delay-ms=120` y la versión optimizada utilizó `app.carga.delay-ms=20`.
- **Etapas de carga:**
  - 10 segundos de incremento hasta 10 VUs.
  - 20 segundos manteniendo 10 VUs.
  - 10 segundos de incremento hasta 30 VUs.
  - 20 segundos manteniendo 30 VUs.
  - 10 segundos de reducción hasta 0 VUs.
- **Umbrales:**
  - `http_req_failed`: tasa menor al 1 %.
  - `http_req_duration`: p95 menor a 500 ms.
  - `http_req_duration`: p99 menor a 800 ms.
  - `checks`: tasa mayor al 99 %.
- **Commit evaluado:** .

## 2. Resultados
 Versión | p50 | p95 | p99 | RPS | Errores | CPU | Memoria |
|---|---:|---:|---:|---:|---:|---:|---:|
| Base (`delay-ms=120`) | 128.74 ms | 135.18 ms | No visible en la salida | 15.17 req/s | 0.00 % | No registrado | No registrado |
| Optimizada (`delay-ms=20`) | 29.73 ms | 36.64 ms | No visible en la salida | 16.81 req/s | 0.00 % | No registrado | No registrado |

El smoke test válido de la configuración base obtuvo 100 % de checks exitosos, 0 % de solicitudes fallidas y un p95 de 172.26 ms. Después de la optimización, el smoke test obtuvo 100 % de checks exitosos, 0 % de solicitudes fallidas y un p95 de 76.43 ms.

En la prueba de carga base se completaron 1078 solicitudes, con 100 % de checks exitosos y sin errores HTTP. En la prueba optimizada se completaron 1181 solicitudes, también con 100 % de checks exitosos y sin errores HTTP.

Mejora p95 (%) = ((135.18 - 36.64) / 135.18) × 100
Mejora p95 (%) = 72.90 %

El throughput aumentó aproximadamente un 10.82 %, al pasar de 15.17 a 16.81 solicitudes por segundo.

## 3. Hallazgos
Síntoma principal: La versión base presentó una latencia mayor debido al retraso artificial configurado en 120 ms. Al reducirlo a 20 ms, la latencia p50 y p95 disminuyó de manera considerable.

Hipótesis de cuello de botella: El retraso configurado en CargaService era el principal factor que limitaba el tiempo de respuesta del endpoint. Con los resultados disponibles no se puede afirmar que la CPU o la memoria hayan sido cuellos de botella, porque esos recursos no fueron registrados durante la prueba.

Evidencia que la sustenta: El p95 disminuyó de 135.18 ms a 36.64 ms, equivalente a una mejora aproximada del 72.90 %. El p50 disminuyó de 128.74 ms a 29.73 ms. Al mismo tiempo, el throughput aumentó de 15.17 a 16.81 solicitudes por segundo, los checks se mantuvieron en 100 % y la tasa de errores permaneció en 0 %.

## 4. Decisión
Las ejecuciones base y optimizada cumplieron los thresholds definidos. En ambos casos, la tasa de errores fue menor al 1 %, los checks superaron el 99 % y el p95 se mantuvo por debajo de 500 ms. Además, k6 indicó que el threshold configurado para http_req_duration, que también incluye p99 menor a 800 ms, fue aprobado.

La versión optimizada ofrece un mejor comportamiento, ya que reduce de forma significativa la latencia sin aumentar los errores ni afectar las validaciones funcionales. Por ello, se recomienda mantener app.carga.delay-ms=20 para el escenario evaluado.

Como acción adicional, se recomienda repetir el monitoreo registrando CPU y memoria al inicio, con 10 VUs y con 30 VUs. Esto permitirá confirmar que la mejora no oculta un consumo excesivo de recursos y completar todas las evidencias solicitadas por la guía.