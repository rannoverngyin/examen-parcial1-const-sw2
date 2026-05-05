package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import pe.unas.demoapi.application.ProyectoService;

@RestController
public class ProyectoController {
    private final ProyectoService service;

    public ProyectoController(ProyectoService service){
        this.service = service;
    }

    @GetMapping("/proyectos")
    public List<String> listar(){
        return  service.Listar();
    }
}
