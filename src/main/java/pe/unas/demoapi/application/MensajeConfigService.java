package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MensajeConfigService {
    @Value("${app.mensaje}")
    private String mensaje;
    public String obtenerMensaje() {
        return mensaje;
    }
}
