package com.gonzalo.api_cs2.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gonzalo.api_cs2.application.ConfiguracionService;

import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfiguracionController {

    private final ConfiguracionService service;

    public ConfiguracionController(ConfiguracionService service) {
        this.service = service;
    }

    @GetMapping("/entorno")
    public String entorno() {
        return service.obtenerEntorno();
    }

    @GetMapping("/mensaje")
    public String mensaje() {
        return service.obtenerMensaje();
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "entorno", service.obtenerEntorno(),
                "mensaje", service.obtenerMensaje(),
                "version", service.obtenerVersion(),
                "soporte", service.obtenerSoporte()
        );
    }

    @GetMapping("/soporte")
    public String soporte() {
        return service.obtenerSoporte();
    }
}
