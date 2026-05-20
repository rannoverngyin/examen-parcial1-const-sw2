# Banco de Pruebas – Sesión 13

## 1. Módulo Evaluado

| Propiedad | Valor |
|-----------|-------|
| **Nombre del módulo** | ProductoService |
| **Tipo** | Servicio de aplicación |
| **Ubicación** | pe.unas.demoapi.application |
| **Responsabilidad** | Gestión de productos |

---

## 2. Estado Inicial del Servicio

| Atributo | Valor |
|----------|-------|
| Productos iniciales | Laptop, Mouse |
| Total inicial | 2 |
| Tipo de almacenamiento | ArrayList<String> |
| Constructor | Inicializa con 2 productos predeterminados |

---

## 3. Especificación de Casos de Prueba

| ID | Caso de Prueba | Prioridad | Entrada | Resultado Esperado | Método Probado |
|----|----------------|-----------|---------|-------------------|-----------------|
| PU-01 | Listar productos iniciales | Alta | N/A | total() == 2, existe("Laptop") == true, existe("Mouse") == true | total(), existe() |
| PU-02 | Agregar producto válido | Alta | nombre="Teclado" | total() == 3, existe("Teclado") == true | agregar(), total(), existe() |
| PU-03 | Eliminar producto existente | Media | nombre="Mouse" | total() == 1, existe("Mouse") == false | eliminar(), total(), existe() |
| PU-04 | Rechazar producto vacío | Alta | "", "   ", null | Lanza IllegalArgumentException | agregar() |

---

## 4. Archivos Involucrados

### Clase Bajo Prueba
```
Archivo: ProductoService.java
Ruta: src/main/java/pe/unas/demoapi/application/ProductoService.java
Componente: @Service
```

**Métodos públicos:**
- `ProductoService()` - Constructor que inicializa productos
- `List<String> listar()` - Retorna lista de productos
- `void agregar(String nombre)` - Agrega producto con validación
- `void eliminar(String nombre)` - Elimina un producto
- `int total()` - Retorna cantidad de productos
- `boolean existe(String nombre)` - Verifica existencia de producto

### Clase de Prueba
```
Archivo: ProductoServiceTest.java
Ruta: src/test/java/pe/unas/demoapi/application/ProductoServiceTest.java
Framework: JUnit 5
```

**Configuración:**
- `@BeforeEach preparar()` - Crea nueva instancia de ProductoService antes de cada prueba

---

## 5. Definición de Casos de Prueba

### Caso PU-01: Listar productos iniciales

| Campo | Valor |
|-------|-------|
| **Descripción** | Verificar que el servicio retorna los productos iniciales |
| **Precondición** | ProductoService inicializado |
| **Entrada** | N/A |
| **Pasos** | 1. Llamar service.total() |
| | 2. Llamar service.existe("Laptop") |
| | 3. Llamar service.existe("Mouse") |
| **Resultado esperado** | total == 2, ambos productos existen |
| **Método de Prueba** | debeListarProductosIniciales() |
| **Assertions** | assertEquals(2, service.total()), assertTrue(service.existe("Laptop")), assertTrue(service.existe("Mouse")) |

---

### Caso PU-02: Agregar producto válido

| Campo | Valor |
|-------|-------|
| **Descripción** | Verificar que un producto válido se agrega correctamente |
| **Precondición** | ProductoService con 2 productos |
| **Entrada** | nombre = "Teclado" |
| **Pasos** | 1. Llamar service.agregar("Teclado") |
| | 2. Llamar service.total() |
| | 3. Llamar service.existe("Teclado") |
| **Resultado esperado** | total == 3, nuevo producto existe |
| **Método de Prueba** | debeAgregarProductoValido() |
| **Assertions** | assertEquals(3, service.total()), assertTrue(service.existe("Teclado")) |

---

### Caso PU-03: Eliminar producto existente

| Campo | Valor |
|-------|-------|
| **Descripción** | Verificar que un producto se elimina correctamente |
| **Precondición** | ProductoService con 2 productos |
| **Entrada** | nombre = "Mouse" |
| **Pasos** | 1. Llamar service.eliminar("Mouse") |
| | 2. Llamar service.total() |
| | 3. Llamar service.existe("Mouse") |
| **Resultado esperado** | total == 1, producto no existe |
| **Método de Prueba** | debeEliminarProductoExistente() |
| **Assertions** | assertEquals(1, service.total()), assertFalse(service.existe("Mouse")) |

---

### Caso PU-04: Rechazar producto vacío

| Campo | Valor |
|-------|-------|
| **Descripción** | Verificar que se rechaza entrada inválida |
| **Precondición** | ProductoService inicializado |
| **Entrada** | "", "   ", null |
| **Pasos** | 1. Llamar service.agregar("") |
| | 2. Llamar service.agregar("   ") |
| | 3. Llamar service.agregar(null) |
| **Resultado esperado** | Lanza IllegalArgumentException en cada caso |
| **Método de Prueba** | noDebeAceptarProductoVacio() |
| **Assertions** | assertThrows(IllegalArgumentException.class, ...) para cada entrada |
| **Mensaje de error** | "El nombre del producto es obligatorio" |

---

## 6. Comando de Ejecución

```
Comando: .\mvnw test
Ubicación: Raíz del proyecto
Descripción: Ejecuta todas las pruebas unitarias
```

---

## 7. Resultado de Ejecución

| Métrica | Valor |
|---------|-------|
| Resultado | BUILD SUCCESS |
| Tests ejecutados | 4 |
| Tests pasados | 4 |
| Fallos | 0 |
| Errores | 0 |
| Omitidos | 0 |
| Tasa de éxito | 100% |
| Tiempo total | 0.300s |

---

## 8. Cobertura de Pruebas

| Método | Cubierto por | Estado |
|--------|-------------|--------|
| listar() | PU-01 | Probado |
| agregar() | PU-02, PU-04 | Probado |
| eliminar() | PU-03 | Probado |
| total() | PU-01, PU-02, PU-03 | Probado |
| existe() | PU-01, PU-02, PU-03 | Probado |

---

## 9. Conclusiones

| Aspecto | Estado |
|--------|--------|
| Funcionalidad básica | Validado |
| Validación de entrada | Validada |
| Manejo de excepciones | Validado |
| Cobertura de métodos | 100% |
| Calidad del código de prueba | Aceptada |
| Recomendación | Apto para integración |
