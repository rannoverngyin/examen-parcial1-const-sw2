package pe.unas.demoapi17;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi17.application.PrecioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrecioServiceTest {
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
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }

    @Test
    void debeAplicarDescuentoEstudiante() {
        assertEquals(70.0, service.calcularPorVariante(100, "ESTUDIANTE"));
    }
}
