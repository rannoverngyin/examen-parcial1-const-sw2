package com.gonza.unas.presentation;

import com.gonza.unas.application.ProductoService;
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
    public List<String> listar() {
        return productoService.listar();
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

    @GetMapping("/total")
    public int total() {
        return productoService.total();
    }

    @GetMapping("/existe")
    public boolean existe(@RequestParam String nombre) {
        return productoService.existe(nombre);
    }
}