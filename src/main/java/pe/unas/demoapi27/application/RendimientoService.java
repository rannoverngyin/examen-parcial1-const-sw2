package pe.unas.demoapi27.application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class RendimientoService {

    private static final int MAX_CANTIDAD = 10_000;

    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora"
    );

    /*
     * La versión optimizada prepara una sola vez los 10 000 elementos.
     * Luego reutiliza esa lista según la cantidad solicitada.
     */
    private final List<String> productosOptimizados =
            crearProductos(MAX_CANTIDAD);

    public List<String> listarBase(int cantidad) {
        validarCantidad(cantidad);

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // La versión base vuelve a generar los elementos en cada petición.
        return crearProductos(cantidad);
    }

    public List<String> listarOptimizado(int cantidad) {
        validarCantidad(cantidad);

        // Reutiliza la respuesta ya preparada.
        return productosOptimizados.subList(0, cantidad);
    }

    private List<String> crearProductos(int cantidad) {
        return IntStream.range(0, cantidad)
                .mapToObj(i ->
                        productos.get(i % productos.size()) + "-" + (i + 1)
                )
                .map(String::toUpperCase)
                .toList();
    }

    private void validarCantidad(int cantidad) {
        if (cantidad < 1 || cantidad > MAX_CANTIDAD) {
            throw new IllegalArgumentException(
                    "La cantidad debe estar entre 1 y 10000"
            );
        }
    }
}