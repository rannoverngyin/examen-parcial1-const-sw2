# Reporte de Pruebas - Sesión 11

## Pruebas ejecutadas
-ProductoControllerIntegrationTest
-ProductoServiceConcurrencyTest
-ProductoApiConcurrencyTest

## Resultado
[INFO] Results:
[INFO]
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  13.012 s
[INFO] Finished at: 2026-05-17T18:28:59-05:00
[INFO] ------------------------------------------------------------------------


## Evidencias
mvn test 
![alt text][mvntest.png]

endpoint / productos

![alt text][endpoint/producto]


## Conclusión
La API responde correctamente y el servicio mantiene 
consistencia básica ante solicitudes concurrentes.

[mvntest.png]: image.png
[endpoint/producto]: image-1.png