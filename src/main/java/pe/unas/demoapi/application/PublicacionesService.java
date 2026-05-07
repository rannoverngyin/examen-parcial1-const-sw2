package pe.unas.demoapi.application;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PublicacionesService {
    public List<String> Listar(){
        return List.of("Articulo IA", "Articulo Software");
    }
}
