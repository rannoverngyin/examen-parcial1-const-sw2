# Sesión 20 - Contenedores y Despliegue
## Empaquetado con Maven

Se ejecutaron las pruebas del proyecto con `./mvnw test` obteniendo BUILD SUCCESS con 39 tests, 0 fallos.

![Test Inicial](<img/sesion20/Test INICIAL.png>)

Se generó el artefacto JAR con `./mvnw clean package -DskipTests` obteniendo BUILD SUCCESS.

![Clean Package](<img/sesion20/Clean Package.png>)

Se verificó la existencia del JAR generado en la carpeta `target/`.

![Foto del Target](<img/sesion20/Foto del target.png>)

## Archivos de configuración Docker

Se creó el archivo `.dockerignore` para excluir archivos innecesarios de la imagen.

![Dockerignore](<img/sesion20/Dockerignore.png>)

Se creó el `Dockerfile` usando imagen base `eclipse-temurin:17-jre`, exponiendo el puerto 8080.

![Docker Imagenes](<img/sesion20/Docker imagenes.png>)

## Construcción de la imagen Docker

Se construyó la imagen con `docker build -t examen-parcial1-api:1.0 .` y se verificó con `docker images`.

![Docker Build](<img/sesion20/Docker Build.png>)

## Ejecución en contenedor

Se ejecutó el contenedor en primer plano con `docker run --name examen-api -p 8080:8080 examen-parcial1-api:1.0`.

![Docker Run](<img/sesion20/Docker Run.png>)

Se validó el endpoint `/productos` desde otra terminal con `curl http://localhost:8080/productos`.

![Prueba en otra terminal](<img/sesion20/Prueba en otra terminal.png>)

Se verificó también desde el navegador en `localhost:8080/productos`.

![Prueba del Endpoint](<img/sesion20/Prueba del Endpoint.png>)

## Ejecución en segundo plano

Se detuvo y eliminó el contenedor anterior, luego se ejecutó en modo detached con `docker run -d`.

![Docker RM](<img/sesion20/Docker RM.png>)

Se verificaron los contenedores activos con `docker ps`.

![Docker PS](<img/sesion20/Docker ps.png>)

Se revisaron los logs del contenedor con `docker logs examen-api` y se detuvo con `docker stop` y `docker rm`.

![Docker Logs](<img/sesion20/Docker Logs.png>)

## Docker Compose básico

Se creó el archivo `docker-compose.yml` para despliegue reproducible con un solo servicio API.

![Docker Compose](<img/sesion20/Docker compose.png>)

Se ejecutó `docker compose up --build` y la API inició correctamente en el puerto 8080.

![Docker Compose Up](<img/sesion20/Docker compose up.png>)

Se detuvo el entorno con `docker compose down`.

![Docker Down](<img/sesion20/Docker Down.png>)

## Despliegue con PostgreSQL y volumen persistente

Se actualizó el `docker-compose.yml` agregando el servicio `db` con PostgreSQL 16-alpine y volumen persistente.

![Docker Compose Nuevo](<img/sesion20/Docker compose nuevo.png>)

Se levantaron ambos servicios con `docker compose up --build` verificando que API y base de datos iniciaron correctamente.

![Container](<img/sesion20/Container.png>)

## Verificación de persistencia

Se ingresó al contenedor de base de datos con `docker exec -it examen-db psql` y se creó una tabla de prueba con un registro.

![Postgres](<img/sesion20/Postgres.png>)

Se bajó el entorno con `docker compose down` y se volvió a levantar con `docker compose up -d`. El dato persistió correctamente tras el reinicio de los contenedores.

![Docker Run Comprobacion](<img/sesion20/Docker run comprobacion.png>)