package unas.pe.demo.presentation;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unas.pe.demo.application.ParametroService;

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

    // NUEVO ENDPOINT 
    @GetMapping("/version")
    public String version() {
        return service.obtenerVersionSistema();
    }
}