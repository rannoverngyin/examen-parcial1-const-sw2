# Banco de pruebas – Sesión 13

## Módulo evaluado

ProductoService

## Descripción

El banco de pruebas permite organizar, ejecutar y mantener las pruebas unitarias del sistema.  
En esta práctica se valida el comportamiento de la clase ProductoService, considerando operaciones como listar, agregar, eliminar y validar productos.

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna los productos iniciales Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa el total de productos y el producto agregado existe |
| PU-03 | Eliminar producto existente | Media | Reduce el total de productos y el producto eliminado ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException cuando el nombre es vacío, espacios o null |
| PU-05 | Rechazar producto duplicado | Alta | Lanza IllegalArgumentException cuando se intenta agregar un producto existente |

## Comando de ejecución

./mvnw test

## Resultado obtenido

Se ejecutó correctamente el banco de pruebas con Maven.

Resultado de la ejecución:

- Tests run: 5
- Failures: 0
- Errors: 0
- Skipped: 0
- BUILD SUCCESS

## Evidencia

- Captura de consola con ./mvnw test y BUILD SUCCESS.
- Archivo ProductoServiceTest.java implementado.
- Archivo TEST_BANK.md actualizado.
- Workflow de GitHub Actions creado.
- Commit y push realizados en la rama feature.
- Pull Request creado en GitHub.