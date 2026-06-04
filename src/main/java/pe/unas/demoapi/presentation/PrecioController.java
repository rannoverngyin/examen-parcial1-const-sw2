package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.PrecioService;

@RestController  // ← Esta clase manejará peticiones HTTP y devolverá JSON/texto
public class PrecioController {

    private final PrecioService service;  // Dependencia del servicio

    // Constructor: Spring inyectará automáticamente el PrecioService
    public PrecioController(PrecioService service) {
        this.service = service;
    }

    /**
     * Endpoint: GET /precio-final?precio=100
     * Calcula el precio final según la variante activa
     * 
     * @param precio El precio original (parámetro de la URL)
     * @return El precio final calculado
     */
    @GetMapping("/precio-final")
    public double calcular(@RequestParam double precio) {
        return service.calcularPrecioFinal(precio);
    }

    /**
     * Endpoint: GET /variante-activa
     * Devuelve qué variante está actualmente configurada
     */
    @GetMapping("/variante-activa")
    public String variante() {
        return service.obtenerVarianteActiva();
    }
}