package pe.unas.demoapi.application;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DocenteService {
    public List<String> Listar() {
        return List.of("Dr.Garcia", "Mg.Yanac");
    }
}
