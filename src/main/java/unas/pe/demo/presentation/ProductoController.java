package unas.pe.demo.presentation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unas.pe.demo.application.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    // Inyección de dependencia por constructor
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<String> listarProductos() {
        return productoService.listar();
    }

    @PostMapping
    public ResponseEntity<String> agregarProducto(@RequestParam String nombre) {
        productoService.agregar(nombre);
        return ResponseEntity.ok("Producto agregado");
    }
}