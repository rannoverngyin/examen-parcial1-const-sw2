package com.pruebas_de.rendimiento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RendimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ambosEndpointsDebenResponderOk() throws Exception {
        mockMvc.perform(get("/rendimiento/productos/base"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rendimiento/productos/optimizado"))
                .andExpect(status().isOk());
    }

    @Test
    void endpointsConCantidadDebenResponderOk() throws Exception {
        mockMvc.perform(get("/rendimiento/productos/base?cantidad=1000"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rendimiento/productos/optimizado?cantidad=1000"))
                .andExpect(status().isOk());
    }
}
