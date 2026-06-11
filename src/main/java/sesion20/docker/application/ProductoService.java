package sesion20.docker.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    public List<String> obtenerProductos() {
        return List.of(
                "Laptop",
                "Mouse",
                "Teclado",
                "Monitor"
        );
    }
}