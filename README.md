# Guía de Práctica – Sesión 12
## Construcción de Software II – Pruebas de Integración en Servicios REST

**Tema:** Pruebas de integración en servicios REST con Spring Boot
**Producto:** Suite de pruebas de integración para endpoints REST usando MockMvc
**Duración:** 60 minutos
**Evidencia:** Pruebas exitosas + capturas + commit/push en GitHub

---

## 1. Objetivo de la práctica

Validar el funcionamiento integrado de servicios REST en una aplicación Spring Boot, verificando rutas, métodos HTTP, códigos de estado, respuestas JSON y comportamiento entre las capas Presentation y Application.

- Comprobar endpoints GET, POST y DELETE mediante pruebas automatizadas.
- Usar `@SpringBootTest`, `@AutoConfigureMockMvc` y `MockMvc` para validar contratos REST.
- Documentar evidencias de ejecución de pruebas con Maven.
- Registrar el incremento mediante Git y Pull Request.

---

## 2. Requisitos previos

- Java 17 instalado y activo.
- Maven funcionando: `mvn -version`.
- Proyecto Spring Boot con dependencia Spring Web y `spring-boot-starter-test`.
- Estructura base: domain, application y presentation.
- Endpoint `/productos` implementado desde las sesiones anteriores.

---

## 3. Ubicarse en la raíz del proyecto

Desde la terminal, ingresar al proyecto Spring Boot. La carpeta debe contener el archivo `pom.xml`.

```bash
cd examen-parcial1-const-sw2
ls
```

Salida esperada:
```
pom.xml  mvnw  mvnw.cmd  src/
```

---

## 4. Verificar dependencia de pruebas

Abre el archivo `pom.xml` y verifica que exista la dependencia de pruebas. Spring Initializr normalmente la agrega por defecto.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 5. Código base del servicio REST a probar

Si ya tienes `ProductoService` y `ProductoController`, verifica que estén completos.

### 5.1. ProductoService.java

Ruta: `src/main/java/pe/unas/demoapi/application/ProductoService.java`

```java
package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<String> productos = new ArrayList<>();

    public ProductoService() {
        productos.add("Laptop");
        productos.add("Mouse");
    }

    public List<String> listar() {
        return productos;
    }

    public void agregar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        String nombreLimpio = nombre.trim();
        if (productos.contains(nombreLimpio)) {
            throw new IllegalArgumentException("El producto ya existe");
        }
        productos.add(nombreLimpio);
    }

    public void eliminar(String nombre) {
        productos.remove(nombre);
    }

    public int total() {
        return productos.size();
    }

    public boolean existe(String nombre) {
        return productos.contains(nombre);
    }
}
```

### 5.2. ProductoController.java

Ruta: `src/main/java/pe/unas/demoapi/presentation/ProductoController.java`

```java
package pe.unas.demoapi.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.ProductoService;

@RestController
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping("/productos")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping("/productos")
    public ResponseEntity<String> agregar(@RequestParam String nombre) {
        service.agregar(nombre);
        return ResponseEntity.ok("Producto agregado");
    }

    @DeleteMapping("/productos")
    public ResponseEntity<String> eliminar(@RequestParam String nombre) {
        service.eliminar(nombre);
        return ResponseEntity.ok("Producto eliminado");
    }

    @GetMapping("/productos/total")
    public ResponseEntity<Integer> total() {
        return ResponseEntity.ok(service.total());
    }

    @GetMapping("/productos/existe")
    public ResponseEntity<Boolean> existe(@RequestParam String nombre) {
        return ResponseEntity.ok(service.existe(nombre));
    }
}
```

---

## 6. Crear prueba de integración REST

Crear el paquete y archivo de prueba en:
`src/test/java/pe/unas/demoapi/presentation/ProductoControllerIntegrationTest.java`

