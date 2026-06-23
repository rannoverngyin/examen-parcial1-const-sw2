package pe.unas.demoapi.application;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    public List<String> listar(){
        return List.of("admin","editor");
    }
}
