package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParametroService {

    @Value("${app.institucion}")
    private String institucion;

    @Value("${app.modo}")
    private String modo;

    @Value("${app.limite-usuarios}")
    private int limiteUsuarios;

    @Value("${app.version-sistema}")
    private String versionSistema;

    public String obtenerInstitucion() {
        return institucion;
    }

    public String obtenerModo() {
        return modo;
    }

    public int obtenerLimiteUsuarios() {
        return limiteUsuarios;
    }

    public String obtenerVersionSistema() {
        return versionSistema;
    }
}
