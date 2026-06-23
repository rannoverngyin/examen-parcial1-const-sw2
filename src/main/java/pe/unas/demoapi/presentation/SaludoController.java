package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;

@RestController
public class SaludoController {
    @GetMapping("/saludo")
    public String saludo() {
        return "Hola Software II";
    }
    
}

