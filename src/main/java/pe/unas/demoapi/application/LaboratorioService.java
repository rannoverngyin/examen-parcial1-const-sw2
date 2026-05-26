package pe.unas.demoapi.application;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LaboratorioService {
    public List<String> Listar(){
        return List.of("Lab Software", "Lab Redes");
    }
}


