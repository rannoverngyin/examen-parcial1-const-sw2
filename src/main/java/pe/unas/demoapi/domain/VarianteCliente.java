package pe.unas.demoapi.domain;

public enum VarianteCliente {
    BASICO(1.0),
    PREMIUM(0.90),
    VIP(0.80),
    ESTUDIANTE(0.70);

    private final double multiplicador;

    VarianteCliente(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return this.multiplicador;
    }

    public double calcularPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        return precio * this.multiplicador;
    }

    public static VarianteCliente parse(String variante) {
        if (variante == null) {
            return BASICO;
        }
        try {
            return VarianteCliente.valueOf(variante.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Variante no reconocida '" + variante.trim().toUpperCase() + "'. Aplicando descuento BASICO (0%).");
            return BASICO;
        }
    }
}
