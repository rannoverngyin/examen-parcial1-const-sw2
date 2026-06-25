package pe.unas.const_sw2_parcial2.application;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.notificador", havingValue = "email")
public class EmailNotificador implements Notificador {

    @Override
    public String enviar(String mensaje) {
        return "Email enviado: " + mensaje;
    }
}
