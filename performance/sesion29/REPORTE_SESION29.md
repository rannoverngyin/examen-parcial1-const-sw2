# Reporte – Sesión 29

## 1. Objetivo e hipótesis
El objetivo de esta sesión es diseñar y evaluar un modelo de carga multiescenario complejo con k6 para medir el comportamiento de un backend en Spring Boot bajo diferentes patrones de carga simultáneos (navegación cerrada, registros de llegada fija y picos controlados de reportes lentos).

**Hipótesis:**
El backend en Spring Boot responderá eficientemente con latencias mínimas para consultas y escrituras directas. Sin embargo, el endpoint de reporte (que introduce un retardo artificial de 120 ms) presentará una latencia significativamente mayor y consumirá más tiempo de procesamiento durante su pico de tráfico, aunque debería mantenerse por debajo del umbral de p(95) < 1500 ms si el pool de hilos de Tomcat no se satura.

## 2. Entorno y versión evaluada
- **Sistema Operativo:** Windows 11 (arquitectura amd64)
- **Java:** JDK 17.0.12 (HotSpot JVM)
- **Framework:** Spring Boot v3.5.14
- **Herramienta de Carga:** k6 v2.1.0 (local)
- **Endpoints evaluados:**
  - `GET /carga/productos` (listado)
  - `GET /carga/productos/total` (total)
  - `POST /carga/productos` (registro)
  - `GET /carga/productos/reporte` (reporte)

