package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.application.CalculadoraService;

@RestController
@RequestMapping("/calculadora")
public class CalculadoraController {

    private final CalculadoraService service;

    public CalculadoraController(CalculadoraService service) {
        this.service = service;
    }

    @GetMapping("/sumar")
    public int sumar(@RequestParam int a, @RequestParam int b) {
        return service.sumaar(a, b);
    }

    @GetMapping("/restar")
    public int restar(@RequestParam int a, @RequestParam int b) {
        return service.restar(a, b);
    }

    @GetMapping("/multiplicar")
    public int multiplicar(@RequestParam int a, @RequestParam int b) {
        return service.multiplicar(a, b);
    }

    @GetMapping("/dividir")
    public int dividir(@RequestParam int a, @RequestParam int b) {
        return service.dividir(a, b);
    }
}

