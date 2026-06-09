package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.*;

import pe.unas.demoapi.application.NotificadorService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificadorService service;

    public NotificacionController(
            NotificadorService service){

        this.service = service;
    }

    @PostMapping("/enviar")
    public String enviar(
            @RequestParam String destino){

        return service.enviar(destino);
    }
}