package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.PrecioService;

public class PrecioServiceTest {
    private final  PrecioService service = new PrecioService();

    @Test
    void debeAplicarDescuentoPremiun(){
        assertEquals(90.0, service.calcularPorVariante(100, "PREMIUM"));
    }
        @Test
    void debeAplicarDescuentoVip(){
        assertEquals(80.0, service.calcularPorVariante(100, "VIP"));
    }
        @Test
    void debeAplicarDescuentoBasico(){
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }
        @Test
    void debeAplicarDescuentoEstudiante(){
        assertEquals(70.0, service.calcularPorVariante(100, "ESTUDIANTE"));
    }
}
