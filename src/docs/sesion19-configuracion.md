# Configuración y Parametrización

#### Configurar páramentros externos

![alt text](image.png)

#### Crear el Service y los servicios

![alt text](image-1.png)

#### Crear el Controller y los controladores de los parámetros

![alt text](image-2.png)

### Prueba de configuración con envio de mensaje

#### Creamos la interfaz de notificador

![alt text](image-3.png)

#### Crear el Service para enviar y notificar por Email a los proveedores

![alt text](image-4.png)

#### Crear un Mock para prueba y simulación del notificador por Email

![alt text](image-5.png)


#### Crear Controller del notificador

![alt text](image-6.png)


#### Ejecutar y validar

![alt text](image-7.png)

![alt text](image-8.png)


#### Cambiamos el parametro de Email a Mock

![alt text](image-9.png)

 Y comprobamos que se cambio correctamente
 ![alt text](image-10.png)


 #### Crear Test unitarios de proveedor email y de los parámetros

Notificador:
![alt text](image-11.png)

Parametro institucion:
![alt text](image-12.png)

comprobamos:

![alt text](image-14.png)


#### EXTRA - Ejecicio aplicado agregar un nuevo parámetro

>Agregamos en application.properties:
![alt text](image-15.png)

>Modificamos Serive:
![alt text](image-16.png)

>Modificamos Controller:
![alt text](image-17.png)

Y comprobamos:
![alt text](image-18.png)

Ahora creamos su prueba de integración:

>Modificamos ParametroControllerTest.java:
![alt text](image-19.png)

Y ejecutamos:
![alt text](image-20.png)