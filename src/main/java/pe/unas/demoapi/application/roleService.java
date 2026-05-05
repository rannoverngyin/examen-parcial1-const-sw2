package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class roleService {
    public List<String> Listar(){
        return List.of("ADMIN","DOCENTE");
    }
}
