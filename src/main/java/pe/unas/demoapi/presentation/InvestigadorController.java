package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.investigadorService;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;


@RestController

public class InvestigadorController {
    private final investigadorService service;
    public InvestigadorController(investigadorService service){
        this.service = service;
    }
    @GetMapping("/investigadores")
    public List<String>Listar() {
        return service.Listar();
    }
    
}
