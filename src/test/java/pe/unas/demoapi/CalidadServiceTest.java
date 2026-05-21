package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.CalidadService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThrows(IllegalArgumentException.class, () -> service.clasificarCobertura(-1));
    }

    @Test
    void rechazaCoberturaMayorACien() {
        assertThrows(IllegalArgumentException.class, () -> service.clasificarCobertura(101));
    }

    @Test
    void coberturaSetentaEsAceptable() {
        assertTrue(service.esAceptable(70));
    }

    @Test
    void coberturaNoventaEsAceptable() {
        assertTrue(service.esAceptable(90));
    }

    @Test
    void coberturaCuarentaNoEsAceptable() {
        assertFalse(service.esAceptable(40));
    }
}
