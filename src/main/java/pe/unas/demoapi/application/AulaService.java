package pe.unas.demoapi.application;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AulaService {
    public List<String> listar() {
        return List.of("Aula 101", "Aula 102");
    }
    
}
