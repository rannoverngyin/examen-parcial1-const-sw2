# Reporte técnico — Sesión 29

## Diseño de escenarios avanzados de carga

**Curso:** Construcción de Software II  
**Unidad:** Rendimiento y optimización  
**Estudiante:** Hidalgo Dairon  
**Repositorio:** `examen-parcial1-const-sw2`  
**Rama:** `feature/sesion29-escenarios-avanzados-hidalgo-dairon`  
**Sistema operativo:** Windows 11  
**Java:** 24.0.2  
**Maven:** 3.9.15  
**k6:** 2.1.0  
**Aplicación evaluada:** Spring Boot  
**BASE_URL:** `http://localhost:8080`  
**Fecha de ejecución:** 13 de julio de 2026  

---

## 1. Objetivo e hipótesis

### Objetivo

Diseñar y ejecutar un modelo de carga multiescenario con k6 que combine navegación de productos, operaciones de escritura y picos controlados de reportes. El experimento utiliza datos únicos, checks funcionales, métricas de negocio y thresholds globales y por endpoint.

### Hipótesis

La API debe sostener la carga mixta sin superar:

- 2 % de errores HTTP globales;
- p95 de 700 ms en el listado;
- p95 de 500 ms en la consulta del total;
- p95 de 1000 ms en los registros;
- p95 de 1500 ms en el reporte;
- menos de 10 errores de negocio;
- más de 97 % de escrituras exitosas;
- más de 98 % de checks exitosos.

También se espera que el sistema mantenga un comportamiento estable cuando el pico de reportes se duplique de 20 a 40 iteraciones por segundo.

---

## 2. Sistema bajo prueba

### Endpoints evaluados

```text
GET  /carga/productos
GET  /carga/productos/total
POST /carga/productos?nombre=<valor>
GET  /carga/productos/reporte
```

### Comportamiento del backend

- El listado devuelve una copia de la colección de productos.
- El endpoint `/total` devuelve la cantidad actual de productos.
- El POST agrega productos con nombres únicos.
- El reporte incorpora una espera controlada de 120 ms.
- La colección utiliza `CopyOnWriteArrayList` para soportar lecturas y escrituras concurrentes.

---

## 3. Modelo de carga

### 3.1 Consultas

| Propiedad | Valor |
|---|---|
| Ejecutor | `ramping-vus` |
| Modelo | Cerrado |
| Función | `consultarProductos` |
| Inicio | 0 VUs |
| Máximo | 25 VUs |
| Duración | 160 s |
| Pausa | Entre 1 y 3 s |
| Tag | `flujo=consulta` |

| Etapa | Duración | Objetivo |
|---:|---:|---:|
| 1 | 20 s | 0 → 10 VUs |
| 2 | 60 s | 10 VUs |
| 3 | 30 s | 10 → 25 VUs |
| 4 | 30 s | 25 VUs |
| 5 | 20 s | 25 → 0 VUs |

### 3.2 Registros

| Propiedad | Valor |
|---|---|
| Ejecutor | `constant-arrival-rate` |
| Modelo | Abierto |
| Función | `registrarProducto` |
| Inicio | 20 s |
| Tasa | 5 iteraciones/s |
| Duración | 120 s |
| VUs preasignados | 5 |
| VUs máximos | 30 |
| Tag | `flujo=registro` |

Los nombres se generaron con el patrón:

```text
Prod-<runId>-<VU>-<ITER>
```

### 3.3 Pico de reportes

| Propiedad | Escenario base | Pico doble |
|---|---:|---:|
| Ejecutor | `ramping-arrival-rate` | `ramping-arrival-rate` |
| Inicio | 80 s | 80 s |
| Tasa inicial | 2 iteraciones/s | 4 iteraciones/s |
| Pico | 20 iteraciones/s | 40 iteraciones/s |
| VUs preasignados | 10 | 20 |
| VUs máximos | 50 | 100 |
| Duración | 45 s | 45 s |

El escenario de pico doble modificó únicamente la intensidad del pico de reportes. Los escenarios de consultas, registros, código de la API y equipo se mantuvieron iguales.

---

## 4. Thresholds

| Métrica | Criterio |
|---|---|
| `http_req_failed` | `rate < 0.02` |
| `checks` | `rate > 0.98` |
| `http_req_duration{endpoint:listado}` | `p(95) < 700 ms` |
| `http_req_duration{endpoint:total}` | `p(95) < 500 ms` |
| `http_req_duration{endpoint:registro}` | `p(95) < 1000 ms` |
| `http_req_duration{endpoint:reporte}` | `p(95) < 1500 ms` |
| `successful_writes` | `rate > 0.97` |
| `business_errors` | `count < 10` |
| `report_latency` | `p(95) < 1500 ms` |

