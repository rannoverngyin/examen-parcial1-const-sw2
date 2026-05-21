package pe.unas.demoapi14.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalidadServiceTest {

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
    void rechazaAceptableMayorACien() {
    assertThrows(IllegalArgumentException.class,
            () -> service.esAceptable(101));
    }
    @Test
    void aceptaCoberturaSetenta() {
        assertTrue(service.esAceptable(70));
    }

    @Test
    void aceptaCoberturaNoventa() {
        assertTrue(service.esAceptable(90));
    }

    @Test
    void rechazaCoberturaCuarenta() {
        assertFalse(service.esAceptable(40));
    }
    @Test
    void esAceptableRechazaInvalido() {
        assertThrows(IllegalArgumentException.class, 
            () -> service.esAceptable(-5));
    }
}
