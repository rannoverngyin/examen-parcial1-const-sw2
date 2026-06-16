package pe.unas.demoapi.application;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "test", "default"})
public class MockNotificadorService implements NotificadorService {

    @Override
    public String enviar(String destino) {
        return "Notificacion simulada enviada a " + destino;
    }
}