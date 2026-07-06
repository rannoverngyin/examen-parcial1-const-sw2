package pe.unas.demoapi.application;

public enum Variante {
    BASICO(1.0),
    PREMIUM(0.90),
    VIP(0.80),
    ESTUDIANTE(0.70);

    private final double factor;

    Variante(double factor) {
        this.factor = factor;
    }

    public double aplicar(double precio) {
        return precio * factor;
    }

    public static Variante from(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return BASICO;
        }

        try {
            return Variante.valueOf(nombre.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return BASICO;
        }
    }
}
