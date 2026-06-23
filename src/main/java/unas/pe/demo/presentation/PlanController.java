package unas.pe.demo.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import unas.pe.demo.application.PlanServices;

@RestController
public class PlanController {

    private final PlanServices services;

    public PlanController(PlanServices services) {
        this.services = services;
    }

    // 
    @GetMapping("/plan/valor-final")
    public int obtenerValorFinal() {
        return services.calcularPrecioFinal();
    }

    // 
    @GetMapping("/plan/variante-activa")
    public String obtenerVariante() {
        return services.obtenerVarianteActiva();
    }
}