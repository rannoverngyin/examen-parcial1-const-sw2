# Evidencia de entrega

## Capturas guardadas
- `BUILD_SUCCESS_OUTPUT.txt`: salida de `./mvnw test` con `BUILD SUCCESS`.
- `COMMIT-EVIDENCE.txt`: commits recientes y metadata del último commit.
- `ProductoControllerIntegrationTest.java`: copia del archivo de prueba de integración.

## Flujo verificado
1. `MockMvc` envía la petición HTTP de prueba.
2. `ProductoController` recibe la petición y la mapea a la ruta REST.
3. `ProductoController` invoca al `ProductoService`.
4. `ProductoService` ejecuta la lógica de negocio sobre la lista de productos.
5. La respuesta vuelve a través del controlador hacia MockMvc.

## Endpoints cubiertos
- `GET /productos`
- `POST /productos`
- `DELETE /productos`
- `GET /productos/total`
- `GET /productos/existe?nombre=Laptop`

## Resultado de pruebas
- `Tests run: 22, Failures: 0, Errors: 0, Skipped: 0`
- `BUILD SUCCESS`
