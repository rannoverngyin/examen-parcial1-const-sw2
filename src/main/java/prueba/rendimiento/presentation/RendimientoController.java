package prueba.rendimiento.presentation;

import org.springframework.web.bind.annotation.*;
import prueba.rendimiento.application.RendimientoService;
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

