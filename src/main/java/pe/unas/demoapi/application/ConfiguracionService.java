package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionService {

    @Value("${app.entorno}")
    private String entorno;

    @Value("${app.mensaje}")
    private String mensaje;

    @Value("${app.version}")
    private String version;

    // Para el ejercicio aplicado
    @Value("${app.soporte:no-configurado@unas.edu.pe}")
    private String soporte;

    public String obtenerEntorno() {
        return entorno;
    }

    public String obtenerMensaje() {
        return mensaje;
    }

    public String obtenerVersion() {
        return version;
    }

    public String obtenerSoporte() {
        return soporte;
    }
}