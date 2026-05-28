package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.ProductoService;

@RestController
@RequestMapping
public class ProductoController {

    public ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> listar() {
        return service.listar();
    }

    

}
