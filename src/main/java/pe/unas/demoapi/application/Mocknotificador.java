package pe.unas.demoapi.application;

import javax.management.NotificationFilter;

import org.springframework.stereotype.Service;
@Service ("MockNotificador")



public class Mocknotificador implements Notification {

    @Override
    public String enviar (String mensaje) {
        return "Mock: " + mensaje;
    } 
    
  


    
}
