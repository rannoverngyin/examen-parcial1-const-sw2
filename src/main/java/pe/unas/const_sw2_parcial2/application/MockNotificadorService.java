package pe.unas.const_sw2_parcial2.application;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
@Service
@ConditionalOnProperty(name = "app.notificacion", havingValue = "mock")
public class MockNotificadorService implements Notificador {
     @Override
    public String enviar(String destino) {
        return "Notificación simulada para " + destino;
    }
}



    
