package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LineaService {

    public List<String> listar() {
        return List.of("Linea 1", "Linea 2", "Linea 3");
    }
}
