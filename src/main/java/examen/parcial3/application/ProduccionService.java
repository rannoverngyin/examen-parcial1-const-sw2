package examen.parcial3.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProduccionService {
    
    @Value("${app.mensaje}")
    private String mensaje;


    public String obtenerMensaje(){
        return mensaje;
    }
}
