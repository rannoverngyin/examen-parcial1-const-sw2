package pe.unas.const_sw2_parcial2.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.const_sw2_parcial2.application.Notificador;

@RestController
public class NotificacionController {

    private final Notificador notificador;

    public NotificacionController(Notificador notificador) {
        this.notificador = notificador;
    }

    @GetMapping("/notificar")
    public String notificar(@RequestParam String mensaje) {
        return notificador.enviar(mensaje);
    }
}
