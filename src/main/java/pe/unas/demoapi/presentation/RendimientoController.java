package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.RendimientoService;
import java.util.List;

@RestController
@RequestMapping("/rendimiento/productos")
public class RendimientoController {

    private final RendimientoService service;

    public RendimientoController(RendimientoService service) {
        this.service = service;
    }

    @GetMapping("/base")
    public List<String> listarBase() {
        return service.listarBase();
    }

    @GetMapping("/optimizado")
    public List<String> listarOptimizado() {
        return service.listarOptimizado();
    }
}