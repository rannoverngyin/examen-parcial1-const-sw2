# Banco de pruebas – Sesión 13

## Módulo evaluado
ProductoService

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa total y producto existe |
| PU-03 | Eliminar producto existente | Media | Reduce total y producto ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException |

## Comando de ejecución
./mvnw test

## Evidencia
Captura de BUILD SUCCESS y commit en GitHub.
![alt text](image-6.png)

## Adicional: Prueba duplicado
![alt text](image-7.png)

Se agregó una prueba para verificar productos duplicados, por lo cual se decidio modificar el método agregar()

![alt text](image-8.png)

Después de la modificación, se hizo la prueba y dio BUILD SUCESS

![alt text](image-9.png)