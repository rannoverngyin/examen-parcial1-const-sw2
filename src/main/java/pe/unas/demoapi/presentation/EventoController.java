package pe.unas.demoapi.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.EventoService;

import java.util.List;

@RestController
public class EventoController {

    private final EventoService eventoService;
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }
    @GetMapping("/eventos")
    public List<String> Listar(){
        return eventoService.listar();
    }

}
