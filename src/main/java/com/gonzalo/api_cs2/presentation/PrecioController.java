package com.gonzalo.api_cs2.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gonzalo.api_cs2.application.PrecioService;

@RestController
public class PrecioController {

    private final PrecioService service;

    public PrecioController(PrecioService service) {
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
