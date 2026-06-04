package pe.unas.demoapi.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PrecioServiceTest {

    private final PrecioService service = new PrecioService();

    @Test
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100.0, "BASICO"));
    }

    @Test
    void debeAplicarDescuentoPremium() {
        assertEquals(90.0, service.calcularPorVariante(100.0, "PREMIUM"));
    }

    @Test
    void debeAplicarDescuentoVip() {
        assertEquals(80.0, service.calcularPorVariante(100.0, "VIP"));
    }

    @Test
    void debeAplicarDescuentoEstudiante() {
        // Validación del Ejercicio Aplicado (30% de descuento)
        assertEquals(70.0, service.calcularPorVariante(100.0, "ESTUDIANTE"));
    }
}