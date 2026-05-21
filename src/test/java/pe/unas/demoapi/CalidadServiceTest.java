package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.CalidadService;
import static org.junit.jupiter.api.Assertions.*;

class CalidadServiceTest {

    private final CalidadService service = new CalidadService();

    // ==========================================
    // PRUEBAS DE: clasificarCobertura (Puntos 8 y 12)
    // ==========================================

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

    // ==========================================
    // NUEVAS PRUEBAS DE: esAceptable (Punto 16)
    // ==========================================

    @Test
    void esAceptableConSetenta() {
        assertTrue(service.esAceptable(70));
    }

    @Test
    void esAceptableConNoventa() {
        assertTrue(service.esAceptable(90));
    }

    @Test
    void esAceptableConCuarenta() {
        assertFalse(service.esAceptable(40));
}
}