package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.MensajeConfigService;

@RestController
public class MensajeConfigController {
    private final MensajeConfigService service;
    public MensajeConfigController(MensajeConfigService service){
        this.service = service;
    }
    @GetMapping("/config/mensaje")
    public String mensaje(){
        return service.obtenerMensaje();
    }
}
