package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.EventoService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;


public class EventoServiceTest {
    @Test
    void debeListarDosEventos(){
        EventoService service = new EventoService();
        assertEquals(2, service.listar().size());
    }


}
