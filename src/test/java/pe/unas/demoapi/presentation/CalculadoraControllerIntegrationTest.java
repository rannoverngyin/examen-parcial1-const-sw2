package pe.unas.demoapi.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalculadoraControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sumar_debeRetornarResultado() throws Exception {
        mockMvc.perform(get("/calculadora/sumar")
                .param("a", "5")
                .param("b", "3")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("8"));
    }

    @Test
    void restar_debeRetornarResultado() throws Exception {
        mockMvc.perform(get("/calculadora/restar")
                .param("a", "5")
                .param("b", "2")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    void multiplicar_debeRetornarResultado() throws Exception {
        mockMvc.perform(get("/calculadora/multiplicar")
                .param("a", "4")
                .param("b", "3")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("12"));
    }

    @Test
    void dividir_debeRetornarResultado() throws Exception {
        mockMvc.perform(get("/calculadora/dividir")
                .param("a", "10")
                .param("b", "2")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }
}
