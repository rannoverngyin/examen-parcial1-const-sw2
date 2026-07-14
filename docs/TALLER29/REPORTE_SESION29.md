# Reporte – Sesión 29

**Estudiante:** Sergio Andrés Henríquez Moya  
**Curso:** Construcción de Software II  
**Sesión:** 29 – Diseño de escenarios avanzados de carga  
**Fecha:** 14 de julio de 2026  

## 1. Objetivo e hipótesis

### Objetivo

Diseñar y ejecutar un modelo avanzado de carga con k6 que represente de forma concurrente los principales comportamientos de los usuarios sobre la API de productos:

- Consultar el listado de productos y la cantidad total.
- Registrar productos utilizando nombres únicos.
- Generar un pico temporal de solicitudes sobre el endpoint de reporte.
- Comparar el escenario base con una segunda versión que duplica el pico de reportes.

### Hipótesis

La API debe sostener la carga mixta sin superar los siguientes límites:

- p95 menor de 700 ms para el listado de productos.
- p95 menor de 500 ms para la consulta del total.
- p95 menor de 1000 ms para el registro de productos.
- p95 menor de 1500 ms para el reporte.
- Tasa global de errores HTTP menor del 2 %.
- Tasa de checks correctos superior al 98 %.
- Tasa de escrituras exitosas superior al 97 %.
- Menos de 10 errores de negocio.
- Ausencia de iteraciones descartadas por falta de VUs.

## 2. Entorno y versión evaluada

| Elemento | Valor |
|---|---|
| Sistema operativo | Windows |
| Proyecto | examen-parcial1-const-sw2 |
| Framework | Spring Boot 3.5.14 |
| Java de ejecución | Java 26 |
| Versión objetivo del proyecto | Java 17 |
| Servidor embebido | Apache Tomcat 10.1.54 |
| Herramienta de carga | Grafana k6 |
| Tipo de ejecución | Local |
| URL evaluada | http://localhost:8080 |
| Almacenamiento de productos | Lista concurrente en memoria |
| Endpoint base | `/carga/productos` |

La aplicación fue ejecutada localmente mediante Maven Wrapper y expuesta en el puerto 8080. Los datos creados durante las pruebas permanecieron almacenados temporalmente en una colección `CopyOnWriteArrayList`.

## 3. Modelo de carga

### 3.1 Escenarios y ejecutores

| Escenario | Ejecutor | Modelo | Operaciones |
|---|---|---|---|
| Consultas | `ramping-vus` | Cerrado | Listar productos y consultar total |
| Registros | `constant-arrival-rate` | Abierto | Registrar productos con nombres únicos |
| Pico de reportes | `ramping-arrival-rate` | Abierto | Consultar el reporte de productos |

El modelo cerrado de consultas representa usuarios que navegan, esperan la respuesta y realizan una nueva iteración.

Los escenarios de registros y reportes utilizan modelos abiertos. En estos casos, las nuevas solicitudes se programan independientemente del tiempo que demoren las anteriores.

### 3.2 Escenario de consultas

El escenario de consultas utilizó la siguiente distribución:

| Periodo | VUs objetivo |
|---|---:|
| 0–20 segundos | Incremento de 0 a 10 VUs |
| 20–80 segundos | 10 VUs |
| 80–110 segundos | Incremento de 10 a 25 VUs |
| 110–140 segundos | 25 VUs |
| 140–160 segundos | Descenso de 25 a 0 VUs |

Cada iteración realizó dos solicitudes:

1. `GET /carga/productos`
2. `GET /carga/productos/total`

Después de cada iteración se agregó una pausa aleatoria de entre uno y tres segundos para representar el tiempo de espera de un usuario real.

### 3.3 Escenario de registros

El escenario de registros comenzó después de 20 segundos y utilizó:

- Tasa constante de 5 registros por segundo.
- Duración de 120 segundos.
- 5 VUs preasignados.
- Máximo de 30 VUs.
- Nombres de productos generados de forma dinámica.

El nombre de cada producto combinó:

- El identificador de la ejecución.
- El número del usuario virtual.
- El número de iteración.

Ejemplo:

```text
Prod-1784038549042-4-25
```

Esto permitió evitar que todas las solicitudes utilizaran registros repetidos.

