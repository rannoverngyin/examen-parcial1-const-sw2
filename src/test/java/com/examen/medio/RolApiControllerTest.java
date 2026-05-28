package com.examen.medio;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.examen.medio.application.RolService;
import com.examen.medio.presentation.RolController;

@WebMvcTest(RolController.class)
public class RolApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Test
    void debeContenerRolAdmin() throws Exception {
        when(rolService.listar()).thenReturn(List.of("ADMIN"));

        mockMvc.perform(get("/roles"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("ADMIN")));
    }
    
}
