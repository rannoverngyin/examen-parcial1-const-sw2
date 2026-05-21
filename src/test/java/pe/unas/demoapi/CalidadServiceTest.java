package pe.unas.demoapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.CalidadService;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias - CalidadService")
class CalidadServiceTest {

    private final CalidadService service = new CalidadService();


    @Test
    @DisplayName("Clasifica cobertura ALTA (85%)")
    void clasificaCoberturaAlta() {
        assertEquals("ALTA", service.clasificarCobertura(85));
    }

    @Test
    @DisplayName("Clasifica cobertura ALTA en el límite exacto (80%)")
    void clasificaCoberturaAltaLimite() {
        assertEquals("ALTA", service.clasificarCobertura(80));
    }

    @Test
    @DisplayName("Clasifica cobertura ALTA al 100%")
    void clasificaCoberturaCienPorciento() {
        assertEquals("ALTA", service.clasificarCobertura(100));
    }

    @Test
    @DisplayName("Clasifica cobertura MEDIA (60%)")
    void clasificaCoberturaMedia() {
        assertEquals("MEDIA", service.clasificarCobertura(60));
    }

    @Test
    @DisplayName("Clasifica cobertura MEDIA en el límite exacto (50%)")
    void clasificaCoberturaMediaLimite() {
        assertEquals("MEDIA", service.clasificarCobertura(50));
    }

    @Test
    @DisplayName("Clasifica cobertura BAJA (30%)")
    void clasificaCoberturaBaja() {
        assertEquals("BAJA", service.clasificarCobertura(30));
    }

    @Test
    @DisplayName("Clasifica cobertura BAJA al 0%")
    void clasificaCoberturaCero() {
        assertEquals("BAJA", service.clasificarCobertura(0));
    }

    @Test
    @DisplayName("Rechaza cobertura negativa (-1)")
    void rechazaCoberturaNegativa() {
        assertThrows(IllegalArgumentException.class,
                () -> service.clasificarCobertura(-1));
    }

    @Test
    @DisplayName("Rechaza cobertura mayor a 100 (101)")
    void rechazaCoberturaMayorACien() {
        assertThrows(IllegalArgumentException.class,
                () -> service.clasificarCobertura(101));
    }

    @Test
    @DisplayName("esAceptable devuelve true en el límite exacto (70%)")
    void esAceptableEnLimite() {
        assertTrue(service.esAceptable(70));
    }

    @Test
    @DisplayName("esAceptable devuelve true para cobertura alta (90%)")
    void esAceptableAlta() {
        assertTrue(service.esAceptable(90));
    }

    @Test
    @DisplayName("esAceptable devuelve false para cobertura baja (40%)")
    void esAceptableBaja() {
        assertFalse(service.esAceptable(40));
    }

    @Test
    @DisplayName("esAceptable rechaza valor negativo")
    void esAceptableRechazaNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> service.esAceptable(-5));
    }

    @Test
    @DisplayName("esAceptable rechaza valor mayor a 100")
    void esAceptableRechazaMayorACien() {
        assertThrows(IllegalArgumentException.class,
                () -> service.esAceptable(110));
    }
}
