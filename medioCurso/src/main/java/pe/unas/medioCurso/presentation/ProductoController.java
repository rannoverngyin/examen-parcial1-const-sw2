package pe.unas.medioCurso.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.medioCurso.application.ProductoService;

import java.util.List;

@RestController
public class ProductoController {

    private final ProductoService service = new ProductoService();

    @GetMapping("/productos")
    public List<String> listar() {
        return service.listar();
    }
}