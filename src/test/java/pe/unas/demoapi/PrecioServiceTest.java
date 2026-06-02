package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.PrecioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrecioServiceTest {

    private final PrecioService service = new PrecioService();

    @Test
    void debeAplicarDescuentoPremium() {
        assertEquals(90.0, service.calcularPorVariante(100, "PREMIUM"));
    }

    @Test
    void debeAplicarDescuentoVip() {
        assertEquals(80.0, service.calcularPorVariante(100, "VIP"));
    }
    
    @Test
    void debeAplicarDescuentoEstudiante() {
        assertEquals(70.0, service.calcularPorVariante(100, "ESTUDIANTE"));
    }

    @Test
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }
}
