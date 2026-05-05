package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.ResolucionService;
import java.util.*;
@RestController
public class ResolucionController {
    private final ResolucionService service;
    public ResolucionController(ResolucionService service){
        this.service=service;
    }
    

    @GetMapping("/resoluciones")
    public List<String> Listar(){
        return service.Listar();
    }


}
