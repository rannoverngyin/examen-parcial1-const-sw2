package pe.unas.demoapi.application;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RendimientoService {
    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora"
    );

    public List<String> listarBase() {
        try {
            // Simulamos un retraso de red o consulta pesada de 200ms
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productos.stream()
                .map(String::toUpperCase)
                .toList();
    }

    // Precalculamos la respuesta inmutable para evitar procesamientos y demoras repetitivas
    private final List<String> productosOptimizados = productos.stream()
            .map(String::toUpperCase)
            .toList();

    public List<String> listarOptimizado() {
        return productosOptimizados;
    }
}