package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.NotaService;

import static org.junit.jupiter.api.Assertions.*;

class NotaServiceTest {

    private final NotaService service = new NotaService();

    // ── Ciclo TDD principal ──────────────────────────────────────────────────

    @Test
    void calculaPromedioSimple() {
        assertEquals(15.0, service.promedio(14.0, 16.0), 0.001);
    }

    @Test
    void determinaAprobadoCuandoPromedioEsMayorOIgualA105() {
        assertTrue(service.estaAprobado(10.5));
    }

    @Test
    void determinaDesaprobadoCuandoPromedioEsMenorA105() {
        assertFalse(service.estaAprobado(10.4));
    }

    @Test
    void rechazaNotasFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> service.promedio(21.0, 15.0));
    }



    // Ejercicio nuevo ciclo TDD

    @Test
    void calculaPromedioPonderado() {
        // practica=15 (40%) + examen=17 (60%) = 6.0 + 10.2 = 16.2
        assertEquals(16.2, service.promedioPonderado(15.0, 17.0), 0.001);
    }

    @Test
    void rechazaNotaFueraDeRangoEnPromedioPonderado() {
        assertThrows(IllegalArgumentException.class, () -> service.promedioPonderado(-1.0, 17.0));
    }

    @Test
    void notaLimiteInferiorEsValida() {
        assertEquals(0.0, service.promedio(0.0, 0.0), 0.001);
    }

    @Test
    void notaLimiteSuperiorEsValida() {
        assertEquals(20.0, service.promedio(20.0, 20.0), 0.001);
    }
}
