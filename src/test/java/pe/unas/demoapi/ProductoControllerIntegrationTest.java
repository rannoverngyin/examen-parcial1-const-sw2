package pe.unas.demoapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductoControllerIntegrationTest {
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
    @Test
    void existeProducto_debeRetornarTrueCuandoExiste() throws Exception {
        mockMvc.perform(get("/productos/existe").param("nombre", "Laptop"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));

    }

    @Test
    void agregarProducto_rechazaNombreVacio_debeRetornar400() throws Exception {
        mockMvc.perform(post("/productos").param("nombre", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El nombre del producto es obligatorio"));
    }

}
