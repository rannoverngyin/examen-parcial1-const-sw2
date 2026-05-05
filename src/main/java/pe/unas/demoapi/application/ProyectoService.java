package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProyectoService {
    public List<String> Listar() {
        return List.of("SGI-UNAS", "Portal Resoluciones");
    }


}
