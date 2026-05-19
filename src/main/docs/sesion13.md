# MANTENIMETOS DE BANCO DE PRUEBAS

## Creación del service

package mantenimiento.banco_pruebas.application;

import org.springframework.stereotype.Service; 
import java.util.*;
 
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
        productos.add(nombre.trim()); 
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

2026-05-19T14:08:24.294-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-05-19T14:08:24.295-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.40]
2026-05-19T14:08:24.325-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2026-05-19T14:08:24.326-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 573 ms
2026-05-19T14:08:24.567-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2026-05-19T14:08:24.595-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2026-05-19T14:08:24.599-05:00  INFO 6972 --- [banco_pruebas] [  restartedMain] m.banco_pruebas.BancoPruebasApplication  : Started BancoPruebasApplication in 1.138 seconds (process running for 1.388)


# Creación del banco de pruebas

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
 
import static org.junit.jupiter.api.Assertions.*; 
 
class ProductoServiceTest { 
 
    private ProductoService service; 
 
    @BeforeEach 
    void preparar() { 
        service = new ProductoService(); 
    } 
 
    @Test 
    @DisplayName("Debe listar productos iniciales") 
    void debeListarProductosIniciales() { 
        assertEquals(2, service.total()); 
        assertTrue(service.existe("Laptop")); 
        assertTrue(service.existe("Mouse")); 
    } 
 
    @Test 
    @DisplayName("Debe agregar un producto válido") 
    void debeAgregarProductoValido() { 
        service.agregar("Teclado"); 
 
        assertEquals(3, service.total()); 
        assertTrue(service.existe("Teclado")); 
    } 
 
    @Test 
    @DisplayName("Debe eliminar un producto existente") 
    void debeEliminarProductoExistente() { 
        service.eliminar("Mouse"); 
 
        assertEquals(1, service.total()); 
        assertFalse(service.existe("Mouse")); 
    } 
 
    @Test 
    @DisplayName("No debe aceptar producto vacío") 
    void noDebeAceptarProductoVacio() { 
        assertThrows(IllegalArgumentException.class, () -> service.agregar("")); 
        assertThrows(IllegalArgumentException.class, () -> service.agregar("   ")); 
        assertThrows(IllegalArgumentException.class, () -> service.agregar(null)); 
    } 
} 

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mantenimiento.banco_pruebas.application.ProductoServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.041 s -- in mantenimiento.banco_pruebas.application.ProductoServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.796 s
[INFO] Finished at: 2026-05-19T14:12:21-05:00
[INFO] ------------------------------------------------------------------------
PS E:\CURSOS\CONSTRUCCION DE SOFTWARE II\banco_pruebas> 

# creación de nuevo código

@Test 
@DisplayName("No debe aceptar producto duplicado") 
void noDebeAceptarProductoDuplicado() { 
    assertThrows(IllegalArgumentException.class, () -> service.agregar("Laptop")); 
} 

Service
String nombreLimpio = nombre.trim(); 
 
    if (productos.contains(nombreLimpio)) { 
        throw new IllegalArgumentException("El producto ya existe"); 
    } 
 
    productos.add(nombreLimpio); 

RESULTADOS

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running mantenimiento.banco_pruebas.application.ProductoServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.048 s -- in mantenimiento.banco_pruebas.application.ProductoServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.123 s
[INFO] Finished at: 2026-05-19T14:45:21-05:00
[INFO] ------------------------------------------------------------------------