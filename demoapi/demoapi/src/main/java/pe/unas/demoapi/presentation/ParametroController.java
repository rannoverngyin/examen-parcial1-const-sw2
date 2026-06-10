package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.ParametroService;

@RestController
@RequestMapping("/parametros")
// Expone parámetros de configuración.
public class ParametroController {

    private final ParametroService service;

    public ParametroController(ParametroService service) {
        this.service = service;
    }

    @GetMapping("/institucion")
    // Devuelve la institución.
    public String institucion() {
        return service.obtenerInstitucion();
    }

    @GetMapping("/modo")
    // Devuelve el modo actual.
    public String modo() {
        return service.obtenerModo();
    }

    @GetMapping("/limite-usuarios")
    // Devuelve el límite de usuarios.
    public int limiteUsuarios() {
        return service.obtenerLimiteUsuarios();
    }

    @GetMapping("/version")
    // Devuelve la versión.
    public String version() {
        return service.obtenerVersionSistema();
    }
}