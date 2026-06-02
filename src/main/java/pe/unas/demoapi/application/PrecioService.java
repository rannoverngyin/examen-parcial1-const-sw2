package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrecioService {

    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    public PrecioService() {
        this.varianteCliente = "BASICO";
    }

    public PrecioService(String varianteCliente) {
        this.varianteCliente = varianteCliente;
    }

    public String obtenerVarianteActiva() {
        return this.varianteCliente != null ? this.varianteCliente.trim().toUpperCase() : "BASICO";
    }

    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, obtenerVarianteActiva());
    }

    public double calcularPorVariante(double precio, String variante) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        String varianteNormalizada = (variante == null) ? "BASICO" : variante.trim().toUpperCase();

        return switch (varianteNormalizada) {
            case "PREMIUM" -> precio * 0.90;
            case "VIP" -> precio * 0.80;
            case "ESTUDIANTE" -> precio * 0.70;
            case "BASICO" -> precio;
            default -> {
                System.out.println("Variante no reconocida '" + varianteNormalizada + "'. Aplicando descuento BASICO (0%).");
                yield precio;
            }
        };
    }
}