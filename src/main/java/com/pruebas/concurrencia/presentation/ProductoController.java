package com.pruebas.concurrencia.presentation;

import com.pruebas.concurrencia.application.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<String> listar() {
        return productoService.listar();
    }

    @PostMapping
    public ResponseEntity<String> agregar(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return ResponseEntity.ok("Producto agregado");
    }

    @DeleteMapping
    public ResponseEntity<String> eliminar(@RequestParam String nombre) {
        productoService.eliminar(nombre);
        return ResponseEntity.ok("Producto eliminado");
    }

    @GetMapping("/total")
    public int total() {
        return productoService.total();
    }
}