---

## 5. Resultados globales

| Métrica | Escenario base | Pico doble | Variación |
|---|---:|---:|---:|
| RPS | 21.58 | 25.13 | +16.45 % |
| Solicitudes HTTP | 3465 | 4044 | +16.71 % |
| Iteraciones | 2340 | 2936 | +25.47 % |
| `dropped_iterations` | 0 | 0 | Sin descartes |
| Errores HTTP | 0.00 % | 0.00 % | Sin errores |
| Checks exitosos | 100.00 % | 100.00 % | Sin degradación |
| Escrituras exitosas | 100.00 % | 100.00 % | Sin degradación |
| Errores de negocio | 0 | 0 | Sin errores |
| p95 del reporte | 123.28 ms | 123.06 ms | -0.18 % |

---

## 6. Resultados por endpoint

| Ejecución | Endpoint | p50 | p95 | p99 | Threshold p95 | Resultado |
|---|---|---:|---:|---:|---:|---|
| Base | Listado | 0.95 ms | 2.80 ms | 5.59 ms | < 700 ms | Cumple |
| Pico doble | Listado | 1.31 ms | 3.13 ms | 4.46 ms | < 700 ms | Cumple |
| Base | Total | 0.59 ms | 1.82 ms | 4.18 ms | < 500 ms | Cumple |
| Pico doble | Total | 0.65 ms | 1.81 ms | 2.61 ms | < 500 ms | Cumple |
| Base | Registro | 0.87 ms | 3.13 ms | 5.03 ms | < 1000 ms | Cumple |
| Pico doble | Registro | 1.19 ms | 2.99 ms | 4.09 ms | < 1000 ms | Cumple |
| Base | Reporte | 121.48 ms | 123.28 ms | 124.16 ms | < 1500 ms | Cumple |
| Pico doble | Reporte | 121.80 ms | 123.06 ms | 123.72 ms | < 1500 ms | Cumple |

---

## 7. Comparación técnica

### Throughput

El throughput aumentó de **21.58 RPS** a **25.13 RPS**, equivalente a una mejora de **16.45 %**. La ejecución de pico doble también completó 579 solicitudes HTTP adicionales.

### Latencia del reporte

El endpoint de reporte fue el más lento en ambas pruebas debido a la espera controlada de 120 ms.

Sin embargo, el incremento del pico de 20 a 40 iteraciones por segundo no produjo degradación relevante:

- p95 base: **123.28 ms**;
- p95 pico doble: **123.06 ms**;
- p99 base: **124.16 ms**;
- p99 pico doble: **123.72 ms**.

### Errores y trabajo descartado

Ambas ejecuciones finalizaron con:

- 0 % de errores HTTP;
- 100 % de checks exitosos;
- 100 % de escrituras exitosas;
- 0 errores de negocio;
- 0 `dropped_iterations`.

Esto demuestra que k6 logró iniciar toda la carga programada y que la API procesó las solicitudes sin fallos funcionales.

### Recuperación

No se observaron señales de saturación ni aumento de errores durante el pico. La prueba finalizó sin iteraciones interrumpidas ni trabajo descartado, lo que indica una recuperación adecuada después del descenso de la carga.

---

## 8. Cuello de botella e hipótesis

### Síntoma principal

El endpoint `/carga/productos/reporte` presentó una latencia considerablemente mayor que listado, total y registro.

### Hipótesis de causa

La diferencia se explica principalmente por la espera controlada de 120 ms incorporada en el método de reporte.

### Evidencia

- p95 del reporte cercano a 123 ms en ambas ejecuciones;
- p95 inferior a 3.2 ms en los demás endpoints;
- ausencia de errores HTTP;
- ausencia de `dropped_iterations`;
- throughput mayor en el escenario de pico doble;
- estabilidad del p95 y p99 al duplicar el pico.

La evidencia no muestra saturación del backend con la carga aplicada. El retraso del reporte es estable y corresponde al comportamiento diseñado.

---

## 9. Recursos observados

| Momento | CPU | Memoria | Observación |
|---|---:|---:|---|
| Inicio | No registrado | No registrado | Sin evidencia numérica |
| Consultas estables | No registrado | No registrado | Sin evidencia numérica |
| Registros activos | No registrado | No registrado | Sin evidencia numérica |
| Pico de reportes | No registrado | No registrado | Sin evidencia numérica |
| Recuperación | No registrado | No registrado | Sin evidencia numérica |

No se atribuye una causa de CPU o memoria porque estas métricas no fueron registradas durante las ejecuciones. La conclusión se limita a la latencia, throughput, errores, checks y trabajo descartado observados mediante k6.

---

## 10. Evaluación de thresholds

