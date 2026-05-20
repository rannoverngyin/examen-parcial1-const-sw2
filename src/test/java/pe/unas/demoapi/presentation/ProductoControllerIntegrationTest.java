package pe.unas.demoapi.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarProductos_debeRetornarStatus200YListaInicial() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Laptop"))
                .andExpect(jsonPath("$[1]").value("Mouse"));
    }

    @Test
    void agregarProducto_debeRetornarMensajeYActualizarLista() throws Exception {
        mockMvc.perform(post("/productos").param("nombre", "Teclado"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto agregado"));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("Teclado")));
    }

    @Test
    void eliminarProducto_debeRetirarProductoDeLaLista() throws Exception {
        mockMvc.perform(delete("/productos").param("nombre", "Mouse"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminado"));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(hasItem("Mouse"))));
    }

    @Test
    void totalProductos_debeRetornarCantidadInicial() throws Exception {
        mockMvc.perform(get("/productos/total"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }
}
