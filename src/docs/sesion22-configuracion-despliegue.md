# VALIDACIÓN DE CONFIGURACIÓN Y DESPLIEGUE 

*La FIIS-UNAS cuenta con una API Spring Boot que debe pasar de un entorno de desarrollo a un entorno de despliegue. Antes de liberar la version, el equipo debe comprobar que la configuracion activa sea correcta, que el servicio responda, que el contenedor se ejecute y que exista evidencia tecnica del despliegue.*

#### Creamos configuracion de perfiles por entorno (dev,test,prod)

>application.properties:
![alt text](image.png)

>application-dev.properties:
![alt text](image-1.png)

>application-test.properties:
![alt text](image-2.png)

>applicaton-prod.properties:
![alt text](image-3.png)

#### Implementamos servicos de validación de despliegue
![alt text](image-4.png)

#### Implementamos Controller REST de validación
![alt text](image-5.png)

#### Ejecutamos y validamos por los perfiles creados

>Perfil Dev:
![alt text](image-6.png)

>Perfil Test:
![alt text](image-7.png)

>Perfil Prod:
![alt text](image-8.png)

#### Creamos prueba de integración y verificamos
![alt text](image-9.png)
![alt text](image-10.png)


# Creación del Docker:

#### Empaquetamos
![alt text](image-11.png)

#### Creamos .dockerignore
![alt text](image-12.png)

#### Creamos Dockerfile
![alt text](image-13.png)

#### Construimos y ejecutamos el contenedor Docker
![alt text](image-14.png)
![alt text](image-15.png)

![alt text](image-16.png)


#### Despliegue con Docker Compose
docker-compose.yml:
![alt text](image-17.png)

Ejecutamos y verificamos:
![alt text](image-18.png)
![alt text](image-19.png)