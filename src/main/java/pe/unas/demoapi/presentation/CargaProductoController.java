package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.CargaProductoService;
import pe.unas.demoapi.application.CargaService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carga/productos")
public class CargaProductoController {
    private final CargaProductoService service;
    private final CargaService cargaService;

    public CargaProductoController(CargaProductoService service, CargaService cargaService) {
        this.service = service;
        this.cargaService = cargaService;
    }

    @GetMapping
    public List<String> listar() {
        return service.listar();
    }

    @GetMapping("/total")
    public Map<String, Integer> total() {
        return Map.of("total", service.total());
    }

    @PostMapping
    public Map<String, String> agregar(@RequestParam String nombre) {
        return Map.of("creado", service.agregar(nombre));
    }

    @GetMapping("/reporte")
    public Map<String, String> reporte() {
        return Map.of("resumen", service.reporte());
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
