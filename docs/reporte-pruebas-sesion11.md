# Reporte de Pruebas - Sesión 11

## 1. Pruebas ejecutadas

Se ejecutaron pruebas de integración y concurrencia para la API de productos desarrollada con Java, Spring Boot y JUnit 5.

Las pruebas ejecutadas fueron:

- ProductoControllerIntegrationTest
- ProductoServiceConcurrencyTest
- ProductoApiConcurrencyTest

## 2. Comando utilizado

El comando utilizado para ejecutar las pruebas fue:

mvn test

## 3. Resultado obtenido

La ejecución final de las pruebas fue exitosa.

Resultado:

[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.062 s -- in pe.unas.demoapi11.ProductoControllerIntegrationTest
[INFO] Running pe.unas.demoapi11.ProductoServiceConcurrencyTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.008 s -- in pe.unas.demoapi11.ProductoServiceConcurrencyTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.448 s
[INFO] Finished at: 2026-05-17T16:19:00-05:00
[INFO] ------------------------------------------------------------------------

## 4. Evidencias

Como evidencia se considera:

- Captura de la ejecución del comando mvn test.
- Captura de los archivos de prueba creados.
- Resultado BUILD SUCCESS mostrado en la terminal.

## 5. Conclusión

La API responde correctamente y el servicio mantiene consistencia básica ante solicitudes concurrentes.

## 6. explicación del flujo de prueba

El flujo validado fue:

Controller -> Service -> Spring Context

Las pruebas de integración levantan el contexto de Spring Boot y verifican que el Controller pueda responder correctamente utilizando el Service.
Las pruebas de concurrencia simulan varias operaciones al mismo tiempo para comprobar que el servicio mantiene un conteo consistente y no presenta errores por acceso concurrente.

## 7. Conclusión

La API responde correctamente ante solicitudes normales y mantiene consistencia básica ante operaciones concurrentes.
Además, las pruebas automatizadas permiten comprobar que el Controller, el Service y el contexto de Spring Boot trabajan de forma integrada.