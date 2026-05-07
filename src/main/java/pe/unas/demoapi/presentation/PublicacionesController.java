package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.PublicacionesService;
import java.util.List;

@RestController
public class PublicacionesController {
    private final PublicacionesService service;
    public PublicacionesController(PublicacionesService service){
        this.service = service;
    }

    @GetMapping("/publicaciones")
    public List<String> Listar(){
        return List.of("Articulo IA", "Articulo Software");
    }
}
