package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.ConfiguracionService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfiguracionController {

    private final ConfiguracionService configuracionService;

    public ConfiguracionController(ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    @GetMapping("/entorno")
    public String obtenerEntorno() {
        return configuracionService.getEntorno();
    }

    @GetMapping("/mensaje")
    public String obtenerMensaje() {
        return configuracionService.getMensaje();
    }

    @GetMapping("/info")
    public Map<String, String> obtenerInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("entorno", configuracionService.getEntorno());
        info.put("mensaje", configuracionService.getMensaje());
        info.put("version", configuracionService.getVersion());
        return info;
    }

    @GetMapping("/soporte")
    public String obtenerSoporte() {
        return configuracionService.getSoporte();
    }
}
