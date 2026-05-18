package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.LineaService;

@RestController
public class LineaController {
    private final LineaService service;
    public LineaController(LineaService service){
        this.service = service;
    }

    @GetMapping("/lineas")
    public List<String> listar(){
        return service.listar();
    }
}


