package sesion17.variabilidad;

import sesion17.variabilidad.application.precioService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class precioServiceTest {

    private final precioService service = new precioService();

    @Test
    void debeAplicarDescuentoPremium() {
        assertEquals(90.0, service.calcularPorVariante(100, "PREMIUM"));
    }

    @Test
    void debeAplicarDescuentoVip() {
        assertEquals(80.0, service.calcularPorVariante(100, "VIP"));
    }

    @Test
    void debeMantenerPrecioBasico() {
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }

    @Test
    void debeAplicarDescuentoEstudiante() {
        assertEquals(70.0, service.calcularPorVariante(100, "ESTUDIANTE"));
    }

    
}
