package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

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
        productos.add(nombre);
    }

    public void eliminar(String nombre) {
        productos.remove(nombre);
    }

    public int total() {
        return productos.size();
    }

    // Ejercicio del punto 9: Verifica si existe el producto
    public boolean existe(String nombre) {
        return productos.contains(nombre);
    }
}