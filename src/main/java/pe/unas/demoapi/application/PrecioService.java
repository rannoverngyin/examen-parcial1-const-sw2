package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrecioService {

    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, Variante.from(varianteCliente));
    }

    public String obtenerVarianteActiva() {
        return Variante.from(varianteCliente).name();
    }

    public double calcularPorVariante(double precio, String variante) {
        return calcularPorVariante(precio, Variante.from(variante));
    }

    public double calcularPorVariante(double precio, Variante variante) {
        return variante.aplicar(precio);
    }

}
