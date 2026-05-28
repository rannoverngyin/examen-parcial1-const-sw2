package pe.unas.demoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeEliminarProducto() throws Exception {
        mockMvc.perform(delete("/productos")
                        .param("nombre", "Mouse"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("Producto eliminado")));
    }
}