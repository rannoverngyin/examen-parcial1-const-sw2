package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.PrecioService;

@RestController
public class PrecioController {

    private final PrecioService precioService;

    public PrecioController(PrecioService precioService) {
        this.precioService = precioService;
    }

    @GetMapping("/variante-activa")
    public String obtenerVarianteActiva() {
        return precioService.obtenerVarianteActiva();
    }

    @GetMapping("/precio-final")
    public double calcularPrecioFinal(@RequestParam(name = "precio") double precio) {
        return precioService.calcularPrecioFinal(precio);
    }
}

