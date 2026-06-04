package configuracion.perfilesEntornos.application;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


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

    public String ObtenerEntorno(){
        return entorno;
     }

     public String ObtenerMensaje(){
        return mensaje;
     }

     public String ObtenerVersion(){
        return version;
    }
    public String ObtenerSoporte(){
        return soporte;
    }
}
