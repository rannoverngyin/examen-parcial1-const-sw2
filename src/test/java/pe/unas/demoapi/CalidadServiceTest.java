package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.CalidadService;

import static org.junit.jupiter.api.Assertions.*;

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
    void esAceptable_debeRetornarTrueCuandoEsMayorOIgualAlUmbral() {
        assertTrue(service.esAceptable(70));
        assertTrue(service.esAceptable(90));
    }

    @Test
    void esAceptable_debeRetornarFalseCuandoEsMenorAlUmbral() {
        assertFalse(service.esAceptable(40));
    }

    @Test
    void esAceptable_debeRechazarValoresFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> service.esAceptable(-5));
        assertThrows(IllegalArgumentException.class, () -> service.esAceptable(105));
    }
}
