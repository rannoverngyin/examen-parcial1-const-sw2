package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.CalidadService;

class CalidadServiceTest {

    private final CalidadService service = new CalidadService();

    @Test
    void clasificaCoberturaAlta() {
        assertEquals("ALTA", service.clasificarCobertura(85));
    }

    @Test
    void clasificaCoberturaMedia() {
        assertEquals("MEDIA", service.clasificarCobertura(60));
    }

    @Test
    void clasificaCoberturaBaja() {
        assertEquals("BAJA", service.clasificarCobertura(30));
    }

    @Test
    void rechazaCoberturaNegativa() {
        assertThrows(IllegalArgumentException.class,
                () -> service.clasificarCobertura(-1));
    }

    @Test
    void rechazaCoberturaMayorACien() {
        assertThrows(IllegalArgumentException.class,
                () -> service.clasificarCobertura(101));
    }

    @Test
    void esAceptableConSetentaDebeSerTrue() {
        assertEquals(true, service.esAceptable(70));
    }

    @Test
    void esAceptableConNoventaDebeSerTrue() {
        assertEquals(true, service.esAceptable(90));
    }

    @Test
    void esAceptableConCuarentaDebeSerFalse() {
        assertEquals(false, service.esAceptable(40));
    }

}
