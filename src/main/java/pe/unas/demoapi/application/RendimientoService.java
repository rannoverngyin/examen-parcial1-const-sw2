package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendimientoService {

    private final List<String> productos = List.of(
            "Laptop",
            "Mouse",
            "Teclado",
            "Monitor",
            "Impresora"
    );

    /*
     * Versión base:
     * simula una operación lenta y repite la transformación
     * de los productos en cada solicitud.
     */
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

    /*
     * La lista optimizada se prepara una sola vez.
     */
    private final List<String> productosOptimizados = productos.stream()
            .map(String::toUpperCase)
            .toList();

    /*
     * Versión optimizada:
     * reutiliza la respuesta preparada y evita el retraso.
     */
    public List<String> listarOptimizado() {
        return productosOptimizados;
    }
}