package pe.unas.demoapi.application;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class servicioTiService {
     public List<String> listar () {
        return List.of ("soporte","redes");
     }
    
}
