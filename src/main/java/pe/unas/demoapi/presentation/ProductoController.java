package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;

import pe.unas.demoapi.application.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<String> listar() {
        return productoService.listar();
    }

    @PostMapping
    public String agregar(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return "Producto agregado";
    }
}