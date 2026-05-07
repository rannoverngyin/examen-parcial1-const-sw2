package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.ModuloService;

import java.util.List;

@RestController
public class ModuloController {
    private final ModuloService service;
    public ModuloController(ModuloService service){
        this.service =service;
    }
    @GetMapping ("/modulos")

    public List<String> listar(){
        return service.listar();
    }

}
