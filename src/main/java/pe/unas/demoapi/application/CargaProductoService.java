package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CargaProductoService {

    private final List<String> productos =
            new CopyOnWriteArrayList<>(
                    List.of(
                            "Laptop",
                            "Mouse",
                            "Teclado"
                    )
            );

    public List<String> listar() {
        return List.copyOf(productos);
    }

    public int total() {
        return productos.size();
    }

    public String agregar(String nombre) {
        productos.add(nombre);
        return nombre;
    }

    public String reporte() {
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return "productos=" + productos.size();
    }
}