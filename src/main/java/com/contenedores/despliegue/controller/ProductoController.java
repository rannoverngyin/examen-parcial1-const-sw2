package com.contenedores.despliegue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Arrays;

@RestController
public class ProductoController {

    @GetMapping("/productos")
    public List<String> obtenerProductos() {
        return Arrays.asList("Laptop", "Mouse", "Teclado", "Monitor");
    }
}
