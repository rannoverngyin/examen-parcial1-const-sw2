## Objetivo
Implementar internacionalización en una API REST desarrollada con Spring Boot, permitiendo que los mensajes del sistema se muestren en español e inglés mediante archivos externos de configuración.

## videncia de archivos de mensajes
-- Archivo messages_es.properties

![alt text](image.png)

Explicación:
En este archivo se definieron los mensajes en español. Las claves como saludo, curso, idioma y evaluacion permiten que el sistema obtenga el texto correspondiente sin escribirlo directamente en el controlador.

-- Archivo messages_en.properties

![alt text](image-1.png)

Explicación:
En este archivo se definieron los mensajes en inglés

## Evidencia de configuración de internacionalización
Archivo application.properties

![alt text](image-2.png)

Explicación:
En este archivo se configuró la propiedad spring.messages.basename=messages, lo que permite que Spring Boot busque los archivos de mensajes externos

## Evidencia del servicio de mensajes
-- Clase MensajeService.java

![alt text](image-3.png)

Explicación:
Esta clase recibe la clave del mensaje y el idioma solicitado, luego devuelve el texto correspondiente según el archivo de idioma configurado.

## Evidencia del controlador REST
Clase InternacionalizacionController.java

![alt text](image-4.png)

Explicación:
En el controlador se implementaron los endpoints REST para consultar los mensajes internacionalizados. Los endpoints reciben el parámetro lang, el cual permite seleccionar si la respuesta será en español o inglés.

## Evidencia de endpoint en español
Prueba de /i18n/saludo?lang=es

Comando utilizado:

curl "http://localhost:8080/i18n/saludo?lang=es"


![alt text](image-5.png)

-- Prueba de /i18n/curso?lang=es

Comando utilizado:

curl "http://localhost:8080/i18n/curso?lang=es"

![alt text](image-7.png)


## Evidencia de endpoint en inglés
Captura 8: Prueba de /i18n/saludo?lang=en

Comando utilizado:

curl "http://localhost:8080/i18n/saludo?lang=en"

![alt text](image-6.png)

-- Prueba de /i18n/curso?lang=en

Comando utilizado:

curl "http://localhost:8080/i18n/curso?lang=en"

![alt text](image-8.png)

-- Prueba de /i18n/idioma?lang=en

Comando utilizado:

curl "http://localhost:8080/i18n/curso?lang=en"

![alt text](image-9.png)

## Evidencia del endpoint aplicado evaluacion
-- Prueba de /i18n/evaluacion?lang=es

Comando utilizado:

curl "http://localhost:8080/i18n/evaluacion?lang=es"

![alt text](image-10.png)

-- Prueba de /i18n/evaluacion?lang=en

Comando utilizado:

curl.exe "http://localhost:8080/i18n/evaluacion?lang=en"

![alt text](image-11.png)

## Evidencia del uso de cabecera Accept-Language
Prueba de /i18n/saludo-header

Comando utilizado:

curl.exe -H "Accept-Language: es" "http://localhost:8080/i18n/saludo-header"

![alt text](image-13.png)

curl.exe -H "Accept-Language: en" "http://localhost:8080/i18n/saludo-header"

![alt text](image-12.png)

## Evidencia de prueba de integración
Clase InternacionalizacionControllerTest.java

![alt text](image-14.png)

Explicación:
Se creó una prueba de integración usando MockMvc. Esta prueba valida que los endpoints respondan correctamente en español e inglés, verificando el funcionamiento del controlador

## Evidencia de ejecución de pruebas
Resultado de mvnw.cmd test

Comando utilizado:

./mvnw test

![alt text](image-15.png)

![alt text](image-16.png)

La ejecución de las pruebas finalizó correctamente con BUILD SUCCESS