package sesion14.cobertura;

import sesion14.cobertura.application.CalidadService;
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
    void esAceptableCon70() {
        assertTrue(service.esAceptable(70));
    }

    @Test
    void esAceptableCon90() {
        assertTrue(service.esAceptable(90));
    }

    @Test
    void noEsAceptableCon40() {
        assertFalse(service.esAceptable(40));
    }

}
