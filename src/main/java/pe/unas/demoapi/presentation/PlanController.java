package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.PlanService;

@RestController
public class PlanController {
    private final PlanService service;
    private PlanController(PlanService service){
        this.service = service;
    }

    @GetMapping("/plan/descuento")
    public int descuento(){
        return service.descuento();
    }
}
