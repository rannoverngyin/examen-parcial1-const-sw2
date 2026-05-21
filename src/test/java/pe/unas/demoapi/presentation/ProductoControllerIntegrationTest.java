package pe.unas.demoapi.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void listarProductos_debeResponderOk() {

        ResponseEntity<String> response =
                restTemplate.getForEntity("/productos", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertTrue(response.getBody().contains("Laptop"));
        assertTrue(response.getBody().contains("Mouse"));
    }

    @Test
    void agregarProducto_debeResponderMensajeCorrecto() {

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/productos?nombre=Teclado",
                        null,
                        String.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto agregado", response.getBody());
    }

    @Test
    void agregarProductoVacio_debeRetornarError() {

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/productos?nombre=",
                        null,
                        String.class
                );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode());
    }

    @Test
    void agregarProductoDuplicado_debeRetornarError() {

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/productos?nombre=Laptop",
                        null,
                        String.class
                );

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode());
    }

    @Test
    void eliminarProducto_debeResponderCorrectamente() {

        // agregar primero
        restTemplate.postForEntity(
                "/productos?nombre=Tablet",
                null,
                String.class
        );

        ResponseEntity<String> response =
                restTemplate.exchange(
                        "/productos?nombre=Tablet",
                        HttpMethod.DELETE,
                        null,
                        String.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto eliminado", response.getBody());
    }

    @Test
    void totalProductos_debeRetornarCantidad() {

        ResponseEntity<Integer> response =
                restTemplate.getForEntity(
                        "/productos/total",
                        Integer.class
                );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertTrue(response.getBody() >= 2);
    }
}