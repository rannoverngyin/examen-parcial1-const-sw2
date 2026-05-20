
package unas.pe.demo.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias para ProductoService - Banco Mantenedor")
class ProductoServiceTest {

    private ProductoService service;

    @BeforeEach
    void preparar() {
        // Garantiza aislamiento: instancia limpia antes de cada prueba
        service = new ProductoService();
    }

    @Test
    @DisplayName("PU-01: Debe listar productos iniciales")
    void debeListarProductosIniciales() {
        assertEquals(2, service.total());
        assertTrue(service.existe("Laptop"));
        assertTrue(service.existe("Mouse"));
    }

    @Test
    @DisplayName("PU-02: Debe agregar un producto válido")
    void debeAgregarProductoValido() {
        service.agregar("Teclado");
        assertEquals(3, service.total());
        assertTrue(service.existe("Teclado"));
    }

    @Test
    @DisplayName("PU-03: Debe eliminar un producto existente")
    void debeEliminarProductoExistente() {
        service.eliminar("Mouse");
        assertEquals(1, service.total());
        assertFalse(service.existe("Mouse"));
    }

    @Test
    @DisplayName("PU-04: No debe aceptar producto vacío o nulo")
    void noDebeAceptarProductoVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.agregar(""));
        assertThrows(IllegalArgumentException.class, () -> service.agregar("   "));
        assertThrows(IllegalArgumentException.class, () -> service.agregar(null));
    }

    @Test
    @DisplayName("PU-05: No debe aceptar producto duplicado (Regresión)")
    void noDebeAceptarProductoDuplicado() {
        assertThrows(IllegalArgumentException.class, () -> service.agregar("Laptop"));
        assertThrows(IllegalArgumentException.class, () -> service.agregar("  Laptop  "));
    }
}