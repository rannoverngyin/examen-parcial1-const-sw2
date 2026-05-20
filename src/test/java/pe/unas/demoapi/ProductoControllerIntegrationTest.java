package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductoControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void listarProductos_debeResponderOk() {
        ResponseEntity<String> response = restTemplate.getForEntity("/productos", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("laptop"));
    }

    @Test
    void agregarProducto_debeResponderMensajeCorrecto() {
        ResponseEntity<String> response = restTemplate.postForEntity("/productos?nombre=Teclado", null, String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("producto agregado", response.getBody());
    }

}
