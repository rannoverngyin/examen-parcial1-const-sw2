package pe.unas.demoapi.application;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ResolucionService {
    public List<String>Listar(){
        return List.of("R-001-2026","R-002-2026");
    }
}
