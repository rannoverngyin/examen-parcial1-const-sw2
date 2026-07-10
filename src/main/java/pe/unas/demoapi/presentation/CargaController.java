package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.CargaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carga")
public class CargaController {

    private final CargaService cargaService;

    public CargaController(CargaService cargaService) {
        this.cargaService = cargaService;
    }

    @GetMapping("/productos")
    public List<String> productos() {
        return cargaService.listarProductos();
    }

    @GetMapping("/metricas")
    public Map<String, Long> metricas() {
        return Map.of(
                "peticiones",
                cargaService.totalPeticiones()
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status",
                "UP"
        );
    }
}