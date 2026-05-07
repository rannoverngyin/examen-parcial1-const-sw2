package pe.unas.demoapi.aplication;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LineaService {
    public List <String> listar(){
        return List.of("Ingenieria de Software", "Inteligencia Artificial");
    }

}
