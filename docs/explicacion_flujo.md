## Breve explicación del flujo: MockMvc → Controller → Service

En esta práctica, `MockMvc` se utiliza para simular peticiones HTTP hacia los endpoints REST sin necesidad de levantar un servidor real. La petición enviada por `MockMvc` llega primero al `ProductoController`, que pertenece a la capa `presentation`.

Luego, el `ProductoController` recibe la solicitud y llama a los métodos correspondientes del `ProductoService`, ubicado en la capa `application`. Esta clase contiene la lógica del sistema, como listar, agregar, eliminar, contar o verificar si existe un producto.

Finalmente, el `ProductoService` devuelve el resultado al `ProductoController`, y este responde al cliente con el código de estado HTTP y el contenido esperado. De esta forma, la prueba verifica que las capas trabajen juntas correctamente y que el contrato REST responda como espera el cliente.