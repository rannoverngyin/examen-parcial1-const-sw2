package com.gonza.unas;

import org.junit.jupiter.api.Test;
import com.gonza.unas.application.CalidadService;

import static org.junit.jupiter.api.Assertions.*;

class CalidadServiceTest {

    private final CalidadService service = new CalidadService();

    // clasificarCobertura
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

    // Ejercicio Aplicado (esAceptable)
    @Test
    void esAceptableEnLimiteInferior() {
        assertTrue(service.esAceptable(70)); // Caso límite 70
    }

    @Test
    void esAceptableCasoExito() {
        assertTrue(service.esAceptable(90)); // Caso sobre el límite
    }

    
    @Test
    void noEsAceptableCasoInsuficiente() {
        assertFalse(service.esAceptable(40)); // Caso bajo el límite
    }
    
    @Test
    void esAceptableRechazaInvalido() {
        assertThrows(IllegalArgumentException.class, 
            () -> service.esAceptable(-5));
    }
}
