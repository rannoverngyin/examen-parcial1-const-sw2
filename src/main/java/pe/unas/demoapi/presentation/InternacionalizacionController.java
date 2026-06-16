package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.MensajeService;

@RestController
public class InternacionalizacionController {

    private final MensajeService service;

    public InternacionalizacionController(MensajeService service) {
        this.service = service;
    }

    @GetMapping("/i18n/saludo")
    public String saludo(@RequestParam(defaultValue = "es") String lang) {
        return service.obtenerMensaje("saludo", lang);
    }

    @GetMapping("/i18n/curso")
    public String curso(@RequestParam(defaultValue = "es") String lang) {
        return service.obtenerMensaje("curso", lang);
    }

    @GetMapping("/i18n/idioma")
    public String idioma(@RequestParam(defaultValue = "es") String lang) {
        return service.obtenerMensaje("idioma", lang);
    }

    @GetMapping("/i18n/saludo-header")
    public String saludoHeader(@RequestHeader(name = "Accept-Language", defaultValue = "es") String lang) {
        String idioma = lang.startsWith("en") ? "en" : "es";
        return service.obtenerMensaje("saludo", idioma);
    }

    @GetMapping("/i18n/evaluacion")
    public String evaluacion(@RequestParam(defaultValue = "es") String lang) {
        return service.obtenerMensaje("evaluacion", lang);
    }
}