### 3.4 Escenario base de reportes

El escenario base inició en el segundo 80 con una tasa inicial de 2 iteraciones por segundo.

| Etapa | Duración | Tasa objetivo |
|---|---:|---:|
| Incremento | 15 segundos | 2 → 20 iteraciones/s |
| Mantenimiento | 15 segundos | 20 iteraciones/s |
| Descenso | 15 segundos | 20 → 0 iteraciones/s |

### 3.5 Escenario con pico doble

Para el reto se creó una segunda versión del script manteniendo sin cambios los escenarios de consultas y registros.

Solamente se duplicó el pico del endpoint de reporte:

| Etapa | Duración | Tasa objetivo |
|---|---:|---:|
| Incremento | 15 segundos | 2 → 40 iteraciones/s |
| Mantenimiento | 15 segundos | 40 iteraciones/s |
| Descenso | 15 segundos | 40 → 0 iteraciones/s |

Se realizó una ejecución representativa del escenario base y una ejecución representativa del escenario con pico doble.

### 3.6 Variables de entorno

La URL de la API se proporcionó mediante la variable:

```text
BASE_URL=http://localhost:8080
```

Cuando no se especifica esta variable, el script utiliza `http://localhost:8080` como valor predeterminado.

## 4. Umbrales

| Métrica | Umbral |
|---|---:|
| `http_req_failed` | Menor del 2 % |
| `checks` | Mayor del 98 % |
| Listado p95 | Menor de 700 ms |
| Total p95 | Menor de 500 ms |
| Registro p95 | Menor de 1000 ms |
| Reporte p95 | Menor de 1500 ms |
| `successful_writes` | Mayor del 97 % |
| `business_errors` | Menor de 10 |
| `report_latency` p95 | Menor de 1500 ms |

Los umbrales por endpoint permitieron comprobar de manera independiente si alguna operación se degradaba más que las demás.

## 5. Resultados

### 5.1 Ejecución base

| Escenario | Carga real | p95 | Error | RPS aproximado | Dropped iterations | Resultado |
|---|---:|---:|---:|---:|---:|---|
| Consultas | Hasta 25 VUs | N/D | 0 % observado | ≈ 13.90 req/s | 0 | Cumple funcionalmente |
| Registros | 5 iteraciones/s | N/D | 0 % observado | ≈ 5.01 req/s | 0 | Cumple funcionalmente |
| Pico de reportes | Hasta 20 iteraciones/s | N/D | 0 % observado | ≈ 13.67 req/s | 0 | Cumple funcionalmente |

#### Resumen global de la ejecución base

| Métrica | Resultado |
|---|---:|
| Duración aproximada | 161.2 segundos |
| Iteraciones completadas | 2336 |
| Iteraciones interrumpidas | 0 |
| Solicitudes HTTP estimadas | 3457 |
| Throughput global estimado | 21.44 req/s |
| Pico configurado de reportes | 20 iteraciones/s |
| Dropped iterations observadas | 0 |

La ejecución base finalizó correctamente con los tres escenarios al 100 % y sin iteraciones interrumpidas.

Los valores p95 no pudieron recuperarse de esta ejecución porque `handleSummary()` reemplazó el resumen habitual de la consola y el JSON base disponible correspondía a una ejecución previa abortada durante la verificación de disponibilidad de la API.

Por esta razón, los valores de p95 de la ejecución base se registran como `N/D` en lugar de utilizar valores no sustentados por la evidencia.

### 5.2 Ejecución con pico doble

| Escenario | Carga real | p95 | Error | RPS aproximado | Dropped iterations | Resultado |
|---|---:|---:|---:|---:|---:|---|
| Consultas | Hasta 25 VUs | 2.29 ms | 0 % | 13.98 req/s | 0 | Cumple |
| Registros | 5 iteraciones/s | 2.15 ms | 0 % | 5.01 req/s | 0 | Cumple |
| Pico doble de reportes | Hasta 40 iteraciones/s | 134.81 ms | 0 % | 27.00 req/s | 0 | Cumple |

El endpoint `/carga/productos/total` obtuvo adicionalmente un p95 de 1.52 ms, cumpliendo ampliamente su umbral de 500 ms.

#### Resumen global del pico doble

