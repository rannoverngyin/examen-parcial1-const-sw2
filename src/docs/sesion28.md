# PRUEBAS DE CARGA Y MÉTRICAS

#### Instalamos k6
![alt text](image-8.png)

#### Creamos un servicio con retraso configurable
![alt text](image-2.png)

#### Creamos el controlador REST
![alt text](image-3.png)

>Cambio en application.properties
![alt text](image-4.png)

#### Ejecutamos y validamos
![alt text](image-5.png)

![alt text](image-6.png)

#### Creamos el smoke test
![alt text](image-7.png)
![alt text](image-9.png)

#### Creamos y ejecutar la prueba de carga
![alt text](image-10.png)
![alt text](image-11.png)

#### Observamos CPU y memoria durante la carga
>Versión con 120 de carga
![alt text](image-12.png)

>Versión con 20 de carga
![alt text](image-14.png)

#### Comparación
![alt text](image-15.png)

# EXTRA - Ejercicio aplicado

![alt text](image-16.png)

![alt text](image-17.png)

````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
Se agregó el threshold p(95)<700ms manteniendo el mismo endpoint (/carga/productos) y la misma pausa de usuario (sleep(1)).
Con una rampa progresiva de 1 a 30 VUs durante 70 segundos, el sistema cumplió el threshold ampliamente (p95 real = 38.27ms, muy por debajo de los 700ms exigidos), sin errores (0.00%) y con 100% de checks exitosos sobre 1180 iteraciones.
El RPS se mantuvo estable en 16.79/s, valor casi idéntico al obtenido en la corrida optimizada anterior (16.63/s) con solo 10 VUs constantes. 
Esto demuestra que una mayor cantidad de VUs no produce mayor throughput una vez que el sistema alcanza su capacidad de procesamiento sostenible: los usuarios virtuales adicionales generan más peticiones concurrentes, pero el servidor las procesa al mismo ritmo máximo, por lo que el exceso de carga se traduce en peticiones en espera (reflejado en la latencia máxima aislada de 141.93ms) en lugar de en más peticiones completadas por segundo. 
El cuello de botella no está en CPU (que se mantuvo bajo según VisualVM), sino en la capacidad de concurrencia del pool de threads/conexiones del servidor embebido.
````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````