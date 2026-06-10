package pe.unas.demoapi.application;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.notificacion.proveedor", havingValue = "email")
// Servicio real de notificación por correo.
public class EmailNotificadorService implements NotificadorService {
    @Override
    public String enviar(String destino) {
        // Devuelve un mensaje simple de confirmación.
        return "Notificación enviada por EMAIL a " + destino;
    }
}