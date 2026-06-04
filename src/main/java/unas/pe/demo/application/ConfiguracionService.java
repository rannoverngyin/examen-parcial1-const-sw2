package unas.pe.demo.application;



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

    // Nueva propiedad para el ejercicio aplicado
    @Value("${app.soporte}")
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

    // Nuevo método expuesto
    public String obtenerSoporte() {
        return soporte;
    }
}