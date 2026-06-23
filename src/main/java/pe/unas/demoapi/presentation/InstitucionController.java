package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.InstitutoService;

@RestController
public class InstitucionController {
    private final InstitutoService Service;
    public InstitucionController(InstitutoService Service) {
        this.Service = Service;
    }
    @GetMapping("/conf/institucion")
    public String intitutcion (){   
        return Service.obtenerInstitucion();
    }
}
