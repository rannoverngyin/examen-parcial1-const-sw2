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
![alt text](image-2.png)
 
## Evidencia 
Captura de BUILD SUCCESS

 ![alt text](image.png)

  commit en GitHub.
  
  ![alt text](image-1.png)
