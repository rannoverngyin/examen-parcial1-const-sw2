package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class RendimientoService {

    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora"
    );

    private final List<String> productosOptimizados = productos.stream()
            .map(String::toUpperCase)
            .toList();

    /**
     * Version base: simula una operacion lenta (espera externa) y repite
     * trabajo de transformacion en cada llamada.
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

    /**
     * Version optimizada: reutiliza una respuesta inmutable precalculada
     * y evita el retraso artificial.
     */
    public List<String> listarOptimizado() {
        return productosOptimizados;
    }

    /**
     * Reto aplicado: version base parametrizable por cantidad. Genera el
     * dataset y aplica el mismo retraso artificial que la version original,
     * para observar la degradacion del p95 al crecer el tamano de la
     * respuesta (generacion de datos + serializacion JSON).
     */
    public List<String> listarBase(int cantidad) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return generarDataset(cantidad).stream()
                .map(String::toUpperCase)
                .toList();
    }

    /**
     * Reto aplicado: version optimizada parametrizable por cantidad. Genera
     * el dataset una sola vez (sin retraso artificial) para aislar el costo
     * de generacion/serializacion del costo de la espera simulada.
     */
    public List<String> listarOptimizado(int cantidad) {
        return generarDataset(cantidad).stream()
                .map(String::toUpperCase)
                .toList();
    }

    private List<String> generarDataset(int cantidad) {
        return IntStream.range(0, cantidad)
                .mapToObj(i -> productos.get(i % productos.size()) + "-" + i)
                .toList();
    }
}
