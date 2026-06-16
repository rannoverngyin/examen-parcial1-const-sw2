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
// Tests para los endpoints de internacionalización
class InternacionalizacionControllerTest {

    // Cliente MockMVC para simular peticiones HTTP
    @Autowired
    private MockMvc mockMvc;

    // Verifica que el saludo se entregue en español
    @Test
    void debeResponderSaludoEnEspanol() throws Exception {
        mockMvc.perform(get("/i18n/saludo").param("lang", "es"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bienvenido")));
    }

    // Verifica que el saludo se entregue en inglés
    @Test
    void debeResponderSaludoEnIngles() throws Exception {
        mockMvc.perform(get("/i18n/saludo").param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));
    }
}