\# Reporte de Pruebas - Sesión 11


\## Pruebas ejecutadas

\- ProductoControllerIntegrationTest (2 pruebas)

\- ProductoServiceConcurrencyTest (1 prueba)

\- ProductoApiConcurrencyTest (1 prueba)


\## Resultado

[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS

BUILD SUCCESS

\## Evidencias
- Captura de mvn test
![alt text](image.png)
- Captura de endpoint /productos
Codigo del endpoint:
![alt text](image-1.png)
Endponit en el navegador:
![alt text](image-2.png)
- Captura de endpoint /productos/total
![alt text](image-3.png)
\## Conclusión

La API responde correctamente y el servicio mantiene

consistencia ante solicitudes concurrentes.

