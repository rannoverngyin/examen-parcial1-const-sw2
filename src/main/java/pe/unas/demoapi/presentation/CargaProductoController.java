package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.CargaProductoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carga/productos")
public class CargaProductoController {

    private final CargaProductoService cargaProductoService;

    public CargaProductoController(
            CargaProductoService cargaProductoService
    ) {
        this.cargaProductoService = cargaProductoService;
    }

    @GetMapping
    public List<String> listar() {
        return cargaProductoService.listar();
    }

    @GetMapping("/total")
    public Map<String, Integer> total() {
        return Map.of(
                "total",
                cargaProductoService.total()
        );
    }

    @PostMapping
    public Map<String, String> agregar(
            @RequestParam String nombre
    ) {
        return Map.of(
                "creado",
                cargaProductoService.agregar(nombre)
        );
    }

    @GetMapping("/reporte")
    public Map<String, String> reporte() {
        return Map.of(
                "resumen",
                cargaProductoService.reporte()
        );
    }
}