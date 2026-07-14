package unas.pe.demo.application;

import org.springframework.stereotype.Service;
import java.util.List;

import java.util.stream.IntStream;

@Service
public class RendimientoService {

    // Versión Base: Genera la cantidad de elementos en demanda y aplica el retraso
    public List<String> listarBase(int cantidad) {
        try {
            // Retraso controlado artificial para simular operación lenta
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Genera dinámicamente una lista del tamaño solicitado por el usuario
        return IntStream.range(0, cantidad)
                .mapToObj(i -> "PRODUCTO_BASE_" + i)
                .map(String::toUpperCase)
                .toList();
    }

    // Versión Optimizada: Genera los elementos dinámicamente SIN el retraso de hilos
    public List<String> listarOptimizado(int cantidad) {
        // Genera la lista de inmediato para medir el impacto puro de la serialización/red
        return IntStream.range(0, cantidad)
                .mapToObj(i -> "PRODUCTO_OPTIMIZADO_" + i)
                .map(String::toUpperCase)
                .toList();
    }
}