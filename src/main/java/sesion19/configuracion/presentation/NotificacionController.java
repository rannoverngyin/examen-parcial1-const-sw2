package sesion19.configuracion.presentation;

import sesion19.configuracion.application.NotificadorService;
import org.springframework.web.bind.annotation.*;   

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
