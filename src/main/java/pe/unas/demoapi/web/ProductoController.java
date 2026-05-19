package pe.unas.demoapi.web;

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

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<String> listar() {
        return productoService.listar();
    }

    @GetMapping("/total")
    public int total() {
        return productoService.total();
    }

    @GetMapping("/existe")
    public boolean existe(@RequestParam String nombre) {
        return productoService.existe(nombre);
    }

    @PostMapping
    public String agregar(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return "Producto agregado";
    }

    @DeleteMapping
    public String eliminar(@RequestParam String nombre) {
        productoService.eliminar(nombre);
        return "Producto eliminado";
    }
}