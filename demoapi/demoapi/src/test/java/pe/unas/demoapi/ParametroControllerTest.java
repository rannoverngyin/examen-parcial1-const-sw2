package pe.unas.demoapi;

import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Verifica la salida de parámetros.
class ParametroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeMostrarVersionConfigurada() throws Exception {
        // Valida la versión expuesta.
        mockMvc.perform(get("/parametros/version"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1.0.0")));
    }
}