| Métrica | Resultado |
|---|---:|
| Duración | 160.49 segundos |
| Solicitudes HTTP | 4061 |
| Throughput global | 25.30 req/s |
| Iteraciones completadas | 2938 |
| Iteraciones interrumpidas | 0 |
| Checks correctos | 4060 de 4060 |
| Porcentaje de checks correctos | 100 % |
| Errores HTTP | 0 % |
| Escrituras exitosas | 601 de 601 |
| Tasa de escrituras exitosas | 100 % |
| Errores de negocio | 0 |
| Dropped iterations | 0 |
| VUs utilizados como máximo | 30 |
| p95 global HTTP | 131.10 ms |
| p95 del reporte | 134.81 ms |

Todos los thresholds definidos fueron cumplidos durante la ejecución con el pico duplicado.

### 5.3 Comparación de las ejecuciones

| Métrica | Escenario base | Pico doble | Variación |
|---|---:|---:|---:|
| Pico máximo de reportes | 20 iteraciones/s | 40 iteraciones/s | +100 % |
| Iteraciones completadas | 2336 | 2938 | +602 |
| Incremento de iteraciones | — | — | +25.77 % |
| Solicitudes HTTP | ≈ 3457 | 4061 | ≈ +604 |
| Throughput global | ≈ 21.44 req/s | 25.30 req/s | ≈ +18 % |
| Errores HTTP | 0 % observado | 0 % | Sin cambio |
| Dropped iterations | 0 | 0 | Sin cambio |
| p95 del reporte | N/D | 134.81 ms | No comparable |
| Iteraciones interrumpidas | 0 | 0 | Sin cambio |

Los valores marcados con el símbolo `≈` fueron estimados a partir de las iteraciones completadas, las tasas configuradas y la duración de la ejecución.

El escenario con pico doble procesó 602 iteraciones adicionales, lo que representa un incremento aproximado del 25.77 % en el trabajo completado.

A pesar de duplicar la tasa máxima del reporte, no se observaron errores HTTP, errores de negocio ni iteraciones descartadas.

## 6. Recursos observados

La JVM fue identificada mediante:

```powershell
jps -l
```

Inicialmente se intentó consultar la memoria nativa mediante:

```powershell
jcmd <PID> VM.native_memory summary
```

La JVM respondió:

```text
Native memory tracking is not enabled
```

Esto indicó que la aplicación había sido iniciada sin activar Native Memory Tracking.

Para habilitarlo, la aplicación debía iniciarse mediante:

```powershell
.\mvnw spring-boot:run "-Dspring-boot.run.jvmArguments=-XX:NativeMemoryTracking=summary"
```

Después de habilitarlo, el seguimiento podía realizarse con:

```powershell
jcmd <PID> VM.native_memory baseline
jcmd <PID> VM.native_memory summary.diff
```

No se dispone de valores numéricos de CPU y memoria dentro de los resultados compartidos. Por ello, no se afirma una causa de saturación de recursos sin una captura o registro adicional.

No obstante, desde el punto de vista de k6, no aparecieron señales de saturación funcional:

- No hubo errores HTTP.
- No hubo errores de negocio.
- No hubo iteraciones descartadas.
- No hubo iteraciones interrumpidas.
- La aplicación completó la carga programada.
- El número real de VUs se mantuvo por debajo del máximo configurado.

La aplicación también logró recuperarse después del descenso del pico y completó normalmente los escenarios restantes.

## 7. Cuello de botella e hipótesis

### Síntoma observado

El endpoint con mayor latencia fue:

```text
GET /carga/productos/reporte
```

Su p95 durante el escenario con pico doble fue de:

```text
134.81 ms
```

Los demás endpoints registraron p95 inferiores a 3 ms:

- Listado: 2.29 ms.
- Total: 1.52 ms.
- Registro: 2.15 ms.

### Hipótesis

El método `reporte()` contiene una espera bloqueante:

```java
Thread.sleep(120);
```

Esta espera explica que la latencia mínima del reporte se encuentre cerca de 120 ms y que su p95 sea considerablemente mayor que el de los demás endpoints.

Cada solicitud al reporte mantiene ocupado un hilo del servidor durante aproximadamente 120 ms, incluso cuando no se está realizando procesamiento real.

### Evidencia

