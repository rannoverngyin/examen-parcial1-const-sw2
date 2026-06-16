package pe.unas.demoapi.application;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MensajeService {
    // Fuente de mensajes para internacionalización
    private final MessageSource messageSource;

    public MensajeService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Devuelve el mensaje traducido según la clave y el idioma.
     */
    public String obtenerMensaje(String clave, String idioma) {
        // Selecciona el locale según el idioma solicitado
        Locale locale = idioma.equalsIgnoreCase("en") ? Locale.ENGLISH : new Locale("es");
        return messageSource.getMessage(clave, null, locale);
    }
}