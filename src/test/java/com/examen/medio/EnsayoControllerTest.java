package com.examen.medio;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.examen.medio.application.EnsayoService;
import com.examen.medio.domain.Ensayo;
import com.examen.medio.presentation.EnsayoController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(EnsayoController.class)
public class EnsayoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnsayoService servicio;

    @Test
    public void testConsultarDevuelveJson() throws Exception {
        Ensayo mockEnsayo = new Ensayo("Mock Sistema", true, "OK");
        when(servicio.listarEnsayos()).thenReturn(List.of(mockEnsayo));

        mockMvc.perform(get("/api/ensayo/consulta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Mock Sistema"))
                .andExpect(jsonPath("$[0].activo").value(true))
                .andExpect(jsonPath("$[0].estado").value("OK"));

    }

}
