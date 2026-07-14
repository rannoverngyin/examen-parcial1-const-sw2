package unas.pe.demo.presentation;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import unas.pe.demo.application.RendimientoService;
import java.util.List;

@RestController
@RequestMapping("/rendimiento/productos")
public class RendimientoController {

    private final RendimientoService service;

    public RendimientoController(RendimientoService service) {
        this.service = service;
    }

    @GetMapping("/base")
    public List<String> listarBase(@RequestParam(defaultValue = "5") int cantidad) {
        // Ahora pasamos la variable 'cantidad' al servicio de forma efectiva
        return service.listarBase(cantidad); 
    }

    @GetMapping("/optimizado")
    public List<String> listarOptimizado(@RequestParam(defaultValue = "5") int cantidad) {
        // Ahora pasamos la variable 'cantidad' al servicio de forma efectiva
        return service.listarOptimizado(cantidad);
    }
}