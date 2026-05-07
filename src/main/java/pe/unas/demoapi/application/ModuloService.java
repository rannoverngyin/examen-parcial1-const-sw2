package pe.unas.demoapi.application;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuloService {
    public List<String> listar(){
        return List.of("usuarios", "reportes");
    }
}
