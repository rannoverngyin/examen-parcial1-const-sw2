package com.carga.metricas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CargaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void endpointsDeCargaDebenResponderOk() throws Exception {
        mockMvc.perform(get("/carga/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));

        mockMvc.perform(get("/carga/productos"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/carga/metricas"))
                .andExpect(status().isOk());
    }
}
