package pe.unas.demoapi.presentation;

import pe.unas.demoapi.application.NombreSistemaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class NombreSistemaController {
    
    private final NombreSistemaService service;
    public NombreSistemaController(NombreSistemaService service){
        this.service = service;
    }
    @GetMapping ("/config/nombre")
    public String nombre(){
        return service.obtenerNombre();
    }

}
