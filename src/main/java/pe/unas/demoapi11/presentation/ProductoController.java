package pe.unas.demoapi11.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi11.application.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<String> listarProductos() {
        return productoService.listar();
    }

    @PostMapping
    public String agregarProducto(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return "Producto agregado";
    }

    @DeleteMapping
    public String eliminarProducto(@RequestParam String nombre) {
        productoService.eliminar(nombre);
        return "Producto eliminado";
    }

    @GetMapping("/total")
    public int totalProductos() {
        return productoService.total();
    }
}