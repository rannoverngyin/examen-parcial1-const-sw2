package com.carga.metricas.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carga.metricas.application.CargaService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carga")
public class CargaController {

    private final CargaService service;

    public CargaController(CargaService service) {
        this.service = service;
    }

    @GetMapping("/productos")
    public List<String> productos() {
        return service.listarProductos();
    }

    @GetMapping("/metricas")
    public Map<String, Long> metricas() {
        return Map.of("peticiones", service.totalPeticiones());
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
