package unas.pe.demo;

import org.junit.jupiter.api.Test;
import unas.pe.demo.application.PlanServices;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlanServiceTest {

    private final PlanServices service = new PlanServices();

    @Test
    void debeRetornarValorPremium() {
        assertEquals(25, service.calcularPorVariante("PREMIUM"));
    }

    @Test
    void debeRetornarValorVip() {
        assertEquals(15, service.calcularPorVariante("VIP"));
    }

    @Test
    void debeRetornarValorBasico() {
        assertEquals(5, service.calcularPorVariante("BASICO"));
    }

}