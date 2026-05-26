package pe.unas.demoapi.application;
import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.LaboratorioService;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LaboratorioServiceTest {
    @Test 
    void debeListarDosLaboratorios() {
        LaboratorioService service = new LaboratorioService();
        assertEquals(2, service.Listar().size());
    }
}

