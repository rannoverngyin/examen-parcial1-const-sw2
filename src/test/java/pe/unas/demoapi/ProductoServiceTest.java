package pe.unas.demoapi;


import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.ProductoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProductoServiceTest {

    @Test
    void debeListarProductosIniciales(){
        ProductoService service = new ProductoService();
        assertEquals(2, service.listar().size() );
    }

    @Test
    void listalaptop(){
        ProductoService service = new ProductoService();
        assertTrue(service.listar().contains("laptop"));
    }

}
