package com.configuracion.desplieguee.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.configuracion.desplieguee.application.DeploymentValidationService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deploy")
public class DeploymentValidationController {

    private final DeploymentValidationService service;

    public DeploymentValidationController(DeploymentValidationService service) {
        this.service = service;
    }

    @GetMapping("/config")
    public Map<String, String> config() {
        return service.config();
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return service.health();
    }

    @GetMapping("/checklist")
    public List<String> checklist() {
        return List.of(
                "perfil activo validado",
                "configuracion externa cargada",
                "api responde correctamente",
                "contenedor listo para despliegue"
        );
    }

    @GetMapping("/version")
    public String version() {
        return service.version();
    }
}
