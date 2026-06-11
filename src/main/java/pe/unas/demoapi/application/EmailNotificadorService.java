package pe.unas.demoapi.application;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.notificacion.proveedor", havingValue = "email")
public class EmailNotificadorService implements NotificadorService {

    @Override
    public String enviar(String destino) {
        return "Notificación enviada por EMAIL a " + destino;
    }
}
