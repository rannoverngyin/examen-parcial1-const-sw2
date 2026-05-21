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

    public synchronized List<String> listar() {
        return new ArrayList<>(productos);
    }

public synchronized void agregar(String nombre) {
    if (nombre == null || nombre.isBlank()) {
        throw new IllegalArgumentException("El nombre del producto es obligatorio");
    }

    String nombreLimpio = nombre.trim();

    if (productos.contains(nombreLimpio)) {
        throw new IllegalArgumentException("El producto ya existe");
    }

    productos.add(nombreLimpio);
}


    public synchronized void eliminar(String nombre) {
        productos.remove(nombre);
    }

    public synchronized int total() {
        return productos.size();
    }

    public synchronized boolean existe(String nombre) {
        return productos.contains(nombre);
    }

    
}
