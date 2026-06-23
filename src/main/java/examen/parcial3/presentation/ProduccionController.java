package examen.parcial3.presentation;

import examen.parcial3.application.ProduccionService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProduccionController {
 
    private final ProduccionService service;
    public ProduccionController(ProduccionService service){
        this.service =service;
    }

    @GetMapping("/mensaje")
    public String mensaje(){
        return service.obtenerMensaje();
    }
}
