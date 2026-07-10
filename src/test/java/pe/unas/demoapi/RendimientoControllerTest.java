package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RendimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void endpointBaseDebeResponderCorrectamente() throws Exception {
        mockMvc.perform(get("/rendimiento/productos/base"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        "application/json"
                ))
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0]").value("LAPTOP"))
                .andExpect(jsonPath("$[1]").value("MOUSE"))
                .andExpect(jsonPath("$[2]").value("TECLADO"))
                .andExpect(jsonPath("$[3]").value("MONITOR"))
                .andExpect(jsonPath("$[4]").value("IMPRESORA"));
    }

    @Test
    void endpointOptimizadoDebeResponderCorrectamente() throws Exception {
        mockMvc.perform(get("/rendimiento/productos/optimizado"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(
                        "application/json"
                ))
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0]").value("LAPTOP"))
                .andExpect(jsonPath("$[1]").value("MOUSE"))
                .andExpect(jsonPath("$[2]").value("TECLADO"))
                .andExpect(jsonPath("$[3]").value("MONITOR"))
                .andExpect(jsonPath("$[4]").value("IMPRESORA"));
    }
}