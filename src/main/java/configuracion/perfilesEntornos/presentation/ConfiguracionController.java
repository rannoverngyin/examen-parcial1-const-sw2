package configuracion.perfilesEntornos.presentation;

import org.springframework.web.bind.annotation.*;
import configuracion.perfilesEntornos.application.ConfiguracionService;
import java.util.*;

@RestController
@RequestMapping("/config")
public class ConfiguracionController {
    private final ConfiguracionService configuracionService;
    
    public ConfiguracionController(ConfiguracionService configuracionService){
        this.configuracionService=configuracionService;
    }

    @GetMapping("/entorno")
    public String entorno(){
        return configuracionService.ObtenerEntorno();
    }

    @GetMapping("/mensaje")
    public String mensaje(){
        return configuracionService.ObtenerMensaje();
    }
    @GetMapping("/soporte")
    public String soporte(){
        return configuracionService.ObtenerSoporte();
    }

@GetMapping("/info")
    public Map<String, String> info(){
        return Map.of(
            "entorno", configuracionService.ObtenerEntorno(),
            "mensaje", configuracionService.ObtenerMensaje(),
            "version", configuracionService.ObtenerVersion(),
            "soporte", configuracionService.ObtenerSoporte()
        );
    }
    
}
