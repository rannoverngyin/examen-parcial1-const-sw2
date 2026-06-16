# Sesión 21 - Internacionalización en Spring Boot

## Paso 1 - Aplicación corriendo
Comando ejecutado:
./mvnw spring-boot:run

Resultado: Tomcat started on port 8080
![App corriendo](img/sesion21/app_corriendo.png)

## Paso 2 - Endpoints en español e inglés

### /i18n/saludo?lang=es
![Saludo español](img/sesion21/saludo_es.png)

### /i18n/saludo?lang=en
![Saludo inglés](img/sesion21/saludo_en.png)

### /i18n/curso?lang=es
![Curso español](img/sesion21/curso_es.png)

### /i18n/curso?lang=en
![Curso inglés](img/sesion21/curso_en.png)

### /i18n/idioma?lang=en
![Idioma inglés](img/sesion21/idioma_en.png)

## Paso 3 - Pruebas de integración con MockMvc
Comando ejecutado:
./mvnw test

Resultado: BUILD SUCCESS — Tests run: 38, Failures: 0, Errors: 0
![Build Success](img/sesion21/build_success.png)

## Paso 4 - Controlador con Accept-Language (reto opcional)
Endpoint: GET /i18n/saludo-header
Implementado en InternacionalizacionController.java usando @RequestHeader
![Controlador](img/sesion21/controller_header.png)

## Paso 5 - Prueba con cabecera Accept-Language
Comando ejecutado:
Invoke-WebRequest -Uri "http://localhost:8080/i18n/saludo-header" -Headers @{"Accept-Language"="en"} | Select-Object -ExpandProperty Content
Invoke-WebRequest -Uri "http://localhost:8080/i18n/saludo-header" -Headers @{"Accept-Language"="es"} | Select-Object -ExpandProperty Content

Resultado en inglés: Welcome to the FIIS-UNAS system
Resultado en español: Bienvenido al sistema FIIS-UNAS
![Prueba header](img/sesion21/header_test.png)

## Conclusión
Los mensajes del sistema se externalizan en archivos .properties y se
seleccionan dinámicamente según el idioma solicitado, sin modificar el código fuente.