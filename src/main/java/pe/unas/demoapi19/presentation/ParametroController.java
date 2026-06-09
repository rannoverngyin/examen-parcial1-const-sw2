package pe.unas.demoapi19.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi19.application.ParametroService;

@RestController
@RequestMapping("/parametros")
public class ParametroController {
    private final ParametroService service;

    public ParametroController(ParametroService service) {
        this.service = service;
    }

    @GetMapping("/institucion")
    public String institucion() {
        return service.obtenerInstitucion();
    }

    @GetMapping("/modo")
    public String modo() {
        return service.obtenerModo();
    }

    @GetMapping("/limite-usuarios")
    public int limiteUsuarios() {
        return service.obtenerLimiteUsuarios();
    }

    @GetMapping("/version")
    public String version() {
        return service.obtenerVersionSistema();
    }

}
