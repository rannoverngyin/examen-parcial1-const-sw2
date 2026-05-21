package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.ProductoService;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> listar() {
        return service.listar();
    }

    @PostMapping
    public String agregar(@RequestParam String nombre) {
        service.agregar(nombre);
        return "Producto agregado";
    }

    @DeleteMapping
    public String eliminar(@RequestParam String nombre) {
        service.eliminar(nombre);
        return "Producto eliminado";
    }

    @GetMapping("/total")
    public int total() {
        return service.total();
    }

    @GetMapping("/existe")
    public boolean existe(@RequestParam String nombre) {
        return service.existe(nombre);
    }
}