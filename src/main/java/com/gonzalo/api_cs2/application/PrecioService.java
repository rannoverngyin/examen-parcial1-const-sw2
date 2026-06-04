package com.gonzalo.api_cs2.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrecioService {

    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, varianteCliente);
    }

    public double calcularPorVariante(double precio, String variante) {
        return switch (variante.toUpperCase()) {
            case "PREMIUM" -> precio * 0.90;
            case "VIP" -> precio * 0.80;
            default -> precio;
        };
    }

    public String obtenerVarianteActiva() {
        return varianteCliente;
    }
}