package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Value("${app.perfil}")
    private String perfil;
    public String perfilActivo(){
        return perfil;
    }
    

}