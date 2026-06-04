package pe.unas.demoapi;

import org.junit.jupiter.api.Test;  // JUnit 5
import pe.unas.demoapi.application.PrecioService;
import static org.junit.jupiter.api.Assertions.assertEquals;  // Para las comparaciones

class PrecioServiceTest {

    // Creamos el servicio MANUALMENTE (no usa Spring, más rápido para pruebas)
    private final PrecioService service = new PrecioService();

    @Test
    void debeAplicarDescuentoPremium() {
        // Escenario: precio 100, variante PREMIUM → esperamos 90
        double resultado = service.calcularPorVariante(100, "PREMIUM");
        assertEquals(90.0, resultado, "PREMIUM debería dar 10% descuento");
    }

    @Test
    void debeAplicarDescuentoVip() {
        double resultado = service.calcularPorVariante(100, "VIP");
        assertEquals(80.0, resultado, "VIP debería dar 20% descuento");
    }

    @Test
    void debeMantenerPrecioBasico() {
        double resultado = service.calcularPorVariante(100, "BASICO");
        assertEquals(100.0, resultado, "BASICO no aplica descuento");
    }
    @Test
    void debeAplicarDescuentoEstudiante() {
        double resultado = service.calcularPorVariante(100, "ESTUDIANTE");
        assertEquals(70.0, resultado, "ESTUDIANTE debería dar 30% descuento");
    }
}