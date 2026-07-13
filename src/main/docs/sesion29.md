# DISEÑO DE ESCENARIOS AVANZADOS DE CARGA

#### Creamos el endpoint requeridos

>Service
![alt text](image.png)

>Controller
![alt text](image-1.png)

#### Validamos
![alt text](image-2.png)

#### Creamos carpeta y scripts a utilizar
![alt text](image-3.png)


#### Extraemos datos

PS E:\CURSOS\CONSTRUCCION DE SOFTWARE II\avanzado\performance\sesion29> $data = Get-Content evidencia\resumen-sesion29.json | ConvertFrom-Json
>>    $data.metrics.'http_req_duration{endpoint:listado}'.values
>>    $data.metrics.'http_req_duration{endpoint:total}'.values
>>    $data.metrics.'http_req_duration{endpoint:registro}'.values
>>    $data.metrics.'http_req_duration{endpoint:reporte}'.values

p(90) : 3.6071
p(95) : 4.1287
p(99) : 5.855
avg   : 2.48782897366031
min   : 0.5029
med   : 2.3186
max   : 8.1358

p(99) : 5.0966
avg   : 1.6311514986376
min   : 0
med   : 1.5256
max   : 6.9274
p(90) : 2.6122
p(95) : 3.1866

p(99) : 6.0233
avg   : 2.42742778702163
min   : 0.5076
med   : 2.1846
max   : 12.0249
p(90) : 3.5244
p(95) : 4.1727

max   : 142.3587
p(90) : 136.2641
p(95) : 137.21993
p(99) : 139.531637
avg   : 130.173732899023
min   : 121.3805
med   : 129.9947

PS E:\CURSOS\CONSTRUCCION DE SOFTWARE II\avanzado\performance\sesion29> $data.metrics.'http_req_duration{endpoint:registro}'.values

p(99) : 6.0233
avg   : 2.42742778702163
min   : 0.5076
med   : 2.1846
max   : 12.0249
p(90) : 3.5244
p(95) : 4.1727

PS E:\CURSOS\CONSTRUCCION DE SOFTWARE II\avanzado\performance\sesion29> 

>Tabla con resultados
![alt text](image-5.png)

Conclusión: Bajo el modelo de carga mixta con tres escenarios concurrentes (consultas con ramping-vus, registros con constant-arrival-rate y pico de reportes con ramping-arrival-rate), el sistema alcanzó 21.49 solicitudes/s. El escenario más afectado fue pico de reportes con p95 de 137.22 ms y error de 0 %. La evidencia de la latencia sostenidamente alta en ese endpoint frente a los demás (más de 30 veces superior a consultas y registros) y coincidente con el Thread.sleep(120) deliberado en el método reporte() del servicio sugiere como causa probable un cuello de botella intencional a nivel de código (latencia fija simulada), no una limitación de infraestructura, concurrencia o recursos del servidor. Se recomienda duplicar la intensidad del pico de reportes para verificar si el sistema sigue absorbiendo la carga sin degradación adicional, sin dropped_ite

# RETO APLICADO
![alt text](image-6.png)

>DATOS
$original2 = Get-Content evidencia\resumen-original-corrida2.json | ConvertFrom-Json
>> $doble1 = Get-Content evidencia\resumen-doble-corrida1.json | ConvertFrom-Json
>> $doble2 = Get-Content evidencia\resumen-doble-corrida2.json | ConvertFrom-Json
>> 
>> Write-Host "=== ORIGINAL - Corrida 2 ==="
>> $original2.metrics.'http_req_duration{endpoint:reporte}'.values
>> $original2.metrics.http_req_failed.values
>> $original2.metrics.dropped_iterations.values
>> 
>> Write-Host "=== PICO DOBLE - Corrida 1 ==="
>> $doble1.metrics.'http_req_duration{endpoint:reporte}'.values
>> $doble1.metrics.http_req_failed.values
>> $doble1.metrics.dropped_iterations.values
>> 
>> Write-Host "=== PICO DOBLE - Corrida 2 ==="
>> $doble2.metrics.'http_req_duration{endpoint:reporte}'.values
>> $doble2.metrics.http_req_failed.values
>> $doble2.metrics.dropped_iterations.values
=== ORIGINAL - Corrida 2 ===

min   : 121.6226
med   : 129.809
max   : 210.9069
p(90) : 136.28506
p(95) : 137.22635
p(99) : 137.939092
avg   : 130.09728894309

rate   : 0
passes : 0
fails  : 3454

=== PICO DOBLE - Corrida 1 ===
p(95) : 136.771995
p(99) : 137.886379
avg   : 129.410128336079
min   : 120.614
med   : 129.3977
max   : 140.2865
p(90) : 135.98627

rate   : 0
passes : 0
fails  : 4075

=== PICO DOBLE - Corrida 2 ===
p(90) : 136.53859
p(95) : 137.354285
p(99) : 138.621611
avg   : 130.206268945634
min   : 121.3949
med   : 130.34835
max   : 140.0034

passes : 0
fails  : 4044
rate   : 0

>Tabla 
![alt text](image-7.png)

Conclusión: Bajo el modelo de carga con el pico de reportes duplicado (de 20 a 40 peticiones/s en rampa), el sistema alcanzó una mediana de p95 de 137.06 ms, prácticamente igual al 137.22 ms de la versión original, con 0% de errores en ambos casos y sin dropped_iterations. El escenario de pico de reportes no mostró degradación pese a duplicar la intensidad de la carga.