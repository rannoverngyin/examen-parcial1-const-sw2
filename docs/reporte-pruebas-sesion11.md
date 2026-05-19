# Reporte de Pruebas - Sesión 11

## Tema

Pruebas de concurrencia en APIs y pruebas de integración con Java, Spring Boot y JUnit 5.

## Pruebas ejecutadas

### 1. ProductoControllerIntegrationTest

Se implementó una prueba de integración para validar el funcionamiento del endpoint `/productos`.

Esta prueba verifica:

- Que el endpoint `/productos` responda correctamente mediante GET.
- Que la respuesta HTTP sea exitosa.
- Que el cuerpo de la respuesta contenga el producto inicial "Laptop".
- Que se pueda agregar un producto usando POST.
- Que el mensaje devuelto sea "Producto agregado".

### 2. ProductoServiceConcurrencyTest

Se implementó una prueba de concurrencia directamente sobre el servicio `ProductoService`.

Esta prueba verifica:

- Que varios hilos puedan agregar productos al mismo tiempo.
- Que el servicio mantenga el conteo correcto de productos.
- Que no existan errores por acceso concurrente a la lista de productos.

### 3. ProductoApiConcurrencyTest

Se implementó una prueba concurrente sobre la API usando `TestRestTemplate`.

Esta prueba simula múltiples solicitudes POST al endpoint `/productos`, validando que todas respondan correctamente.

## Resultado

Se ejecutaron las pruebas con el comando:

```bash
./mvnw test