| Criterio | Escenario base | Pico doble |
|---|---|---|
| Errores HTTP menores al 2 % | Cumplido | Cumplido |
| Checks mayores al 98 % | Cumplido | Cumplido |
| Listado p95 menor a 700 ms | Cumplido | Cumplido |
| Total p95 menor a 500 ms | Cumplido | Cumplido |
| Registro p95 menor a 1000 ms | Cumplido | Cumplido |
| Reporte p95 menor a 1500 ms | Cumplido | Cumplido |
| Escrituras exitosas mayores al 97 % | Cumplido | Cumplido |
| Errores de negocio menores a 10 | Cumplido | Cumplido |
| `dropped_iterations` | 0 | 0 |
| Resultado general | **APROBADO** | **APROBADO** |

---

## 11. Recomendaciones priorizadas

1. Mantener el escenario de pico doble como prueba de regresión, ya que incrementa la demanda sin cambiar los demás factores.
2. Registrar CPU, memoria y GC en futuras ejecuciones para complementar la evidencia de k6.
3. Probar un pico superior a 40 iteraciones/s si se desea localizar el punto real de saturación.
4. Vigilar el crecimiento de `CopyOnWriteArrayList`, porque cada escritura genera una copia de la colección y el costo puede aumentar con conjuntos de datos mayores.
5. Repetir cada escenario al menos dos veces y comparar una corrida representativa o la mediana.
6. Mantener el mismo equipo, BASE_URL, código y configuración para asegurar comparabilidad.

---

## 12. Evidencias

```text
performance/sesion29/
├── escenarios-avanzados.js
├── escenario-pico-doble.js
├── evidencia/
│   ├── salida-k6.txt
│   ├── salida-pico-doble.txt
│   ├── resumen-sesion29.json
│   └── resumen-pico-doble.json
└── REPORTE_SESION29.md
```

### Escenario base

```powershell
& "C:\Program Files\k6\k6.exe" run `
  -e BASE_URL=http://localhost:8080 `
  --no-color `
  .\performance\sesion29\escenarios-avanzados.js 2>&1 |
Tee-Object `
  -FilePath .\performance\sesion29\evidencia\salida-k6.txt
```

### Pico doble

```powershell
& "C:\Program Files\k6\k6.exe" run `
  -e BASE_URL=http://localhost:8080 `
  --no-color `
  .\performance\sesion29\escenario-pico-doble.js 2>&1 |
Tee-Object `
  -FilePath .\performance\sesion29\evidencia\salida-pico-doble.txt
```

---

## 13. Preguntas de reflexión

### ¿Por qué `constant-arrival-rate` representa mejor una demanda externa que `constant-vus`?

`constant-arrival-rate` programa iteraciones a una tasa independiente de la duración de cada iteración. Representa mejor una demanda externa que continúa llegando aunque la aplicación empiece a responder más lentamente.

### ¿Qué diferencia existe entre un check fallido y un threshold incumplido?

Un check valida una condición funcional en una respuesta concreta. Un threshold evalúa una métrica agregada durante toda la ejecución y determina si la prueba cumple el criterio de calidad establecido.

### ¿Qué significa que `dropped_iterations` aumente aunque el error HTTP sea bajo?

Significa que k6 no logró iniciar parte de la carga programada. Esas iteraciones no generan una respuesta HTTP fallida porque nunca fueron ejecutadas.

### ¿Qué escenario conviene repetir primero después de una optimización?

Debe repetirse primero el escenario que mostró la mayor degradación o incumplió un threshold. En este experimento, el escenario de reportes es el candidato principal porque presenta la mayor latencia.

---

## 14. Conclusión

Bajo el modelo multiescenario, la API alcanzó **21.58 solicitudes/s** en la ejecución base y **25.13 solicitudes/s** con el pico doble.

El endpoint más lento fue el reporte, con un p95 de **123.28 ms** en el escenario base y **123.06 ms** en el pico doble. Ambas cifras permanecieron ampliamente por debajo del threshold de 1500 ms.

La duplicación del pico de reportes incrementó el throughput en **16.45 %** sin generar errores HTTP, errores de negocio, checks fallidos ni `dropped_iterations`. Los demás endpoints conservaron p95 inferiores a 3.2 ms.

Por tanto, la API sostuvo correctamente el modelo de carga aplicado y no mostró un punto de saturación con un pico de 40 iteraciones por segundo. La causa principal de la mayor latencia del reporte es la espera controlada de 120 ms incorporada en el servicio, no una degradación provocada por la carga.

Se recomienda conservar este escenario como prueba de regresión, registrar CPU y memoria en futuras corridas y aumentar gradualmente la tasa del pico para determinar el límite operativo real.