package pe.unas.demoapi.application;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.notificacion.proveedor", havingValue = "mock")
public class MockNotificadorService implements NotificadorService {

    @Override
    public String enviar(String destino) {
        return "Notificación simulada para " + destino;
    }
}
