package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service

public class NombreSistemaService {
    @Value("${app.nombre}")
    private String nombre;
    public String obtenerNombre(){
        return nombre;
    }
}
