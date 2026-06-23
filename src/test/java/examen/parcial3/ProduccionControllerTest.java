package examen.parcial3;

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

public class ProduccionControllerTest {

     @Autowired
    private MockMvc mockMvc;

    @Test
    void debeMostrarMensajeProduccion() throws Exception {
        mockMvc.perform(get("/mensaje"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("produccion")));
    }

    
}
