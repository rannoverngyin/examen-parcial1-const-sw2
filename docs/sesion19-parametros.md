# Sesión 19 - Implementa configuracion y parametrizacion
## Parámetros iniciales

Se configuraron los parámetros externos en `application.properties` con el proveedor `email`.

![Parametros Iniciales](<img/sesion19/Parametros Iniciales.png>)

## Servicios de la capa de aplicación

Se creó `ParametroService` con inyección de valores usando `@Value`.

![Paramerto Service](<img/sesion19/Paramerto Service.png>)

Se definió la interfaz `NotificadorService` para desacoplar el controlador del proveedor.

![Notificador Service](<img/sesion19/Notificador Service.png>)

Se implementó `EmailNotificadorService` activo cuando `app.notificacion.proveedor=email`.

![Email Service](<img/sesion19/Email Service.png>)

Se implementó `MockNotificadorService` activo cuando `app.notificacion.proveedor=mock`.

![Mock Service](<img/sesion19/Mock Service.png>)

## Controladores

Se creó `ParametroController` con los endpoints GET `/parametros/...`.

![Parametro Controller](<img/sesion19/Parametro Controller.png>)

Se creó `NotificacionController` con el endpoint POST `/notificaciones/enviar`.

![Notificacion Controller](<img/sesion19/Notificacion Controller.png>)

## Aplicación corriendo

Se ejecutó `./mvnw spring-boot:run` y la aplicación inició en el puerto 8080.

![Programa Corriendo](<img/sesion19/Programa Corriendo.png>)

## Resultado de los endpoints

Prueba de `/parametros/institucion`, `/parametros/modo`, `/parametros/limite-usuarios` y notificación por EMAIL.

![Resultado de los parametros](<img/sesion19/Resultado de los parametros.png>)

## Cambio de proveedor sin modificar código

Se cambió `app.notificacion.proveedor=mock` en `application.properties`.

![Cambio para mock](<img/sesion19/Cambio para mock.png>)

Resultado: el sistema responde con notificación simulada.

![Resultado del cambio mock](<img/sesion19/Resultado del cambio mock.png>)

## Pruebas

Se ejecutó la prueba unitaria `EmailNotificadorServiceTest`.

![Prueba del Test email](<img/sesion19/Prueba del Test email.png>)

Se ejecutó la prueba de integración `ParametroControllerTest`.

![Prueba del Test parametro](<img/sesion19/Prueba del Test parametro.png>)

Resultado: BUILD SUCCESS — 38 tests, 0 fallos.

![BUILD SUCCESS](<img/sesion19/BUILD SUCCESS.png>)

## Ejercicio aplicado

Se agregó `app.version-sistema=1.0.0` y se expuso en `GET /parametros/version`.

Resultado del endpoint `/parametros/version`:

![Resultado Aplicado](<img/sesion19/Resultado Aplicado.png>)

BUILD SUCCESS con el ejercicio aplicado — 39 tests, 0 fallos.

![BUILD SUCCESS Aplicado](<img/sesion19/BUILD SUCCESS Aplicado.png>)