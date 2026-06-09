package sesion19.configuracion.presentation;

import org.springframework.web.bind.annotation.*;
import sesion19.configuracion.application.ParametroService;

@RestController
@RequestMapping("/parametros")
public class ParametroController {

    private final ParametroService service;
    public ParametroController(ParametroService service) {
        this.service = service;
    }

    @GetMapping("/institucion")
    public String obtenerInstitucion() {
        return service.obtenerInstitucion();
    }

    @GetMapping("/modo")
    public String obtenerModo() {
        return service.obtenerModo();
    }

    @GetMapping("/limite-usuarios")
    public int obtenerLimiteUsuarios() {   
        return service.obtenerUsuarios();
    }

    @GetMapping("/version")
    public String version() {
        return service.obtenerVersionSistema();
}
}
