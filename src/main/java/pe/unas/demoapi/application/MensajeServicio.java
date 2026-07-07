package pe.unas.demoapi.application;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MensajeServicio {
    
    private final MessageSource messageSource;
    
    public MensajeServicio(MessageSource messageSource){
        this.messageSource=messageSource;
    }

    public String obtenerMensaje(String codigo, String lang){
        Locale locale =Locale.forLanguageTag(lang);
        return messageSource.getMessage(codigo, null, locale);
    }
}
