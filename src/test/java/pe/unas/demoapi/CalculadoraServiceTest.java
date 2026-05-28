package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.CalculadoraService;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraServiceTest {

    private final CalculadoraService service = new CalculadoraService();

    @Test
    void restar() {
        assertEquals(3, service.restar(5, 2));
    }
    @Test
    void dividir() {
        assertEquals(2, service.dividir(4, 2));
    }

    @Test
    void multiplicar() {
        assertEquals(10, service.multiplicar(5, 2));
    }
}
