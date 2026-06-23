package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class InstitutoService {
    @Value("${app.intituccion:UNAS}")
    private String institucion;
    public String obtenerInstitucion(){
        return institucion;
    }
   
}
