\# Reporte de Pruebas - Sesión 12



\## Pruebas ejecutadas

\- listarProductos\_debeRetornarStatus200YListaInicial

\- agregarProducto\_debeRetornarMensajeYActualizarLista

\- eliminarProducto\_debeRetirarProductoDeLaLista

\- totalProductos\_debeRetornarCantidadInicial

\- existeProducto\_debeRetornarTrueCuandoExiste



\## Resultado

Tests run: 9, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

\## Capturas
 Captura 1 — BUILD SUCCESS en PowerShell

![alt text](image-4.png)

Captura 2 — Archivo de prueba abierto en tu editor

![alt text](image-5.png)

Captura 3 — Endpoint /productos en el navegador

![alt text](image-6.png)

Captura 4 — Endpoint /productos/existe en el navegador

![alt text](image-7.png)

Captura 5 — Commit visible en GitHub

![alt text](image-8.png)

Captura 6 — Verificacion en el GitHub

![alt text](image-9.png)

\## Flujo: MockMvc → Controller → Service

MockMvc es una herramienta de pruebas que simula peticiones HTTP sin necesitar un navegador ni levantar el servidor real. Actúa como si fuera un cliente que hace peticiones a tu API.

El flujo funciona así:

1. MockMvc envía la petición
mockMvc.perform(get("/productos"))
Simula que alguien hace un GET al endpoint /productos, igual que si lo hicieras desde el navegador.

2. El Controller recibe la petición
ProductoController → @GetMapping → listar()
Spring enruta la petición al método correcto del controller según la ruta y el método HTTP.

3. El Controller llama al Service
ProductoService → listar() → retorna ["Laptop", "Mouse"]
El controller delega la lógica al service, que devuelve los datos.

4. MockMvc verifica la respuesta
.andExpect(status().isOk())
.andExpect(jsonPath("$[0]").value("Laptop"))
La prueba comprueba que el código de estado es 200 y que los datos son los esperados.

\## Conclusión

Los endpoints REST responden correctamente verificando

rutas, métodos HTTP, códigos de estado y respuestas JSON.

