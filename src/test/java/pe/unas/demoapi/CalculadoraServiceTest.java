package pe.unas.demoapi;

import pe.unas.demoapi.application.CalculadoraService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraServiceTest {

    CalculadoraService service =
            new CalculadoraService();

    @Test
    void sumar(){
        assertEquals(
                4,
                service.sumar(2,2)
        );
    }

    @Test
    void restar(){
        assertEquals(
                2,
                service.restar(5,3)
        );
    }

    @Test
    void dividir(){
        assertEquals(
                2,
                service.dividir(4,2)
        );
    }

    @Test
    void dividirPorCero(){
        assertThrows(
                ArithmeticException.class,
                () -> service.dividir(4,0)
        );
    }

    @Test
    void multiplicar(){
        assertEquals(
                6,
                service.multiplicar(2,3)
        );
    }
}