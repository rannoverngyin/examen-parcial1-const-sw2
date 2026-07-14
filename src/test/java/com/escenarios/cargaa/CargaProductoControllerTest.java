package com.escenarios.cargaa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CargaProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void endpointsDeCargaProductosDebenFuncionarCorrectamente() throws Exception {
        mockMvc.perform(get("/carga/productos"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/carga/productos/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").isNumber());

        mockMvc.perform(post("/carga/productos").param("nombre", "Monitor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creado").value("Monitor"));

        mockMvc.perform(get("/carga/productos/reporte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resumen").exists());
    }
}
