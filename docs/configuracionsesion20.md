## Contenedores y despliegue de una API Spring Boot con Docker

## . Objetivo de la práctica

El objetivo de la práctica fue contenerizar una aplicación Spring Boot, construir una imagen Docker, ejecutar la API dentro de un contenedor, validar su funcionamiento mediante endpoints REST y preparar un despliegue reproducible utilizando Docker Compose. Además, se configuró PostgreSQL como servicio adicional con volumen persistente.

##  . Verificación del entorno de trabajo
-- java -version 
-- git --version 
-- ./mvnw -version 
-- docker --version 
-- docker compose version

Evidencia: ![alt text](image.png)

## . Ejecución de pruebas del proyecto

se ejecutaron las pruebas del proyecto con Maven:
-- ./mvnw test

Resultado: ![alt text](image-1.png) ,  ![alt text](image-2.png)

## . Generación del archivo JAR

se generó el archivo ejecutable:

-- ./mvnw clean package -DskipTests

Resultado: ![alt text](image-3.png)

Después se verificó el archivo generado:

-- ls target/*.jar

evidencia:  
![alt text](image-4.png) 

## . Creación del archivo .dockerignore

Se creó el archivo .dockerignore para evitar copiar archivos innecesarios durante la construcción de la imagen Docker:

-- nano dockerignore

Captura:

![alt text](image-5.png) 

Este archivo ayuda a reducir el contexto de construcción de Docker y evita incluir carpetas temporales o archivos no necesarios.

## . Creación del Dockerfile

Se creó el archivo Dockerfile en la raíz del proyecto.

-- nano dockerfile

Captura:

![alt text](image-6.png)

## . Creación del endpoint

Para validar que la API respondiera correctamente, se creó el archivo:

--  ConfigController.java : ![alt text](image-7.png)

También se creó el endpoint /productos,  para validar tambien  el funcionamiento de la API.

-- ProductoController.java :

![alt text](image-8.png)

evidencia de la ejecucion con el endpoint /productos:

![alt text](image-11.png)

## . Construcción de la imagen Docker

Después de generar el JAR y crear el Dockerfile, se construyó la imagen Docker con el siguiente comando:

-- sudo docker run --name examen-api -p 8080:8080 examen-parcial1-api:1.0

Luego se verificó la existencia de la imagen:

-- sudo docker ps

![alt text](image-9.png)

Ver los logs de la imagen: 

![alt text](image-10.png)

## . Configuración de Docker Compose
Docker Compose permite definir el servicio de la API y ejecutarlo con un solo comando.

![alt text](image-12.png)

## . Levantamiento de API y PostgreSQL con Docker Compose

Se ejecutó:

-- sudo docker compose up -d --build

Luego se verificaron los contenedores activos:

-- sudo docker ps

evidencias: ![alt text](image-13.png)

Se observaron dos contenedores activos:

examen-api
examen-db

El contenedor examen-api quedó publicado en el puerto 8080 y el contenedor examen-db quedó publicado en el puerto 5432

## . Prueba de persistencia en PostgreSQL

Para comprobar la persistencia de datos, se creó una tabla dentro de PostgreSQL:

-- sudo docker exec -it examen-db psql -U appuser -d appdb -c "CREATE TABLE prueba(id SERIAL PRIMARY KEY, nombre TEXT);"

-- sudo docker exec -it examen-db psql -U appuser -d appdb -c "INSERT INTO prueba(nombre) VALUES ('dato persistente');"

--  sudo docker exec -it examen-db psql -U appuser -d appdb -c "SELECT * FROM prueba;"

evidencias: 

![alt text](image-14.png)

## . Bajar los contenedores sin borrar volumen
Para comprobar que los datos no se perdían al reiniciar los contenedores, se bajaron los servicios:
-- sudo docker compose down

![alt text](image-15.png)

## . Levantar otra vez:

-- sudo docker compose up -d

![alt text](image-16.png)

## . Consultar otra vez el dato:

-- sudo docker exec -it examen-db psql -U appuser -d appdb -c "SELECT * FROM prueba;"

![alt text](image-17.png)

Si el dato sigue apareciendo despues de reiniciar los contenedores, el volumen postgres_data esta funcionando.