package pe.unas.demoapi.presentation;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.DocenteService;

@RestController
public class DocenteController {
    private final DocenteService service;
    public DocenteController(DocenteService service) {
        this.service = service;
    }
    @GetMapping("/docentes")
    public List<String> Listar() {
        return service.Listar();
    }
}
