package pe.unas.demoapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import pe.unas.demoapi.application.CalidadService;
import pe.unas.demoapi.controller.CalidadController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalidadController.class)
@Import(CalidadService.class)
@DisplayName("Pruebas de integración - CalidadController")
class CalidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/calidad/clasificar/85 retorna ALTA")
    void clasificarAlta() throws Exception {
        mockMvc.perform(get("/api/calidad/clasificar/85"))
                .andExpect(status().isOk())
                .andExpect(content().string("ALTA"));
    }

    @Test
    @DisplayName("GET /api/calidad/clasificar/60 retorna MEDIA")
    void clasificarMedia() throws Exception {
        mockMvc.perform(get("/api/calidad/clasificar/60"))
                .andExpect(status().isOk())
                .andExpect(content().string("MEDIA"));
    }

    @Test
    @DisplayName("GET /api/calidad/clasificar/30 retorna BAJA")
    void clasificarBaja() throws Exception {
        mockMvc.perform(get("/api/calidad/clasificar/30"))
                .andExpect(status().isOk())
                .andExpect(content().string("BAJA"));
    }

    @Test
    @DisplayName("GET /api/calidad/aceptable/90 retorna true")
    void esAceptableTrue() throws Exception {
        mockMvc.perform(get("/api/calidad/aceptable/90"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("GET /api/calidad/aceptable/40 retorna false")
    void esAceptableFalse() throws Exception {
        mockMvc.perform(get("/api/calidad/aceptable/40"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
