package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RendimientoService {

    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora");

    public List<String> listarBase() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return productos.stream()
                .map(String::toUpperCase)
                .toList();
    }

    private final List<String> productosOptimizados = productos.stream()
            .map(String::toUpperCase)
            .toList();

    public List<String> listarOptimizado() {
        return productosOptimizados;
    }
}
