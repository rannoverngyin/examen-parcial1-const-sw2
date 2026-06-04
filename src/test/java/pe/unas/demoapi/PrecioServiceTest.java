package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.PrecioService;

class PrecioServiceTest {

    private final PrecioService service = new PrecioService();

    @Test
    void debeAplicarDescuentoPremium() {
        assertEquals(
                90.0,
                service.calcularPorVariante(100, "PREMIUM"),
                0.001
        );
    }

    @Test
    void debeAplicarDescuentoVip() {
        assertEquals(
                80.0,
                service.calcularPorVariante(100, "VIP"),
                0.001
        );
    }

    @Test
    void debeMantenerPrecioBasico() {
        assertEquals(
                100.0,
                service.calcularPorVariante(100, "BASICO"),
                0.001
        );
    }

    @Test
    void debeAplicarDescuentoEstudiante() {
        assertEquals(
                70.0,
                service.calcularPorVariante(100, "ESTUDIANTE"),
                0.001
        );
    }

    @Test
    void debeUsarPrecioBasicoCuandoLaVarianteNoExiste() {
        assertEquals(
                100.0,
                service.calcularPorVariante(100, "DESCONOCIDO"),
                0.001
        );
    }
}