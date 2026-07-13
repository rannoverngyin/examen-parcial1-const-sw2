package pe.unas.demoapi.presentation;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

import pe.unas.demoapi.application.CargaService;

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
        return Map.of("peticiones", cargaService.totalPeticiones());
    }
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
