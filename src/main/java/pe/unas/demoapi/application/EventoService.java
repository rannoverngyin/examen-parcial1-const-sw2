package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {
    public List<String> listar(){
        return List.of("Hackathon", "Jornada cientifica");
    }
}
