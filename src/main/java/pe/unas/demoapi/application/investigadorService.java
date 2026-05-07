package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class investigadorService {
    public List<String> Listar(){
        return List.of("Luis Gorpa","Rannoverng Yanac");
    }
}
