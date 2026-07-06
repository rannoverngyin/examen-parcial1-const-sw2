package pe.unas.demoapi.application;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class mensajesService {

    private final MessageSource messageSource;

    public mensajesService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String obtenerMensaje(String codigo, String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        return messageSource.getMessage(codigo, null, locale);
    }
}
