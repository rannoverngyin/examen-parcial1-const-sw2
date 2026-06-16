package pe.unas.demoapi.application;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MensajeService {

    private final MessageSource messageSource;

    public MensajeService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String obtenerMensaje(String clave, String idioma) {
        Locale locale = idioma.equalsIgnoreCase("en")
                ? Locale.ENGLISH
                : new Locale("es");

        return messageSource.getMessage(clave, null, locale);
    }
}
