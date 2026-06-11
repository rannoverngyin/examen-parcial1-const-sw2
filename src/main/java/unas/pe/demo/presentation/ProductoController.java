package unas.pe.demo.presentation;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @GetMapping
    public List<Map<String, String>> getProductos() {
        return List.of(
            Map.of("id", "1", "nombre", "Laptop Intel Core i7", "precio", "1500"),
            Map.of("id", "2", "nombre", "Monitor Gamer 144Hz", "precio", "350"),
            Map.of("id", "3", "nombre", "Teclado Mecanico RGB", "precio", "80")
        );
    }
}