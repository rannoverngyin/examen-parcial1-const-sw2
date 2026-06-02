package sesion17.variabilidad.presentation;

import sesion17.variabilidad.application.precioService;
import org.springframework.web.bind.annotation.*;

@RestController
public class precioController {

    private final precioService service;

    public precioController(precioService service) {
        this.service = service;
    }

    @GetMapping("/precio-final")
    public double calcular(@RequestParam double precio) {
        return service.calcularPrecioFinal(precio);
    }

    @GetMapping("/variante-activa")
    public String variante() {
        return service.obtenerVarianteActiva();
    }
}

