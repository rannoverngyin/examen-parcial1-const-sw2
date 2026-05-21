package pe.unas.demoapi.controller;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.CalidadService;

@RestController
@RequestMapping("/api/calidad")
public class CalidadController {

    private final CalidadService calidadService;

    public CalidadController(CalidadService calidadService) {
        this.calidadService = calidadService;
    }

    @GetMapping("/clasificar/{porcentaje}")
    public String clasificar(@PathVariable int porcentaje) {
        return calidadService.clasificarCobertura(porcentaje);
    }

    @GetMapping("/aceptable/{porcentaje}")
    public boolean esAceptable(@PathVariable int porcentaje) {
        return calidadService.esAceptable(porcentaje);
    }
}
