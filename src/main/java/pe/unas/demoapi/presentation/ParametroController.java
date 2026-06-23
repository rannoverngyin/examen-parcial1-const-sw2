package pe.unas.demoapi.presentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParametroController {

    @Value("${app.mensaje}")
    private String mensaje;

    @GetMapping("/config/mensaje")
    public String getMensaje() {
        return mensaje;
    }
}
