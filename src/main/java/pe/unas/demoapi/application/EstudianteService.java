package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstudianteService {
    public List<String> listar(){
        return List.of ("Ana", "Carlos");
    }
}

