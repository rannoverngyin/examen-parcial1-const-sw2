package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> listarProductos() {
        return service.listar();
    }

    @PostMapping
    public String agregarProducto(@RequestParam String nombre) {
        service.agregar(nombre);
        return "Producto agregado";
    }
    @DeleteMapping
    public String eliminar (@RequestParam String nombre) {
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