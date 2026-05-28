| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa el total y el producto existe |
| PU-03 | Eliminar producto existente | Media | Reduce el total y el producto ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException |
| PU-05 | Rechazar producto duplicado | Alta | Lanza IllegalArgumentException cuando el producto ya existe |