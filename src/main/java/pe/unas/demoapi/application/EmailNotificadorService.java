package pe.unas.demoapi.application;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class EmailNotificadorService implements NotificadorService {

    @Override
    public String enviar(String destino) {
        return "Notificacion real enviada a " + destino;
    }
}