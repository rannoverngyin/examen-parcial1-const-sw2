package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.InvestigadorService;

@RestController
@RequestMapping("/investigadores")

public class InvestigadorController {

    private final InvestigadorService service;

    public InvestigadorController(InvestigadorService service) {
        this.service = service;
    }


    @GetMapping
    public List<String> listar() {
        return service.listarInvestigadores();
    }

    @GetMapping("/total")
    public int total() {
        return service.totalInvestigadores();
    }

    

}
