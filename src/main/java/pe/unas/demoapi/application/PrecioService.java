package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.unas.demoapi.domain.VarianteCliente;

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
        VarianteCliente varianteEnum = VarianteCliente.parse(variante);
        return varianteEnum.calcularPrecio(precio);
    }
}