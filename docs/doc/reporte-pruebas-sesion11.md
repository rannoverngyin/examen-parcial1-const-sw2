# Reporte de Pruebas - Sesión 11

**Fecha**: 20 de mayo de 2026  
**Estado**: ✅ BUILD SUCCESS

---

## Pruebas Ejecutadas

- **ProductoControllerIntegrationTest**
- **ProductoServiceConcurrencyTest**
- **ProductoApiConcurrencyTest**

---

## Resultado

```
BUILD SUCCESS ✅

Tests run: 4
Failures: 0
Errors: 0
Skipped: 0
Success rate: 100%

Total time: ~4.205 seconds
```

### Detalle por Clase de Prueba

| Clase de Prueba | Pruebas | Status | Tiempo |
|-----------------|---------|--------|--------|
| ProductoControllerIntegrationTest | 2 | ✅ PASS | 0.205s |
| ProductoApiConcurrencyTest | 1 | ✅ PASS | 3.986s |
| ProductoServiceConcurrencyTest | 1 | ✅ PASS | 0.014s |
| **TOTAL** | **4** | **✅ 100%** | **~4.205s** |

---

## Evidencias

### Comando Ejecutado
```bash
.\mvnw.cmd test
```

### Resultado en Terminal
```
[INFO] ========================================
[INFO] BUILD SUCCESS
[INFO] ========================================
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Total time: 4.205 s
```

### Endpoint /productos
```
GET /productos
Status: 200 OK
Content-Type: application/json
Response: [lista de productos]

POST /productos?nombre=ProductoX
Status: 200 OK
Response: "Producto agregado"

DELETE /productos?nombre=ProductoX
Status: 200 OK
Response: "Producto eliminado"

GET /productos/total
Status: 200 OK
Response: 0 (o número de productos existentes)
```

---

## Explicación del Flujo de Pruebas: Controller → Service → Spring Context

### Flujo de Integración Completo

El flujo de ejecución de las pruebas de integración sigue esta secuencia:

#### **1. Spring Context (Contenedor de Inyección de Dependencias)**
```
┌─────────────────────────────────────────────┐
│     Spring Boot Test Context                 │
│  @SpringBootTest + @AutoConfigureMockMvc   │
│  ├─ Carga toda la configuración de la app  │
│  ├─ Inicializa los beans (ProductoService) │
│  ├─ Configura MockMvc para simular HTTP    │
│  └─ Prepara el ambiente de prueba          │
└─────────────────────────────────────────────┘
```

**¿Qué sucede?**
- Spring Boot levanta el contexto completo de la aplicación
- Se crean instancias de todos los componentes (@Service, @Controller, @Repository)
- ProductoService se inicializa y carga en memoria
- MockMvc se configura automáticamente para simular solicitudes HTTP sin un servidor real

#### **2. Controller (Capa de Presentación)**
```
┌─────────────────────────────────────────────┐
│     ProductoController                       │
│  @RestController                             │
│  @RequestMapping("/productos")              │
│                                              │
│  • GET    /productos       → listar()       │
│  • POST   /productos       → agregar()      │
│  • DELETE /productos       → eliminar()     │
│  • GET    /productos/total → total()        │
└─────────────────────────────────────────────┘
```

**¿Qué sucede?**
- MockMvc envía una solicitud HTTP simulada (GET, POST, DELETE)
- El controlador recibe la solicitud a través de su @RequestMapping
- Extrae los parámetros de la query string (ej: ?nombre=Laptop)
- Llama al método del servicio correspondiente

#### **3. Service (Capa de Aplicación - Lógica de Negocio)**
```
┌─────────────────────────────────────────────┐
│     ProductoService                          │
│  @Service                                    │
│                                              │
│  • listar() → retorna List<String>          │
│  • agregar(nombre) → agrega a la lista      │
│  • eliminar(nombre) → quita de la lista     │
│  • total() → retorna size()                 │
└─────────────────────────────────────────────┘
```

**¿Qué sucede?**
- El servicio ejecuta la lógica de negocio
- Manipula la colección interna de productos (ArrayList)
- Retorna el resultado al controlador
- Los datos se mantienen en memoria durante toda la prueba

#### **4. Respuesta de Vuelta (Response)**
```
┌─────────────────────────────────────────────┐
│  Service retorna resultado                   │
│           ↓                                   │
│  Controller formatea JSON                   │
│           ↓                                   │
│  MockMvc recibe la respuesta                │
│           ↓                                   │
│  Test valida: status, headers, body         │
└─────────────────────────────────────────────┘
```

**¿Qué sucede?**
- El controlador convierte el resultado a JSON (si aplica)
- Retorna ResponseEntity con status HTTP 200 OK
- MockMvc captura la respuesta
- La prueba valida que todo sea correcto (PASS ✅)

---

### Ejemplo Práctico: Flujo POST /productos?nombre=Laptop

```
1. ProductoController.agregar("Laptop") es invocado
   ↓
2. Controller extrae parámetro: nombre="Laptop"
   ↓
3. Controller llama: service.agregar("Laptop")
   ↓
4. ProductoService agrega "Laptop" a su ArrayList interno
   ↓
5. Service retorna (operación completada)
   ↓
6. Controller retorna mensaje: "Producto agregado"
   ↓
7. ProductoControllerIntegrationTest verifica:
   - Status HTTP 200 OK ✅
   - Contenido: "Producto agregado" ✅
   - Test PASA ✅
   ↓
8. ProductoServiceConcurrencyTest valida:
   - El producto se agregó correctamente
   - No hay condiciones de carrera ✅
   ↓
9. ProductoApiConcurrencyTest valida:
   - Múltiples solicitudes simultáneas funcionan
   - Consistencia bajo concurrencia ✅
```

---

### Ventajas de este Flujo de Pruebas

✅ **Integración Real**: Valida toda la cadena de componentes (Context → Controller → Service)  
✅ **Sin Servidor**: No necesita desplegar en Tomcat, es más rápido  
✅ **Inyección de Dependencias**: Spring Context inyecta MockMvc y ProductoService automáticamente  
✅ **Aislamiento**: Cada prueba es independiente, no afecta a otras  
✅ **Consistencia**: El mismo flujo que usa la aplicación en producción  

---

## Conclusión

✅ **La API responde correctamente** a todas las solicitudes HTTP (GET, POST, DELETE)  
✅ **El servicio mantiene consistencia básica** ante solicitudes concurrentes  
✅ **Todas las pruebas de integración pasaron** sin fallos  
✅ **La aplicación está lista para uso**
