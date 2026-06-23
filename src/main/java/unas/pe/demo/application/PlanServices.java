package unas.pe.demo.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlanServices {

    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    // Ya no recibe precio, y ahora devuelve un int
    public int calcularPrecioFinal() {
        return calcularPorVariante(varianteCliente);
    }

    // Limpiamos el parámetro precio que ya no se usa
    public int calcularPorVariante(String variante) {
        return switch (variante.toUpperCase()) {
            case "PREMIUM" -> 25;
            case "VIP" -> 15;
            case "BASICO" -> 5;
            default -> 0;
        };
    }

    public String obtenerVarianteActiva() {
        return varianteCliente;
    }
}