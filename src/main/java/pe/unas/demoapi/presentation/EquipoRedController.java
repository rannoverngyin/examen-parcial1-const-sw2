package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.EquipoRedService;
import java.util.List;

@RestController
public class EquipoRedController {
    private final EquipoRedService service;
    public EquipoRedController(EquipoRedService service){
        this.service = service;
    }

    @GetMapping("/equipos-red")
    public List<String> Listar(){
        return service.Listar();
    }
}
