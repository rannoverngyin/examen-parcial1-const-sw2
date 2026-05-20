# Banco de Pruebas – Sesión 13

## Módulo Evaluado
**ProductoService**

---

## Casos de Prueba

| ID | Caso de Prueba | Prioridad | Resultado Esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna Laptop y Mouse, total = 2 |
| PU-02 | Agregar producto válido | Alta | Incrementa total a 3 y producto existe |
| PU-03 | Eliminar producto existente | Media | Reduce total a 1 y producto ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException |

---

## Archivos Utilizados

### Clase bajo prueba
- [ProductoService.java](src/main/java/pe/unas/demoapi/application/ProductoService.java)

### Clase de prueba
- [ProductoServiceTest.java](src/test/java/pe/unas/demoapi/application/ProductoServiceTest.java)

---

## Métodos de Prueba Implementados

```java
@Test
@DisplayName("Debe listar productos iniciales")
void debeListarProductosIniciales()
```
✅ Verifica que el servicio inicia con 2 productos (Laptop, Mouse)

```java
@Test
@DisplayName("Debe agregar un producto válido")
void debeAgregarProductoValido()
```
✅ Verifica que se agrega correctamente y el total incrementa

```java
@Test
@DisplayName("Debe eliminar un producto existente")
void debeEliminarProductoExistente()
```
✅ Verifica que se elimina y el total disminuye

```java
@Test
@DisplayName("No debe aceptar producto vacío")
void noDebeAceptarProductoVacio()
```
✅ Verifica que rechaza valores nulos, vacíos y espacios en blanco

---

## Comando de Ejecución

```bash
.\mvnw test
```

---

## Resultado de Ejecución

```
BUILD SUCCESS ✅

Tests run: 4
Failures: 0
Errors: 0
Skipped: 0
Success rate: 100%
```

---

## Conclusión

✅ **ProductoService validado completamente**  
✅ **Todos los casos de prueba pasados**  
✅ **Validaciones de entrada funcionando correctamente**  
✅ **Servicio listo para integración**
