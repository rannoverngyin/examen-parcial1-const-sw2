package com.clase1.demo.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clase1.demo.application.SaludoService;

@RestController
public class SaludoController {
    private final SaludoService service = new SaludoService();
    @GetMapping("/saludo")
    public String saludo() {
        return service.obtenerSaludo();
    }

}


