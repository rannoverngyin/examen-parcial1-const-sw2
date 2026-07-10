package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.RendimientoService;

import java.util.List;

@RestController
@RequestMapping("/rendimiento/productos")
public class RendimientoController {

    private final RendimientoService rendimientoService;

    public RendimientoController(RendimientoService rendimientoService) {
        this.rendimientoService = rendimientoService;
    }

    @GetMapping("/base")
    public List<String> listarBase() {
        return rendimientoService.listarBase();
    }

    @GetMapping("/optimizado")
    public List<String> listarOptimizado() {
        return rendimientoService.listarOptimizado();
    }
}