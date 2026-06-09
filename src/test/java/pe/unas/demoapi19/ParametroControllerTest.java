package pe.unas.demoapi19;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ParametroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void debeMostrarInstitucionConfigurada() throws Exception {
        mockMvc.perform(get("/parametros/institucion"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Universidad")));
    }

    @Test
    void debeMostrarVersionSistemaConfigurada() throws Exception {
        mockMvc.perform(get("/parametros/version"))
            .andExpect(status().isOk())
            .andExpect(content().string("1.0.0"));
    }

}
