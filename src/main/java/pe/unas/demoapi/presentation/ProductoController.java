package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

    @GetMapping("/productos")
    public List<String> listarProductos() {
        return List.of("Producto 1", "Producto 2", "Producto 3");
    }
}