package com.unas.examen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarProductos_retorna200() throws Exception {
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerProducto_existente_retorna200() throws Exception {
        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerProducto_noExistente_retorna404() throws Exception {
        mockMvc.perform(get("/productos/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearProducto_retorna201() throws Exception {
        String json = """
                {
                    "nombre": "Producto Test",
                    "descripcion": "Descripcion test",
                    "precio": 10.00,
                    "stock": 5
                }
                """;
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void configInfo_retorna200() throws Exception {
        mockMvc.perform(get("/config/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aplicacion").value("examen-parcial1-const-sw2"));
    }
}
