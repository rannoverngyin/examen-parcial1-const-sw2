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

    @Value("${app.soporte}")
    private String soporte;

    public String getEntorno() {
        return entorno;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getVersion() {
        return version;
    }

    public String getSoporte() {
        return soporte;
    }
}
