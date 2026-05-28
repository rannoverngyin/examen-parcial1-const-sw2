package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.InvestigadorService;

public class InvestigadorTest {

    InvestigadorService service = new InvestigadorService();

    @Test
    void debeMostrarDosInvestigadore() {
        assertEquals(2, service.totalInvestigadores());

    }

}
