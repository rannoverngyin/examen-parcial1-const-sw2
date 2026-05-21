# Banco de pruebas – Sesión 13

## Módulo evaluado

ProductoService

## Descripción

El banco de pruebas permite organizar, mantener y ejecutar pruebas unitarias automatizadas para verificar el comportamiento esperado del sistema.

En esta práctica se evaluó la clase ProductoService, la cual permite listar, agregar, eliminar y validar productos. Además, se agregó una prueba de regresión para evitar que se registren productos duplicados.

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna los productos iniciales Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa el total de productos y el producto agregado existe |
| PU-03 | Eliminar producto existente | Media | Reduce el total de productos y el producto eliminado ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException cuando el nombre es vacío, contiene solo espacios o es null |
| PU-05 | Rechazar producto duplicado | Alta | Lanza IllegalArgumentException cuando se intenta agregar un producto existente |

## Comando de ejecución

```bash
./mvnw test