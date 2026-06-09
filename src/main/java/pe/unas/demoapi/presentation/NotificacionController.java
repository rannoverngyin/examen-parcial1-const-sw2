package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.NotificadorService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    private final NotificadorService service;

    public NotificacionController(NotificadorService service) {
        this.service = service;
    }

    @PostMapping("/enviar")
    public String enviar(@RequestParam String destino) {
        return service.enviar(destino);
    }
}
