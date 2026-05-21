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

    // GET /productos
    @GetMapping
    public List<String> listar() {
        return productoService.listar();
    }

    // POST /productos?nombre=Teclado
    @PostMapping
    public String agregar(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return "Producto agregado";
    }

    // DELETE /productos?nombre=Mouse
    @DeleteMapping
    public String eliminar(@RequestParam String nombre) {
        productoService.eliminar(nombre);
        return "Producto eliminado";
    }

    // GET /productos/total
    @GetMapping("/total")
    public int total() {
        return productoService.total();
    }
}