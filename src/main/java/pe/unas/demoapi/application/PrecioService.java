package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrecioService {

    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    // Método principal requerido por el negocio
    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, this.varianteCliente);
    }

    // Método refactorizado para facilitar pruebas unitarias sin levantar el contexto de Spring
    public double calcularPorVariante(double precio, String variante) {
        if (variante == null) {
            variante = "BASICO";
        }
        return switch (variante.toUpperCase()) {
            case "PREMIUM" -> precio * 0.90; // 10% dcto
            case "VIP" -> precio * 0.80;     // 20% dcto
            case "ESTUDIANTE" -> precio * 0.70; // 30% dcto (Ejercicio Aplicado)
            default -> precio;               // Sin descuento
        };
    }

    public String obtenerVarianteActiva() {
        return this.varianteCliente;
    }
}