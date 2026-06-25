
package pe.unas.demoapi.presentation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import pe.unas.demoapi.application.Notification;

@Resource

@RequestMapping("/api")

public class notificacionController {

    private final Notification notificador;

    public notificacionController(Notification notificador) {
        this.notificador = notificador;
    }

    @GetMapping("/enviar")
    public String enviar() {
        return notificador.enviar("Hola Mundo");
    }
}