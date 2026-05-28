package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.ProductoService;

public class ProductoTest {

    ProductoService service = new ProductoService();

    @Test
    void debeListarDosProductos() {
        assertEquals(2, service.listar().size());
    }

    @Test
    void contieneLaptop() {
        assertTrue(service.listar().contains("Laptop"));
    }

}
