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


-evidencias de los  casos de pruebas


## Comando de ejecución
./mvnw test
## Evidencia
Captura de BUILD SUCCESS de la prueva ProductoServiceTest.java
![alt text](image.png)


## Pruebas con Postman 

3. Peticiones incluidas en la colección:
- GET `http://localhost:8080/productos` — espera JSON con `Laptop` y `Mouse`.
![alt text](image-1.png)


- POST `http://localhost:8080/productos` — body form `nombre=Telefono` — espera `producto agregado`.
![alt text](image-2.png)

- DELETE `http://localhost:8080/productos` — body form `nombre=Telefono` — espera `Producto eliminado`.
![alt text](image-3.png)


- Ejercicio Aplicado
![alt text](image-5.png)
![alt text](image-6.png)