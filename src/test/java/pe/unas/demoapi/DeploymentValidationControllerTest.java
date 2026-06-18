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

@SpringBootTest(properties = {
        "app.environment=TEST",
        "app.version=1.0.0",
        "app.message=Entorno de pruebas activo"
})
@AutoConfigureMockMvc
class DeploymentValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeMostrarConfiguracionActiva() throws Exception {
        mockMvc.perform(get("/deploy/config"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TEST")));
    }

    @Test
    void debeResponderHealthOk() throws Exception {
        mockMvc.perform(get("/deploy/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }

    @Test
    void debeResponderVersionOk() throws Exception {
        mockMvc.perform(get("/deploy/version"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1.0.0")));
    }
}

