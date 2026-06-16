/*package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductoControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarProductos_debeResponderOk() {
        ResponseEntity<String> response =
                restTemplate.getForEntity("/productos", String.class);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Laptop"));
    }

    @Test
    void agregarProducto_debeResponderMensajeCorrecto() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/productos?nombre=Teclado",
                null,
                String.class
        );

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Producto agregado", response.getBody());
    }

    @Test
    void existeProducto_debeRetornarTrueCuandoExiste() throws Exception {
        mockMvc.perform(get("/productos/existe").param("nombre", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
*/