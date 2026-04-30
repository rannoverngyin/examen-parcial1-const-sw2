package com.gonzalo.api_cs2.presentation;
import org.springframework.web.bind.annotation.*;

import com.gonzalo.api_cs2.application.ProductoService;

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
}