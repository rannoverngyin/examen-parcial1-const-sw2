package pe.unas.demoapi.presentation;
import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.AulaService;
import java.util.List;

@RestController
public class AulaController {
    private final AulaService service;
    public AulaController(AulaService service) {
        this.service = service;
    }
    @GetMapping("/aulas")
    public List<String> listar()  {
        return service.listar();
    }
    

}
  
    

