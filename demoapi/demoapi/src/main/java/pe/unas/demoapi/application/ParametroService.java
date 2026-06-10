package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
// Expone parámetros de configuración.
public class ParametroService {

    // Datos de la institución.
    @Value("${app.institucion}")
    private String institucion;

    // Modo de ejecución.
    @Value("${app.modo}")
    private String modo;

    // Límite de usuarios.
    @Value("${app.limite-usuarios}")
    private int limiteUsuarios;

    // Versión del sistema.
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