package pe.unas.demoapi.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConfiguracionControllerTestTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeRetornarValoresDeTest() throws Exception {
        mockMvc.perform(get("/config/entorno"))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));

        mockMvc.perform(get("/config/mensaje"))
                .andExpect(status().isOk())
                .andExpect(content().string("Entorno de pruebas FIIS"));

        mockMvc.perform(get("/config/soporte"))
                .andExpect(status().isOk())
                .andExpect(content().string("soporte-test@unas.edu.pe"));

        mockMvc.perform(get("/config/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.entorno").value("test"))
                .andExpect(jsonPath("$.mensaje").value("Entorno de pruebas FIIS"))
                .andExpect(jsonPath("$.version").value("1.0-TEST"))
                .andExpect(jsonPath("$.soporte").value("soporte-test@unas.edu.pe"));
    }
}
