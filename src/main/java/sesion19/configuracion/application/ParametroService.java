package sesion19.configuracion.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParametroService {

    @Value("${app.institucion}")
    private String institucion;

    @Value("${app.modo}")
    private String modo;

    @Value("${app.version-sistema}")
    private String versionSistema;

    @Value("${app.limite-usuarios}")
    private int limiteUsuarios;

    public String obtenerInstitucion() {
        return institucion;
    }

    public String obtenerModo() {
        return modo;
    }

    public int obtenerUsuarios() {
        return limiteUsuarios;
    }

       public String obtenerVersionSistema() {
        return versionSistema;
    }
}
