package com.gonzalo.api_cs2.presentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gonzalo.api_cs2.application.DiagnosticoTIService;
import java.util.Map;

@RestController
public class DiagnosticoTIController {
    private final DiagnosticoTIService service;
    public DiagnosticoTIController(DiagnosticoTIService service) {
        this.service = service;
    }
    @GetMapping("/oti/diagnosticos/resumen")
    public Map<String, Object> resumen() {
        return service.resumen();
    }
}
