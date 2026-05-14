package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.EstudianteService;
import java.util.List;

@RestController
public class EstudianteController {
    private final EstudianteService service;
    public EstudianteController (EstudianteService service){
        this.service = service;
    }
    
    @GetMapping("/estudiantes")
    public List<String> listar() {
        return service.listar();
    }
}
