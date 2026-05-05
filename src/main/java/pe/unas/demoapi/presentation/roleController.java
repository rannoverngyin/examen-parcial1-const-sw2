package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.roleService;
import java.util.List;

@RestController
public class roleController {
    private final roleService service;

    public roleController(roleService service){
        this.service = service;
    }

    @GetMapping("/role")
    public List<String> Listar(){
        return service.Listar();
    }
}
