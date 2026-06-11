package sesion20.docker;

import sesion20.docker.application.ProductoService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    @Test
    void obtenerProductos_debeRetornarLista() {

        ProductoService service = new ProductoService();

        List<String> productos = service.obtenerProductos();

        assertNotNull(productos);
        assertEquals(4, productos.size());
        assertTrue(productos.contains("Laptop"));
    }
}