# Reporte de Pruebas - Sesión 11

## Pruebas ejecutadas
- EmailNotificadorServiceTest
- ParametroControllerTest

## Resultado
BUILD SUCCESS

## Evidencias
-	Captura de endpoints /parametros funcionando.
-	Captura de notificación con proveedor email.
-	Captura de notificación con proveedor mock.
-	Captura de ./mvnw test con BUILD SUCCESS.
-	Commit y Pull Request en GitHub.

![Captura de los endpoints /parametros funcionando](PARAMETROS.png)
![Captura de notificación con proveedor email](NOTIFICACIONEMAIL.png)
![Captura de notificación con proveedor mock](NOTIFICACIONMOCK.png)
![Captura de la prueba unitaria del proveedor email](EMAILTEST.png)
![Captura de la prueba de integración de parámetros](PARAMETROSTEST.png)
![Captura de la salida de la terminal después de probar los tests (incluye tests anteriores)](TESTS.png)


## Conclusión
La API responde correctamente y el servicio mantiene consistencia básica ante solicitudes concurrentes.
