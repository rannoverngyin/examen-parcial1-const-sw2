package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service  // ← Esto le dice a Spring: "esta clase es un servicio, adminístrala tú"
public class PrecioService {

    // 📖 @Value inyecta el valor de application.properties
    // El valor "BASICO" es el DEFAULT si la propiedad no existe
    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    /**
     * Método principal que usa la configuración actual
     * @param precio Precio original del producto
     * @return Precio final después de aplicar descuento
     */
    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, varianteCliente);
    }

    /**
     * 🔧 Método auxiliar (puro, sin dependencias de Spring)
     * Esto es útil para probar la lógica sin levantar todo Spring
     * 
     * @param precio Precio original
     * @param variante La variante a aplicar (BASICO, PREMIUM, VIP, ESTUDIANTE)
     * @return Precio con descuento aplicado
     */
    public double calcularPorVariante(double precio, String variante) {
        // Switch expression (Java 14+)
        return switch (variante.toUpperCase()) {
            case "PREMIUM" -> precio * 0.90;      // 10% descuento
            case "VIP" -> precio * 0.80;          // 20% descuento
            case "ESTUDIANTE" -> precio * 0.70;   // 30% descuento (lo añadiremos después)
            default -> precio;                     // BASICO o cualquier otro valor
        };
    }

    /**
     * Método para saber qué variante está activa actualmente
     */
    public String obtenerVarianteActiva() {
        return varianteCliente;
    }
}