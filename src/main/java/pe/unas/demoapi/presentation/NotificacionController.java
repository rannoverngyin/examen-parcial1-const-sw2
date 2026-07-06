package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/enviar")
    public String enviarGet(@RequestParam String destino) {
        return enviar(destino);
    }

    @PostMapping("/enviar")
    public String enviarPost(@RequestParam String destino) {
        return enviar(destino);
    }

    private String enviar(String destino) {
        return service.enviar(destino);
    }
}
