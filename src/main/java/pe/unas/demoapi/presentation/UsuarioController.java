package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.UsuarioService;

@RestController
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }
    @GetMapping("/usuarios")
    public List<String>listar(){
        return service.listar();
    }
}
