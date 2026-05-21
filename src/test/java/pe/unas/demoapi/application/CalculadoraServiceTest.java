package pe.unas.demoapi.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraServiceTest {

    private final CalculadoraService service = new CalculadoraService();

    @Test
    void sumarDosNumeros() {
        assertEquals(8, service.sumar(5, 3));
    }

    @Test
    void restarDosNumeros() {
        assertEquals(2, service.restar(5, 3));
    }

    @Test
    void multiplicarDosNumeros() {
        assertEquals(15, service.multiplicar(5, 3));
    }

    @Test
    void dividirDosNumeros() {
        assertEquals(2, service.dividir(6, 3));
    }

    @Test
    void dividirEntreCeroLanzaError() {
        assertThrows(ArithmeticException.class,
                () -> service.dividir(5, 0));
    }
}