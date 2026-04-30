package com.clase1.demo.application;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductoService {
    private List<String> productos = new ArrayList<>();

    public ProductoService() {
        productos.add("Laptop");
        productos.add("Mouse");
    }

    public List<String> listar() {
        return productos;
    }

    public void agregar(String nombre) {
        productos.add(nombre);
    }

    public void eliminar(String nombre) {
        productos.remove(nombre);
    }

    // Ejercicio extra
    public int total() {
        return productos.size();
    }
}