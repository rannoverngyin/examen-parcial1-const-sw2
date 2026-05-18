package prueba.integracion.presentation;

import prueba.integracion.application.ProductoService;
import org.springframework.web.bind.annotation.*;

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
}