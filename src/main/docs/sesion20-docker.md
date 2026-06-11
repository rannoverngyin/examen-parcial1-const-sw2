# CONTENEDORES Y DESPLIEGUE

#### Primero comprobamos que no haya error en la ejecución de pruebas

![alt text](image-16.png)

>Preparamos la aplicación para el empaquetado:

![alt text](image-1.png)

#### Creamos el archivo .dockerignore

![alt text](image-7.png)
>*Evita que se copie archivos innecesarios*

#### Creamos el archivo Dockfile

![alt text](image-3.png)

#### Contruimos la imagen Docker

![alt text](image-4.png)

![alt text](image-5.png)

![alt text](image-6.png)

#### Ejecutamos en contenedor

![alt text](image-8.png)

![alt text](image-9.png)

>*Ahora validamos:*

![alt text](image-10.png)

#### Ejecutamos en segundo plano

Primero eliminamos el contenedor:
![alt text](image-11.png)

Luego lo corremos en modo detached:
![alt text](image-12.png)

Verficamos:
![alt text](image-13.png)

Logs:
![alt text](image-14.png)

Ahora detemos y eliminamos:
![alt text](image-15.png)


#### Creamos Docker Compose
>Docker Compose sirve para definir y ejecutar varios contenedores con un solo archivo de configuración

Creamos archivo .yml:
![alt text](image-17.png)

Ejecutamos:
![alt text](image-18.png)

Verificamos:
![alt text](image-19.png)

Detemos todo:
![alt text](image-20.png)

#### Despliegue con PostgreSQL y volumen persistente

Reajustamos el archivo .yml:
![alt text](image-21.png)

Y lo ejecutamos:
![alt text](image-22.png)

Comprobamos:
![alt text](image-24.png)

Verificar persistencia de base de datos creando una tabla de prueba dentro del contenedor:
![alt text](image-26.png)
![alt text](image-27.png)
![alt text](image-28.png)