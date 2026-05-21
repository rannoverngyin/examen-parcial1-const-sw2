# Flujo: MockMvc → Controller → Service

En las pruebas de integración, **MockMvc** simula una petición HTTP (sin levantar servidor real) y la envía al **Controller**, que la recibe, extrae parámetros y delega la lógica al **Service**, el cual ejecuta la operación y devuelve el resultado. Spring serializa la respuesta a JSON y MockMvc la verifica con `andExpect()`.

```
mockMvc.perform(get("/productos"))
        → ProductoController.listar()
            → ProductoService.listar()
                ← ["Laptop", "Mouse"]
        ← 200 OK + JSON ✔
```

Cada capa tiene una responsabilidad clara: MockMvc prueba, el Controller enruta, el Service procesa.