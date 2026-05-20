package unas.pe.demo.presentation;




import org.springframework.web.bind.annotation.*;
import unas.pe.demo.application.ProductoService;
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

    // Ejercicio aplicado de la sesión 12
    @GetMapping("/existe")
    public boolean existe(@RequestParam String nombre) {
        return service.existe(nombre);
    }
}