package pe.unas.demoapi27.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi27.application.RendimientoService;

import java.util.List;

@RestController
@RequestMapping("/rendimiento/productos")
public class RendimientoController {

    private final RendimientoService service;

    public RendimientoController(RendimientoService service) {
        this.service = service;
    }

    @GetMapping("/base")
    public List<String> listarBase(
            @RequestParam(defaultValue = "5") int cantidad
    ) {
        return service.listarBase(cantidad);
    }

    @GetMapping("/optimizado")
    public List<String> listarOptimizado(
            @RequestParam(defaultValue = "5") int cantidad
    ) {
        return service.listarOptimizado(cantidad);
    }
}