## 3. Modelo de carga
El modelo consta de tres escenarios concurrentes ejecutados en paralelo sobre el script [escenarios-avanzados.js](file:///c:/Users/51913/examen-parcial1-const-sw2-clone4/performance/sesion29/escenarios-avanzados.js):

### Escenarios y ejecutores
1. **Consultas (Navegación):** Modelo cerrado con `ramping-vus`.
   - Simula usuarios que navegan por el listado y el total de productos y luego esperan un tiempo aleatorio (de 1 a 3 segundos).
   - **Ramp-up/Duración:** Sube de 0 a 10 VUs (0–20s), mantiene 10 VUs (20–80s), sube a 25 VUs (80–110s), mantiene 25 VUs (110–140s), y baja a 0 VUs (140–160s).
   - **Tags:** `flujo: consulta`
2. **Registros (Escritura):** Modelo abierto con `constant-arrival-rate`.
   - Envía solicitudes de escritura constantes independientemente del tiempo de respuesta del servidor.
   - **Tasa:** Tasa fija de 5 iteraciones por segundo a partir del segundo 20 (`startTime: '20s'`) durante 120s.
   - **VUs preasignados:** 5 (máximo 30).
   - **Tags:** `flujo: registro`
3. **Pico de reportes (Lectura lenta):** Modelo abierto con `ramping-arrival-rate`.
   - Simula un pico súbito de consultas al endpoint de reporte lento (120 ms).
   - **Tasa y etapas:** Inicia en 2 iteraciones/s en el segundo 80 (`startTime: '80s'`). Sube a 20 iteraciones/s en 15s, mantiene 20 iteraciones/s por 15s y baja a 0 en 15s (duración total del escenario: 45s).
   - **VUs preasignados:** 10 (máximo 50).
   - **Tags:** `flujo: reporte`

## 4. Umbrales (Thresholds)
Se definieron los siguientes criterios de aceptación globales y específicos:
- `http_req_failed` < 2% (Tasa de fallos)
- `checks` > 98% (Validaciones exitosas)
- `http_req_duration{endpoint:listado}`: p(95) < 700 ms
- `http_req_duration{endpoint:total}`: p(95) < 500 ms
- `http_req_duration{endpoint:registro}`: p(95) < 1000 ms
- `http_req_duration{endpoint:reporte}`: p(95) < 1500 ms
- `successful_writes` (POST exitosos): rate > 97%
- `business_errors` (Errores de negocio): count < 10
- `report_latency`: p(95) < 1500 ms

## 5. Resultados
Todos los umbrales de rendimiento definidos se cumplieron satisfactoriamente. A continuación se presentan las métricas de rendimiento extraídas de [resumen-sesion29.json](file:///c:/Users/51913/examen-parcial1-const-sw2-clone4/performance/sesion29/evidencia/resumen-sesion29.json):

| Escenario | p95 (ms) | p99 / Max (ms) | Error (%) | RPS Promedio | Dropped iterations | Resultado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Consultas (Listado)** | 4.41 ms | 25.66 ms | 0.00 % | 6.9 RPS | 0 | Cumple |
| **Consultas (Total)** | 4.06 ms | 31.41 ms | 0.00 % | 6.9 RPS | 0 | Cumple |
| **Registros** | 4.30 ms | 11.76 ms | 0.00 % | 5.0 RPS | 0 | Cumple |
| **Pico reportes** | 124.30 ms | 144.95 ms | 0.00 % | 13.6 RPS | 0 | Cumple |

## 6. Recursos observados
El monitoreo de recursos del proceso Java (PID 5700) registrado en [recursos.txt](file:///c:/Users/51913/examen-parcial1-const-sw2-clone4/performance/sesion29/evidencia/recursos.txt) muestra el siguiente perfil de uso:
- **Consumo de Memoria:**
  - Al iniciar el test (línea base): **121.65 MB**
  - Durante el escenario de registros: **125.41 MB**
  - Durante el pico de reportes (carga máxima): **126.80 MB**
  - Al finalizar la prueba (estabilizado): **127.39 MB**
  - *Interpretación:* El crecimiento de la memoria Working Set fue lineal y mínimo (~5.74 MB), provocado por la inserción concurrente en el `CopyOnWriteArrayList` en memoria. No se observó retención indebida ni fugas de memoria.
- **Consumo de CPU (Tiempo de procesador acumulado):**
  - CPU inicial: **6.22 s**
  - CPU final: **12.51 s**
  - *Interpretación:* Durante los 160 segundos de la prueba, el proceso consumió un total acumulado de 6.29 segundos de tiempo de CPU. Esto representa un promedio de utilización de CPU muy bajo (~3.9% de un solo núcleo), confirmando que el backend operó con un margen de holgura sumamente alto.

## 7. Cuello de botella e hipótesis
A pesar del excelente desempeño del backend, la latencia promedio del endpoint de reporte (`/carga/productos/reporte`) se sitúa de forma constante en **122.57 ms** con un p(95) de **124.30 ms**.

**Diagnóstico:**
Este comportamiento no es un cuello de botella físico del sistema, sino que se debe enteramente al retardo simulado mediante `Thread.sleep(120)` en la clase `CargaProductoService.java`. Sin embargo, en una aplicación productiva real, este tipo de operaciones bloqueantes son críticas: si la tasa de solicitudes de reportes aumentara por encima del límite de hilos activos de Tomcat (por defecto 200), el pool de hilos se saturaría rápidamente, elevando los tiempos de espera y provocando caídas en los demás endpoints rápidos de la aplicación (listado y total).

## 8. Recomendaciones priorizadas
1. **Optimización del procesamiento lento:** Reemplazar implementaciones bloqueantes de reportes por procesamiento asíncrono (`CompletableFuture` o Spring WebFlux/Reactive) o implementar una estrategia de caché (ej. `@Cacheable` de Spring con desalojo controlado) si los reportes no requieren datos en tiempo real estricto.
2. **Uso de colecciones concurrentes adecuadas:** La implementación actual utiliza `CopyOnWriteArrayList`. Aunque es segura para concurrencia, esta estructura es costosa en operaciones de escritura porque realiza una copia completa del arreglo interno cada vez que se agrega un producto. Para alta concurrencia de escrituras, se recomienda migrar a `ConcurrentHashMap` o utilizar bases de datos físicas relacionales/NoSQL indexadas.
3. **Optimización del pool de base de datos y de Tomcat:** Asegurar que los límites de hilos en Tomcat (`server.tomcat.threads.max`) y el pool de conexiones a bases de datos (ej. HikariCP) estén correctamente dimensionados para absorber picos súbitos sin degradar la latencia de las transacciones rápidas.

## 9. Evidencias y comandos
- **Comando de ejecución del backend:**
  ```powershell
  ./mvnw spring-boot:run | Tee-Object -FilePath performance/sesion29/evidencia/backend.log
  ```
- **Comando de ejecución de k6:**
  ```powershell
  & "C:\Program Files\k6\k6.exe" run -e BASE_URL=http://localhost:8080 performance/sesion29/escenarios-avanzados.js | Tee-Object -FilePath performance/sesion29/evidencia/salida-k6.txt
  ```
- **Comando de monitoreo de CPU y memoria:**
  ```powershell
  for ($i=0; $i -lt 18; $i++) { Get-Date | Out-String | Add-Content -Path evidencia/recursos.txt; Get-Process -Name "java" -ErrorAction SilentlyContinue | Select-Object Id, CPU, @{Name="RAM(MB)"; Expression={[math]::round($_.WorkingSet / 1MB, 2)}} | Out-String | Add-Content -Path evidencia/recursos.txt; Start-Sleep -Seconds 10 }
  ```

---

## Preguntas de reflexión

### • ¿Por qué constant-arrival-rate representa mejor una demanda externa que constant-vus?
Porque `constant-arrival-rate` simula un comportamiento de llegada abierta de peticiones (modelo abierto), en el que los usuarios envían solicitudes de manera constante e independiente del tiempo de respuesta del servidor (por ejemplo, transacciones de pago o búsquedas web reales). Si el servidor empieza a demorarse, las peticiones siguen llegando al mismo ritmo, lo que expone fallas en las colas de solicitudes. En contraste, `constant-vus` es un modelo cerrado donde cada usuario virtual espera a que finalice su petición anterior para realizar la siguiente; si el servidor se ralentiza, la tasa de llegada de peticiones disminuye artificialmente, ocultando cuellos de botella reales de acumulación de colas.

### • ¿Qué diferencia existe entre un check fallido y un threshold incumplido?
Un **check** es una aserción booleana de nivel funcional aplicada a una petición o respuesta específica (por ejemplo, validar si el código HTTP es 200). Su fallo no detiene la ejecución de k6 ni invalida el estado global de la prueba de carga.
Un **threshold** (umbral) es una métrica de nivel de servicio global que evalúa estadísticas completas (como el percentil 95 de duración de hilos, o el porcentaje de errores totales). Si se incumple un threshold, k6 marca todo el test como fallido en su estado de retorno de salida (exit code diferente de cero), lo cual detiene integraciones automáticas (pipelines de CI/CD).

### • ¿Qué significa que dropped_iterations aumente aunque el error HTTP sea bajo?
Significa que la carga programada (la tasa de llegada deseada) superó la capacidad de k6 de generar peticiones debido a que el pool de hilos cliente (`preAllocatedVUs` hasta `maxVUs`) se saturó esperando las respuestas lentas del servidor. Cuando las VUs disponibles están ocupadas esperando respuestas, k6 no puede iniciar nuevas iteraciones a tiempo, por lo que las "descarta" (`dropped_iterations`). Esto indica que el sistema bajo prueba está ralentizado y bloquea los recursos cliente, aunque responda con códigos exitosos (200 OK) al terminar.

### • ¿Qué escenario conviene repetir primero después de una optimización y por qué?
Conviene repetir el escenario que representa el principal limitador o cuello de botella del sistema, que en este experimento es el de **pico de reportes** (`pico_reportes`). Al ser un escenario de modelo abierto con peticiones bloqueantes lentas (simulando 120ms de sleep), es el que tiene mayor propensión a agotar los recursos de Tomcat y el que genera mayor impacto en el rendimiento global. Probar este escenario de manera aislada permite comprobar inmediatamente si la latencia del percentil 95 disminuyó y si se evita la saturación de memoria o hilos del servidor tras implementar la optimización.

---

## 10. Reto Aplicado: Análisis Comparativo V1 vs V2

### Configuración del Reto
Se creó el script [escenarios-avanzados-v2.js](file:///c:/Users/51913/examen-parcial1-const-sw2-clone4/performance/sesion29/escenarios-avanzados-v2.js) en el cual se duplicó la tasa de llegada de solicitudes del endpoint de reporte:
- Tasa inicial: 4 RPS (antes 2 RPS)
- Tasa pico: 40 RPS (antes 20 RPS)
- Hilos cliente (`preAllocatedVUs` / `maxVUs`): 20 / 100 (antes 10 / 50)
El resto de escenarios (consultas y registros) y los umbrales se mantuvieron idénticos. Ambas versiones fueron ejecutadas dos veces para asegurar la consistencia y representatividad de las métricas.

### Resultados Comparativos (Valores Representativos / Medianas)

| Métrica / Escenario | Versión 1 (Pico 20 RPS) | Versión 2 (Pico 40 RPS) | Impacto / Diferencia |
| :--- | :--- | :--- | :--- |
| **Peticiones Reporte Completadas** | 614 | 1230 | +100.3% |
| **Total Iteraciones (Test)** | 2327 | 2953 | +26.9% |
| **p95 Latencia Reporte** | 124.65 ms | 124.27 ms | -0.3% (Estable) |
| **p99 Latencia Reporte** | 144.93 ms | 149.30 ms | +3.0% |
| **Tasa de Errores HTTP** | 0.00% | 0.00% | Sin cambios |
| **Dropped Iterations** | 0 | 0 | Sin cambios |
| **Consumo CPU Neto** | 6.54 s | 5.16 s | -21.1% (Efecto JIT Compiler) |
| **Memoria RAM Máxima** | 135.73 MB | 136.21 MB | +0.4% (+0.48 MB) |

### Análisis de Resultados e Interpretación
1. **Comportamiento de Latencia y Estabilidad:** A pesar de haber duplicado el pico de carga en reportes de 20 RPS a 40 RPS, el percentil 95 de latencia se mantuvo prácticamente idéntico (alrededor de 124 ms). Esto indica que el backend no ha alcanzado su límite de capacidad en absoluto, y el tiempo de respuesta está dominado casi en su totalidad por el retardo artificial de 120 ms.
2. **Dropped Iterations e Hilos:** No se registraron `dropped_iterations` en ninguna versión. La cantidad de VUs preasignados y máximos (20/100 para V2) fue adecuada para absorber el tráfico generado sin saturación del generador de carga k6.
3. **Consumo de CPU y Efecto JIT Warm-up:** Se observó que el consumo neto de CPU disminuyó de 6.54s a 5.16s en V2. Esto es una consecuencia directa del proceso de "warm-up" de la Java Virtual Machine (JVM). Al ejecutarse V2 inmediatamente después de V1 sobre el mismo proceso PID, el compilador JIT (Just-In-Time) ya había optimizado las rutas calientes del código (la manipulación de colecciones concurrentes y el mapeo JSON), resultando en una ejecución mucho más eficiente con menor tiempo de procesador en la segunda fase.
4. **Crecimiento de Memoria:** La memoria RAM máxima utilizada aumentó muy ligeramente (+0.48 MB) debido a que la colección `CopyOnWriteArrayList` en memoria almacenó más registros de productos generados por el escenario de registros que estuvo activo durante más tiempo acumulativo en la JVM.

### Propuesta de Única Mejora y Métrica Esperada
- **Mejora:** Implementar un **Caché en Memoria con expiración temporal corta (ej. TTL de 2 segundos)** para el endpoint `/carga/productos/reporte` utilizando la anotación `@Cacheable` de Spring Boot.
- **Justificación:** Dado que el reporte solo devuelve el tamaño de la lista de productos (un cálculo muy ligero que cambia a una frecuencia baja de 5 registros/s), no es necesario recalcular el reporte en cada una de las 40 peticiones por segundo concurrentes. Al cachear el resultado por 2 segundos, el 98% de las solicitudes se resolverán inmediatamente desde la memoria sin ejecutar el método bloqueante simulado con retardo de 120 ms.
- **Métrica esperada que debería cambiar:** La latencia de reportes **p(95) y p(99) debería caer de ~124 ms a < 5 ms** (casi instantáneo), y el rendimiento/capacidad de reportes concurrentes podría escalar a miles de RPS sin consumir hilos adicionales de Tomcat.
