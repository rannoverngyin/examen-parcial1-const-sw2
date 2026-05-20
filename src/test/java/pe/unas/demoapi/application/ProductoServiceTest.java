package pe.unas.demoapi.application;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    // Prueba 1: listar debe retornar productos iniciales (Punto 14)
    @Test
    void listar_debeRetornarProductosIniciales() {
        // Arrange: Preparar datos y objeto bajo prueba
        ProductoService service = new ProductoService();

        // Act: Ejecutar el método que se evalúa
        List<String> resultado = service.listar();

        // Assert: Verificar resultado esperado
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("Laptop"));
        assertTrue(resultado.contains("Mouse"));
    }

    // Prueba 2: agregar debe incrementar el total (Punto 14)
    @Test
    void agregar_debeIncrementarTotalDeProductos() {
        // Arrange
        ProductoService service = new ProductoService();
        int totalInicial = service.total();

        // Act
        service.agregar("Teclado");

        // Assert
        assertEquals(totalInicial + 1, service.total());
    }

    // Prueba 3: eliminar debe reducir el total cuando el producto existe (Punto 14)
    @Test
    void eliminar_debeReducirTotalCuandoProductoExiste() {
        // Arrange
        ProductoService service = new ProductoService();
        int totalInicial = service.total();

        // Act
        service.eliminar("Mouse");

        // Assert
        assertEquals(totalInicial - 1, service.total());
    }

    // Prueba Extra: eliminar inexistente no rompe la lista (Caso de clase)
    @Test
    void eliminar_productoInexistenteNoDebeRomperLaLista() {
        // Arrange
        ProductoService service = new ProductoService();
        int totalInicial = service.total();

        // Act
        service.eliminar("Impresora");

        // Assert
        assertEquals(totalInicial, service.total());
    }
}