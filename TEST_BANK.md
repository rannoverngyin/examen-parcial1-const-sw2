# Banco de pruebas – Sesión 13

## Módulo evaluado

ProductoService

---

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa total y producto existe |
| PU-03 | Eliminar producto existente | Media | Reduce total y producto ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException |
| PU-05 | Rechazar producto duplicado | Alta | Lanza IllegalArgumentException |

---
## Captura de consola con ./mvnw test y BUILD SUCCESS.
![alt text](image.png)

## Comando de ejecución

```bash
./mvnw test
