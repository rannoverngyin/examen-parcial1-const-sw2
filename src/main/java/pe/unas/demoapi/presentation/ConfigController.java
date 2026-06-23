package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.ConfigService;

@RestController
public class ConfigController {
    private final ConfigService service;
    public ConfigController(ConfigService service){
        this.service = service;
    }

    @GetMapping("/config/perfil")
    public String perfil(){
        return service.perfilActivo();
    }
   
}