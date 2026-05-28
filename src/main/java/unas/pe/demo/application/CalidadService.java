package unas.pe.demo.application;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CalidadService {
    
    // Tu lista base con los 2 productos iniciales ("Laptop" y "Mouse")
    private final List<String> productos = new CopyOnWriteArrayList<>();

    public CalidadService() {
        productos.add("Laptop");
        productos.add("Mouse");
    }

    // Este método devuelve la cantidad actual
    public int total() {
        return productos.size(); 
    }
}