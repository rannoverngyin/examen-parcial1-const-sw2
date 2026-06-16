package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InternacionalizacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeResponderSaludoEnEspanol() throws Exception {
        mockMvc.perform(get("/i18n/saludo").param("lang", "es"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bienvenido")));
    }

    @Test
    void debeResponderSaludoEnIngles() throws Exception {
        mockMvc.perform(get("/i18n/saludo").param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));
    }

    @Test
    void debeResponderSaludoEnEspanolConHeader() throws Exception {
        mockMvc.perform(get("/i18n/saludo-header").header("Accept-Language", "es"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bienvenido")));
    }

    @Test
    void debeResponderSaludoEnInglesConHeader() throws Exception {
        mockMvc.perform(get("/i18n/saludo-header").header("Accept-Language", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));
    }

    @Test
    void debeResponderEvaluacionEnEspanol() throws Exception {
        mockMvc.perform(get("/i18n/evaluacion").param("lang", "es"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("correctamente")));
    }

    @Test
    void debeResponderEvaluacionEnIngles() throws Exception {
        mockMvc.perform(get("/i18n/evaluacion").param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
    }
}
