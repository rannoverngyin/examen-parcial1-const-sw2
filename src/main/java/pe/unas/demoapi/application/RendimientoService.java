package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class RendimientoService {

    public List<String> listarBase(int cantidad) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return IntStream.rangeClosed(1, cantidad)
                .mapToObj(i -> "PRODUCTO-" + i)
                .map(String::toUpperCase)
                .toList();
    }

    public List<String> listarOptimizado(int cantidad) {
        return IntStream.rangeClosed(1, cantidad)
                .mapToObj(i -> "PRODUCTO-" + i)
                .toList();
    }
}