package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
public class RendimientoService {

    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora");

    public List<String> listarBase(int cantidad) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return IntStream.range(0, cantidad)
                .mapToObj(i -> productos.get(i % productos.size()).toUpperCase())
                .toList();
    }

    private final Map<Integer, List<String>> cacheOptimizada = new ConcurrentHashMap<>();

    public List<String> listarOptimizado(int cantidad) {
        return cacheOptimizada.computeIfAbsent(cantidad, c -> 
            IntStream.range(0, c)
                    .mapToObj(i -> productos.get(i % productos.size()).toUpperCase())
                    .toList()
        );
    }
}
