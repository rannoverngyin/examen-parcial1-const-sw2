package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.ProyectoService;

public class ProyectoServiceTest {
    @Test
    void debeContenerSgiUnas(){
        ProyectoService service = new ProyectoService();
        assertTrue(service.listar().contains("SGI-UNAS"));
    }
}
