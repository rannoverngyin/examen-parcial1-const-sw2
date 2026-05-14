package unas.pe.demo;

import org.junit.jupiter.api.Test;
import unas.pe.demo.application.NotaService;
import static org.junit.jupiter.api.Assertions.*;

// Paso A: Fase RED (Escribir la prueba que falla) no tener el archivo NotaService.java

class NotaServiceTest {
    private final NotaService service = new NotaService();

    @Test
    void calculaPromedioSimple() {
        assertEquals(15.0, service.promedio(14.0, 16.0), 0.001);
    }

    @Test
    void determinaAprobadoCuandoPromedioEsMayorOIgualA105() {
        assertTrue(service.estaAprobado(10.5));
    }

    @Test
    void rechazaNotasFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> service.promedio(21.0, 15.0));
    }



    // Paso E
    // agregamos lo quie pide la guia "calculaPromedioPonderado" 
    // e implementamos la logica en NotaService.java
    @Test
    void calculaPromedioPonderado() {
        assertEquals(16.2, service.promedioPonderado(15.0, 17.0), 0.001);
    }
}