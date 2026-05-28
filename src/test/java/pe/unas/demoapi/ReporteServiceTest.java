package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pe.unas.demoapi.application.ReporteService;

public class ReporteServiceTest {

    @Test
    void debeGenerarReporte(){
        ReporteService service = new ReporteService();
        assertEquals("Reporte generado", service.generar());
    }
}
