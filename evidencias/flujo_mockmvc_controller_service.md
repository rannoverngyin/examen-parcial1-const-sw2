# Explicación del flujo MockMvc → Controller → Service

La prueba de integración inicia con MockMvc, que simula peticiones HTTP hacia los endpoints REST del sistema sin levantar manualmente el servidor.

Luego, la solicitud llega al ProductoController, donde se recibe el endpoint correspondiente, por ejemplo GET /productos, POST /productos, DELETE /productos o GET /productos/existe.

Después, el ProductoController llama a ProductoService, que contiene la lógica para listar, agregar, eliminar o verificar productos.

Finalmente, la respuesta retorna al controlador y la prueba valida el resultado mediante el estado HTTP, el contenido devuelto y los datos esperados.

Flujo general:

MockMvc → ProductoController → ProductoService → Respuesta validada por la pruebas
