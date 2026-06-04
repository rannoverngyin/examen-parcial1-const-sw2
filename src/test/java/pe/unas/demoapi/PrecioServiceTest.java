package pe.unas.demoapi;

import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.PrecioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrecioServiceTest {

    private final PrecioService service = new PrecioService();

    // -----------------------------------------------------------
    // Variante BASICO – sin descuento
    // -----------------------------------------------------------
    @Test
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }

    // -----------------------------------------------------------
    // Variante PREMIUM – 10% de descuento
    // -----------------------------------------------------------
    @Test
    void debeAplicarDescuentoPremium() {
        assertEquals(90.0, service.calcularPorVariante(100, "PREMIUM"));
    }

    // -----------------------------------------------------------
    // Variante VIP – 20% de descuento
    // -----------------------------------------------------------
    @Test
    void debeAplicarDescuentoVip() {
        assertEquals(80.0, service.calcularPorVariante(100, "VIP"));
    }

    // -----------------------------------------------------------
    // Variante ESTUDIANTE – 30% de descuento  (Ejercicio aplicado)
    // -----------------------------------------------------------
    @Test
    void debeAplicarDescuentoEstudiante() {
        assertEquals(70.0, service.calcularPorVariante(100, "ESTUDIANTE"));
    }

    // -----------------------------------------------------------
    // Variante desconocida cae en BASICO (sin descuento)
    // -----------------------------------------------------------
    @Test
    void debeUsarBasicoCuandoVarianteEsDesconocida() {
        assertEquals(200.0, service.calcularPorVariante(200, "INVITADO"));
    }
}