- p95 del reporte: 134.81 ms.
- Espera programada en el servicio: 120 ms.
- p95 del listado: 2.29 ms.
- p95 del total: 1.52 ms.
- p95 del registro: 2.15 ms.
- Errores HTTP: 0 %.
- Errores de negocio: 0.
- Dropped iterations: 0.
- Checks correctos: 100 %.

### Interpretación

No se identificó un cuello de botella crítico bajo la carga evaluada. Sin embargo, el endpoint de reporte fue claramente la operación más costosa.

La diferencia entre sus tiempos y los demás endpoints permite relacionar la mayor latencia con la espera bloqueante implementada en el servicio.

Aunque la aplicación soportó el pico de 40 iteraciones por segundo, un incremento mayor podría provocar agotamiento de hilos y aumento de la latencia.

## 8. Recomendaciones priorizadas

### 1. Eliminar la espera bloqueante del reporte

La mejora prioritaria consiste en retirar o reemplazar:

```java
Thread.sleep(120);
```

por el procesamiento real requerido o por una operación que no mantenga bloqueado innecesariamente el hilo.

La principal métrica que debería mejorar es:

```text
http_req_duration{endpoint:reporte}
```

Se espera que disminuyan:

- El promedio del reporte.
- Su mediana.
- Su p95.
- El uso concurrente de hilos durante el pico.

### 2. Controlar el crecimiento de la colección

Cada ejecución agrega aproximadamente 601 productos nuevos y estos permanecen almacenados en memoria mientras la aplicación continúa activa.

Se recomienda:

- Reiniciar los datos antes de cada prueba.
- Implementar un endpoint de limpieza exclusivo para pruebas.
- Utilizar una base de datos temporal.
- Definir un límite para la colección.

Esto permitirá que las ejecuciones sean comparables y evitará que el tamaño de la respuesta del listado aumente indefinidamente.

### 3. Registrar CPU y memoria en futuras ejecuciones

Se recomienda establecer un baseline de memoria antes de ejecutar k6:

```powershell
jcmd <PID> VM.native_memory baseline
```

Durante el pico:

```powershell
jcmd <PID> VM.native_memory summary.diff
```

También se debe registrar el proceso Java:

```powershell
Get-Process -Id <PID> |
    Select-Object Id, ProcessName, CPU,
        @{Name="MemoriaMB"; Expression={
            [math]::Round($_.WorkingSet64 / 1MB, 2)
        }}
```

### 4. Repetir primero el escenario de reportes

Después de aplicar la mejora, debe repetirse primero el escenario de pico porque fue el flujo con mayor latencia.

Posteriormente se debe ejecutar nuevamente el modelo completo para confirmar que la modificación no afectó negativamente a los escenarios de consultas y registros.

## 9. Evidencias y comandos

### 9.1 Archivos principales

```text
performance/sesion29/
├── escenarios-avanzados.js
├── escenario-pico-doble.js
├── REPORTE_SESION29.md
└── evidencia/
    ├── salida-base-2.txt
    ├── salida-pico-doble.txt
    ├── resumen-sesion29.json
    ├── resumen-pico-doble.json
    ├── captura-recursos.png
    └── backend.log
```

El JSON base disponible correspondía a una ejecución abortada durante el `setup()` y no fue utilizado para establecer valores p95.

El resumen válido del escenario con pico doble fue utilizado para obtener los percentiles, checks, errores, throughput e iteraciones.

### 9.2 Ejecución de Spring Boot

```powershell
.\mvnw spring-boot:run
```

Con Native Memory Tracking:

```powershell
.\mvnw spring-boot:run "-Dspring-boot.run.jvmArguments=-XX:NativeMemoryTracking=summary"
```

### 9.3 Validación de los endpoints

```powershell
curl.exe http://localhost:8080/carga/productos
curl.exe http://localhost:8080/carga/productos/total
curl.exe -X POST "http://localhost:8080/carga/productos?nombre=Monitor"
curl.exe http://localhost:8080/carga/productos/reporte
```

### 9.4 Ejecución del escenario base

```powershell
k6 run `
  -e BASE_URL=http://localhost:8080 `
  .\escenarios-avanzados.js |
  Tee-Object -FilePath .\evidencia\salida-base-2.txt
