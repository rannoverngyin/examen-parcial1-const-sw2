package pe.unas.demoapi20.presentation;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

    @GetMapping("/productos")
    public List<Map<String, Object>> listarProductos() {
        return List.of(
            Map.of("id", 1, "nombre", "Laptop", "precio", 2500.00),
            Map.of("id", 2, "nombre", "Mouse", "precio", 35.50),
            Map.of("id", 3, "nombre", "Teclado", "precio", 80.00)
        );
    }
}