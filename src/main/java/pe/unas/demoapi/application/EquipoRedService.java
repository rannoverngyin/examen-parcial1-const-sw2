package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipoRedService {
    public List<String> Listar(){
        return List.of("Router", "Switch");
    }
}
