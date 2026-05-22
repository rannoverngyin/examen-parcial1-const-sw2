package pe.unas.demoapi.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public List<String> listar() {
        return service.listar();
    }

    @PostMapping
    public String agregar(@RequestParam String nombre) {
        service.agregar(nombre);
        return "Producto agregado";  // <-- el test espera este mensaje exacto
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
}
