package com.gonzalo.api_cs2;

import org.junit.jupiter.api.Test;
import com.gonzalo.api_cs2.application.PrecioService;

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
}
