# Sesión 12 – Pruebas de Integración REST

## Objetivo
Validar el funcionamiento integrado de los endpoints REST `/productos` usando MockMvc en Spring Boot.

## Flujo de prueba
MockMvc → Controller → Service → Respuesta HTTP

- **MockMvc** simula las peticiones HTTP (GET, POST, DELETE)
- **ProductoController** recibe la solicitud y delega al Service
- **ProductoService** ejecuta la lógica y devuelve el resultado
- **MockMvc** verifica el status HTTP y el JSON esperado

## Pruebas realizadas

| Prueba | Endpoint | Resultado |
|--------|----------|-----------|
| listarProductos | GET /productos | Status 200, retorna Laptop y Mouse |
| agregarProducto | POST /productos | Producto agregado y visible en lista |
| eliminarProducto | DELETE /productos | Mouse eliminado de la lista |
| totalProductos | GET /productos/total | Retorna 2 |
| existeProducto | GET /productos/existe | Retorna true si el producto existe |

## Evidencia
Ver capturas en la carpeta `docs/img/`