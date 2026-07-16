package com.enchmarking.analisiss.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BenchmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testBaselineEndpoint() throws Exception {
        mockMvc.perform(get("/benchmark/baseline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("REG-99")))
                .andExpect(jsonPath("$", hasItem("REG-4999")));
    }

    @Test
    void testOptimizadoEndpoint() throws Exception {
        mockMvc.perform(get("/benchmark/optimizado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("REG-99")))
                .andExpect(jsonPath("$", hasItem("REG-499")));
    }
}
