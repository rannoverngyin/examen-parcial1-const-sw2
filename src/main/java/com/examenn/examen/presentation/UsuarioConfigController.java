package com.examenn.examen.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examenn.examen.application.UsuarioConfigService;

@RestController
public class UsuarioConfigController {
    private final UsuarioConfigService service;
    public UsuarioConfigController(UsuarioConfigService service) {
        this.service = service;
    }

    @GetMapping("/config/max-usuarios")
    public int maxUsuarios() {
        return service.obtenerMaxUsuarios();
    }
}
