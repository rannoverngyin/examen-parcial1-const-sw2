package pe.unas.demoapi;

import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.PrecioService;
import pe.unas.demoapi.application.Variante;

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
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }

    @Test
    void debeAplicarDescuentoEstudiante() {
        assertEquals(70.0, service.calcularPorVariante(100, "ESTUDIANTE"));
    }

    @Test
    void debeRetornarVarianteBASICOParaNombreInvalido() {
        assertEquals(100.0, service.calcularPorVariante(100, "INVALIDO"));
    }

    @Test
    void debeAplicarDescuentoVipUsandoEnum() {
        assertEquals(80.0, service.calcularPorVariante(100, Variante.VIP));
    }

}
