# PRUEBAS DE CARGA Y MÉTRICAS


#### 1. Levantar la API de Spring Boot:
./mvnw spring-boot:run

#### Terminal 2: Pruebas y Monitoreo
mkdir -p evidencia/sesion28

#### Terminal 2: Pruebas y Monitoreo
curl.exe -i http://localhost:8080/carga/health
curl.exe -i http://localhost:8080/carga/productos
curl.exe http://localhost:8080/carga/metricas

#### Ejecutar el Smoke Test (1 usuario virtual por 10 segundos):
docker run --rm --network host -v "$PWD:/app" -w /app grafana/k6 run scripts/s28-smoke.js


#### Crear la carpeta para guardar los resultados

mkdir -p evidencia/sesion28

#### Ejecutar la Prueba de Carga Base (Progresiva de 10 a 30 usuarios):
docker run --rm --network host -v "$PWD:/app" -w /app grafana/k6 run --summary-mode=full scripts/s28-load.js

#### Respaldar el archivo JSON generado de la carga base
cp evidencia/sesion28/resumen.json evidencia/sesion28/resumen-base.json

>Cambio en application.properties
app.carga.delay-ms=20

####  Reiniciar la API (Terminal 1):
./mvnw spring-boot:run

#### Ejecutar la Segunda Prueba de Carga (Terminal 2):
docker run --rm --network host -v "$PWD:/app" -w /app grafana/k6 run --summary-mode=full scripts/s28-load.js

#### Renombrar el JSON optimizado:
mv evidencia/sesion28/resumen.json evidencia/sesion28/resumen-optimizado.json

#### Ver el contador final de peticiones registradas por Spring Boot:
curl http://localhost:8080/carga/metricas




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

````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````