package pe.unas.demoapi.Presentation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;


import pe.unas.demoapi.Application.FacultadService;
@RestController
@Controller
public class FacultadController {
    private final FacultadService service;
    public FacultadController (FacultadService service){
        this.service= service;
    }
    @GetMapping("/facultades")
    public List<String> listar(){
        return service.listar();
    }


}