```

### 9.5 Ejecución del escenario con pico doble

```powershell
k6 run `
  -e BASE_URL=http://localhost:8080 `
  .\escenario-pico-doble.js |
  Tee-Object -FilePath .\evidencia\salida-pico-doble.txt
```

### 9.6 Observación del proceso Java

```powershell
jps -l
jcmd <PID> VM.native_memory summary
```

### 9.7 Registro con Git

```powershell
git checkout -b feature/henriquez_sergio_s29
git add performance/sesion29
git add src/main/java
git commit -m "Diseña escenarios avanzados de carga"
git push -u origin feature/henriquez_sergio_s29
```

## 10. Preguntas de reflexión

### 10.1 ¿Por qué `constant-arrival-rate` representa mejor una demanda externa que `constant-vus`?

`constant-arrival-rate` programa una cantidad fija de iteraciones por unidad de tiempo, independientemente de cuánto demoren las respuestas.

Esto representa mejor una demanda externa porque las solicitudes pueden continuar llegando incluso cuando la aplicación comienza a responder lentamente.

En cambio, con `constant-vus`, una nueva iteración depende de que el usuario virtual termine la anterior. Si la aplicación se vuelve lenta, también disminuye automáticamente la cantidad de nuevas solicitudes generadas.

### 10.2 ¿Qué diferencia existe entre un check fallido y un threshold incumplido?

Un check valida una condición funcional sobre una respuesta individual.

Ejemplo:

```javascript
check(res, {
    'reporte HTTP 200': r => r.status === 200,
});
```

Un threshold evalúa una métrica agregada durante toda la prueba.

Ejemplo:

```javascript
'http_req_duration{endpoint:reporte}': ['p(95)<1500']
```

Puede existir un pequeño número de checks fallidos y aun así cumplirse un threshold, dependiendo del límite establecido.

Un threshold incumplido determina que el criterio global de calidad de la prueba no fue alcanzado.

### 10.3 ¿Qué significa que `dropped_iterations` aumente aunque el error HTTP sea bajo?

Significa que las solicitudes que lograron iniciarse respondieron correctamente, pero k6 no pudo comenzar todas las iteraciones programadas.

Las causas posibles incluyen:

- Falta de VUs disponibles.
- `maxVUs` demasiado bajo.
- Respuestas demasiado lentas.
- Saturación del servidor.
- Agotamiento de conexiones o hilos.

Por eso, una tasa baja de errores HTTP no garantiza que el sistema haya absorbido toda la demanda solicitada.

### 10.4 ¿Qué escenario conviene repetir primero después de una optimización y por qué?

Debe repetirse primero el escenario de pico de reportes porque fue el endpoint con el p95 más alto y contiene una espera bloqueante identificada.

Esto permitirá comprobar directamente si la optimización redujo la latencia.

Después debe repetirse el modelo multiescenario completo para confirmar que la mejora no produjo regresiones en el listado, el total o el registro de productos.

## 11. Conclusión

Bajo el modelo de carga mixto, la ejecución base completó 2336 iteraciones, mientras que la ejecución con pico doble completó 2938 iteraciones.

El escenario con pico doble procesó 4061 solicitudes HTTP y alcanzó un throughput global de 25.30 solicitudes por segundo. Todos sus checks funcionales fueron satisfactorios y no se registraron errores HTTP, errores de negocio ni iteraciones descartadas.

El escenario más afectado fue el pico de reportes, con un p95 de 134.81 ms y una tasa de error del 0 %. Sin embargo, este resultado permaneció ampliamente por debajo del umbral de 1500 ms.

La diferencia respecto de los demás endpoints se relaciona con la espera bloqueante de 120 ms implementada mediante `Thread.sleep(120)`. Por ello, esta operación representa el principal punto de optimización identificado.

Se recomienda eliminar o sustituir la espera bloqueante y repetir primero el escenario de reportes. La métrica que debería presentar la mejora más clara es el p95 de `http_req_duration{endpoint:reporte}`.

La comparación cuantitativa del p95 entre la ejecución base y el pico doble quedó limitada porque el resumen JSON válido de la ejecución base no estuvo disponible. No obstante, el incremento del 25.77 % en las iteraciones completadas, sin errores ni descartes observados, indica que la API pudo absorber el aumento de carga aplicado en el escenario con pico doble.