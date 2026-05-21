package pe.unas.demoapi.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final List<String> productos = new ArrayList<>();

    public ProductoService() {
        productos.add("Laptop");
        productos.add("Mouse");
    }

    public List<String> listar() {
        return productos;
    }

    public void agregar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        String nombreLimpio = nombre.trim();

        if (productos.contains(nombreLimpio)) {
            throw new IllegalArgumentException("El producto ya existe");
        }

        productos.add(nombreLimpio);
    }

    public void eliminar(String nombre) {
        productos.remove(nombre);
    }

    public int total() {
        return productos.size();
    }

    public boolean existe(String nombre) {
        return productos.contains(nombre);
    }
}