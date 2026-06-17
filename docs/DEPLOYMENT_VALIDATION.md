## Validación de Configuración y Despliegue

# Objetivo

Validar que la API desarrollada en Spring Boot funcione correctamente en diferentes entornos de ejecución, usando perfiles de configuración, endpoints de validación, pruebas automatizadas y despliegue mediante Docker

## Evidencia de requisitos previos
Verificación de Java, Maven, Docker y Git

Comandos utilizados:

java -version
.\mvnw.cmd -version
docker --version
git --version

![alt text](image.png)

## Evidencia de configuración base
Archivo application.properties

![alt text](image-1.png)

Explicación:
En este archivo se configuraron propiedades generales de la aplicación, como el puerto, la versión, el entorno activo y el mensaje base. También se usaron variables externas para permitir que la configuración cambie según el entorno.

## Evidencia de perfiles de entorno
Archivos application-dev.properties, application-test.properties y application-prod.properties

![alt text](image-2.png)
## 
![alt text](image-3.png)
## 
![alt text](image-4.png)

Explicación:
Se crearon archivos de configuración para los perfiles dev, test y prod. Cada perfil tiene un mensaje y entorno diferente, lo cual permite validar que la aplicación cargue la configuración correcta según el ambiente donde se ejecute.

## Evidencia del servicio de validación
Clase DeploymentValidationService.java

![alt text](image-5.png)

Explicación:
La clase DeploymentValidationService se encarga de leer las propiedades configuradas en los archivos del proyecto. A través de este servicio se obtiene el entorno activo, la versión de la aplicación y el mensaje correspondiente.

## Evidencia del controlador REST
Clase DeploymentValidationController.java

![alt text](image-6.png)

Explicación:
En el controlador se implementaron los endpoints /deploy/config, /deploy/health y /deploy/checklist. Estos endpoints permiten validar la configuración activa, el estado de la API y una lista básica de verificación del despliegue.

## Evidencia de ejecución local con perfil dev
Ejecución de la aplicación con perfil dev

Comando utilizado:

.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"

![alt text](image-7.png)

![alt text](image-8.png)



## Evidencia de endpoint /deploy/config en perfil dev
Validación de configuración en entorno de desarrollo

Comando utilizado:

curl.exe http://localhost:8080/deploy/config

![alt text](image-9.png)

Explicación:
El endpoint /deploy/config respondió mostrando el entorno DEV, la versión de la aplicación y el mensaje de desarrollo. Esto confirma que el perfil dev fue cargado correctamente.

## Evidencia de endpoint /deploy/health
Captura 8: Validación del estado de la API

Comando utilizado:

curl http://localhost:8080/deploy/health

![alt text](image-10.png)



## Evidencia de endpoint /deploy/checklist
 Validación de checklist de despliegue

Comando utilizado:

curl http://localhost:8080/deploy/checklist

![alt text](image-11.png)

Explicación:
El endpoint /deploy/checklist devuelve una lista de validaciones técnicas relacionadas con el despliegue.

## Evidencia de prueba de integración
Clase DeploymentValidationControllerTest.java

![alt text](image-12.png)

Explicación:
Se creó una prueba de integración usando MockMvc. Esto  valida que los endpoints de despliegue respondan correctamente y que la configuración del entorno de prueba contenga el valor esperado.

## Evidencia de ejecución de pruebas
 Resultado de mvnw test

Comando utilizado:

.\mvnw test

![alt text](image-13.png)

![alt text](image-24.png)

Explicación:
Las pruebas se ejecutaron correctamente y finalizaron con BUILD SUCCESS. 

## Evidencia de empaquetado de la aplicación
Captura 12: Generación del archivo .jar

Comando utilizado:

.\mvnw clean package -DskipTests

![alt text](image-14.png)

Luego:

ls target

![alt text](image-15.png)

Explicación:
Se empaquetó la aplicación en un archivo .jar dentro de la carpeta target. Este archivo es necesario para construir la imagen Docker y ejecutar la API dentro de un contenedor.

## Evidencia del archivo .dockerignore
Archivo .dockerignore

![alt text](image-16.png)

Explicación:
El archivo .dockerignore permite excluir carpetas y archivos innecesarios durante la construcción de la imagen Docker. 

## Evidencia del Dockerfile
Archivo Dockerfile

![alt text](image-17.png)

Explicación:
El Dockerfile define cómo se construye la imagen de la aplicación. Usa Java 17, copia el archivo .jar, configura el perfil de producción y expone el puerto 8080 para ejecutar la API.

## Evidencia de construcción de imagen Docker
Construcción de la imagen Docker

Comando utilizado:

docker build -t demoapi-sesion22 .

![alt text](image-18.png)

Explicación:
Se construyó la imagen Docker llamada demoapi-sesion22. Esta imagen contiene la aplicación Spring Boot empaquetada y lista para ejecutarse en un contenedor.

## Evidencia de ejecución del contenedor Docker
Contenedor ejecutándose

Comando utilizado:

docker run --name demoapi-sesion22 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod demoapi-sesion22

![alt text](image-19.png)

En otra terminal:

docker ps

![alt text](image-20.png)

Explicación:
En esta evidencia se muestra el contenedor activo ejecutando la API. El comando docker ps permite confirmar que el contenedor está levantado y que el puerto 8080 está expuesto correctamente.

## Evidencia de endpoint /deploy/config y /deploy/health en perfil prod con Docker
Validación de configuración en Docker

Comando utilizado:

curl http://localhost:8080/deploy/config ,  curl http://localhost:8080/deploy/health

![alt text](image-21.png)

Explicación:
El endpoint  respondió a ambos  con el entorno PROD

## Evidencia de Docker Compose
Archivo docker-compose.yml

![alt text](image-22.png)

Explicación:
El archivo docker-compose.yml permite levantar la aplicación usando Docker Compose. En este archivo se define el servicio, el nombre del contenedor, el puerto y las variables de entorno necesarias para ejecutar la API en producción.

Ejecución con Docker Compose

Comando utilizado:

docker compose up --build

![alt text](image-23.png)

Explicación:
Se ejecutó la aplicación usando Docker Compose. 

## Evidencia del ejercicio aplicado /deploy/version
 Endpoint /deploy/version

Comando utilizado:

curl.exe http://localhost:8080/deploy/version

![alt text](image-25.png)

Explicación:
Se agregó el endpoint /deploy/version, el cual devuelve la versión de la aplicación configurada en app.version.