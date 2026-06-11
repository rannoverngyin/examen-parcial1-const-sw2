package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ParametroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeMostrarInstitucionConfigurada() throws Exception {
        mockMvc.perform(get("/parametros/institucion"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Universidad")));
    }

    @Test
    void debeMostrarModoConfigurado() throws Exception {
        mockMvc.perform(get("/parametros/modo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ACADEMICO")));
    }

    @Test
    void debeMostrarLimiteUsuariosConfigurado() throws Exception {
        mockMvc.perform(get("/parametros/limite-usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("100")));
    }

    @Test
    void debeMostrarVersionSistema() throws Exception {
        mockMvc.perform(get("/parametros/version"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1.0.0")));
    }
}
