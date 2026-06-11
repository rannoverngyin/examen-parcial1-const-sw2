package pe.unas.medioCurso.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoServiceTest {

    @Test
    void debeRetornarTotalProductos() {
        ProductoService service = new ProductoService();

        assertEquals(1, service.total());
    }
}