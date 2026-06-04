package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrecioService {

    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    /**
     * Calcula el precio final según la variante configurada en application.properties.
     */
    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, varianteCliente);
    }

    /**
     * Método auxiliar para calcular precio por variante explícita.
     * Facilita las pruebas unitarias sin depender de @Value.
     *
     * Variantes disponibles:
     *   BASICO     → sin descuento         (precio * 1.00)
     *   PREMIUM    → 10% de descuento      (precio * 0.90)
     *   VIP        → 20% de descuento      (precio * 0.80)
     *   ESTUDIANTE → 30% de descuento      (precio * 0.70)
     */
    public double calcularPorVariante(double precio, String variante) {
        return switch (variante.toUpperCase()) {
            case "PREMIUM"    -> precio * 0.90;
            case "VIP"        -> precio * 0.80;
            case "ESTUDIANTE" -> precio * 0.70;
            default           -> precio;          // BASICO o cualquier valor desconocido
        };
    }

    public String obtenerVarianteActiva() {
        return varianteCliente;
    }
}
