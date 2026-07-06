package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.unas.demoapi.application.MensajeService;

@RestController

public class I18nController {

    private final MensajeService service;

    public I18nController(MensajeService service) {
        this.service = service;
    }

    @GetMapping("/I18n/saludo")
    public String saludo(@RequestParam String lang) {
        return service.obtenerMensaje("saludo", lang);
    }
}
