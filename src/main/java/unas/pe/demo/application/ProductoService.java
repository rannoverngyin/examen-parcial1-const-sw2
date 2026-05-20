package unas.pe.demo.application;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProductoService {
    // Colección segura para hilos concurrentes
    private final List<String> productos = new CopyOnWriteArrayList<>();

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
}