```java
package pe.unas.demoapi.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarProductos_debeRetornarStatus200YListaInicial() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Laptop"))
                .andExpect(jsonPath("$[1]").value("Mouse"));
    }

    @Test
    void agregarProducto_debeRetornarMensajeYActualizarLista() throws Exception {
        mockMvc.perform(post("/productos").param("nombre", "Teclado"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto agregado"));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("Teclado")));
    }

    @Test
    void eliminarProducto_debeRetirarProductoDeLaLista() throws Exception {
        mockMvc.perform(delete("/productos").param("nombre", "Mouse"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado"));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(hasItem("Mouse"))));
    }

    @Test
    void totalProductos_debeRetornarCantidadInicial() throws Exception {
        mockMvc.perform(get("/productos/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void existeProducto_debeRetornarTrueCuandoExiste() throws Exception {
        mockMvc.perform(get("/productos/existe").param("nombre", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
```

---

## 7. Ejecutar pruebas

Desde la raíz del proyecto ejecuta:

```bash
./mvnw test
```

También puedes usar Maven instalado:

```bash
mvn test
```

Resultado esperado:
```
Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## 8. Interpretación de resultados

| Prueba | Qué valida | Resultado esperado |
|---|---|---|
| listarProductos | GET /productos, status 200 y JSON inicial | Laptop y Mouse |
| agregarProducto | POST /productos y actualización de lista | Producto agregado y Teclado visible |
| eliminarProducto | DELETE /productos y eliminación real | Mouse ya no aparece |
| totalProductos | GET /productos/total | 2 |
| existeProducto | GET /productos/existe?nombre=Laptop | true |

---

## 9. Ejercicio aplicado

Agregar un nuevo endpoint y su prueba de integración:

```
GET /productos/existe?nombre=Laptop
```

Debe devolver `true` si el producto existe y `false` si no existe.

En `ProductoService` agrega:

```java
public boolean existe(String nombre) {
    return productos.contains(nombre);
}
```

En `ProductoController` agrega:

```java
@GetMapping("/productos/existe")
public ResponseEntity<Boolean> existe(@RequestParam String nombre) {
    return ResponseEntity.ok(service.existe(nombre));
}
```

En la prueba de integración agrega:

```java
@Test
void existeProducto_debeRetornarTrueCuandoExiste() throws Exception {
    mockMvc.perform(get("/productos/existe").param("nombre", "Laptop"))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
}
```

---

## 10. Registrar avance con Git

```bash
git status
git add .
git commit -m "Agrega pruebas de integracion REST"
git push
```

---

## 11. Errores frecuentes

| Error | Solución |
|---|---|
| 404 en /productos | Verifica `@RequestMapping("/productos")` y que el Controller esté dentro de `pe.unas.demoapi`. |
| 405 Method Not Allowed | Verifica que exista `@PostMapping` o `@DeleteMapping` para el método usado. |
| Bean ProductoService not found | Agrega `@Service` en ProductoService y verifica el package. |
| Tests fallan por datos alterados | Usa `@DirtiesContext` o evita que una prueba dependa de otra. |
| No compila jsonPath/hasItem | Verifica imports estáticos de Hamcrest y MockMvcResultMatchers. |

---

## 12. Evidencia de entrega

- Captura de `mvn test` con `BUILD SUCCESS`.
- Captura del archivo `ProductoControllerIntegrationTest.java`.
- Captura del repositorio con commit realizado.
- Breve explicación del flujo: MockMvc → Controller → Service.

---

## 13. Rúbrica rápida

| Criterio | Logro esperado | Puntaje |
|---|---|---|
| ProductoService completo | listar, agregar, eliminar, total, existe | 2 |
| ProductoController REST | GET, POST, DELETE, /total, /existe | 2 |
| Prueba de integración | @SpringBootTest + MockMvc + 5 tests | 3 |
| Tests ejecutan sin errores | BUILD SUCCESS | 2 |
| Endpoint /existe implementado | Controller + Service + Test | 1 |
