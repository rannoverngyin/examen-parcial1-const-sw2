package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarProductos_debeResponderOkYJson() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").value("Laptop"))
                .andExpect(jsonPath("$[1]").value("Mouse"));
    }

    @Test
    void agregarProducto_debeResponderOkYAgregarNombre() throws Exception {
        mockMvc.perform(post("/productos").param("nombre", "Teclado"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto agregado"));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasItem("Teclado")));
    }

    @Test
    void eliminarProducto_debeResponderOkYRemoverNombre() throws Exception {
        mockMvc.perform(post("/productos").param("nombre", "Monitor"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/productos").param("nombre", "Monitor"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado"));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(not(hasItem("Monitor"))));
    }

    @Test
    void totalProductos_debeResponderOkYNumero() throws Exception {
        mockMvc.perform(get("/productos/total"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNumber());
    }
}
