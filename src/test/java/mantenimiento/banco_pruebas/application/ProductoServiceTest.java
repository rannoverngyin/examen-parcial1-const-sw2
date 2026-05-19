package mantenimiento.banco_pruebas.application;

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