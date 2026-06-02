package pe.unas.demoapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.PrecioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrecioServiceTest {

    @Test
    @DisplayName("Debería calcular el precio sin descuento para cliente BASICO")
    public void testCalcularPrecioBasico() {
        PrecioService service = new PrecioService("BASICO");
        double precioBase = 100.0;
        double precioEsperado = 100.0;
        
        double precioCalculado = service.calcularPrecioFinal(precioBase);
        
        assertEquals(precioEsperado, precioCalculado, 0.001, "El cliente BASICO no debería tener descuento");
    }

    @Test
    @DisplayName("Debería calcular el precio con 10% de descuento para cliente PREMIUM")
    public void testCalcularPrecioPremium() {
        PrecioService service = new PrecioService("PREMIUM");
        double precioBase = 100.0;
        double precioEsperado = 90.0;
        
        double precioCalculado = service.calcularPrecioFinal(precioBase);
        
        assertEquals(precioEsperado, precioCalculado, 0.001, "El cliente PREMIUM debería tener un 10% de descuento");
    }

    @Test
    @DisplayName("Debería calcular el precio con 20% de descuento para cliente VIP")
    public void testCalcularPrecioVip() {
        PrecioService service = new PrecioService("VIP");
        double precioBase = 100.0;
        double precioEsperado = 80.0;
        
        double precioCalculado = service.calcularPrecioFinal(precioBase);
        
        assertEquals(precioEsperado, precioCalculado, 0.001, "El cliente VIP debería tener un 20% de descuento");
    }

    @Test
    @DisplayName("Debería calcular el precio con 30% de descuento para cliente ESTUDIANTE")
    public void testCalcularPrecioEstudiante() {
        PrecioService service = new PrecioService("ESTUDIANTE");
        double precioBase = 100.0;
        double precioEsperado = 70.0;
        
        double precioCalculado = service.calcularPrecioFinal(precioBase);
        
        assertEquals(precioEsperado, precioCalculado, 0.001, "El cliente ESTUDIANTE debería tener un 30% de descuento");
    }

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si el precio es negativo")
    public void testCalcularPrecioNegativo() {
        PrecioService service = new PrecioService("BASICO");
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.calcularPrecioFinal(-50.0);
        }, "No se deben permitir precios negativos");
    }

    @Test
    @DisplayName("Debería retornar el precio base (sin descuento) si la variante no es reconocida")
    public void testCalcularPrecioVarianteDesconocida() {
        PrecioService service = new PrecioService("DESCONOCIDO");
        double precioBase = 200.0;
        double precioEsperado = 200.0;
        
        double precioCalculado = service.calcularPrecioFinal(precioBase);
        
        assertEquals(precioEsperado, precioCalculado, 0.001, "Las variantes desconocidas deben aplicar por defecto la tarifa BASICA");
    }

    @Test
    @DisplayName("Debería calcular con el constructor por defecto sin parámetros")
    public void testConstructorPorDefecto() {
        PrecioService service = new PrecioService();
        double precioBase = 150.0;
        double precioEsperado = 150.0;
        
        double precioCalculado = service.calcularPrecioFinal(precioBase);
        
        assertEquals(precioEsperado, precioCalculado, 0.001, "El constructor por defecto debe inicializar con comportamiento BASICO");
    }
}
