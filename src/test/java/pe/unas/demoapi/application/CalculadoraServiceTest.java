package pe.unas.demoapi.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraServiceTest {

    private final CalculadoraService service = new CalculadoraService();

    @BeforeEach
    void setUp() {
        service.sumaar(1, 2);
    }

    @Test
    @DisplayName("Debe sumar 1 y 2")
    void sumaar() {
        assertEquals(3, service.sumaar(1, 2));
    }
    @Test
    @DisplayName("Debe restar 5 y 2")
    void restar() {
        assertEquals(3, service.restar(5, 2));
    }
    
}
