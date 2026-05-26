package pe.unas.demoapi.application;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProyectoService {
    public List<String> listar(){
        return List.of("SGI-UNAS","Portal resolociones");
    }
}
