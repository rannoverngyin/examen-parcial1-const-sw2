package pe.unas.demoapi.application;

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
        assertTrue(service.listar().contains("Laptop"));
        assertTrue(service.listar().contains("Mouse"));
    }

    @Test
    @DisplayName("Debe agregar un producto válido")
    void debeAgregarProductoValido() {
        service.agregar("Teclado");

        assertEquals(3, service.total());
        assertTrue(service.listar().contains("Teclado"));
    }

    @Test
    @DisplayName("Debe eliminar un producto existente")
    void debeEliminarProductoExistente() {
        service.eliminar("Mouse");

        assertEquals(1, service.total());
        assertFalse(service.listar().contains("Mouse"));
    }
}
