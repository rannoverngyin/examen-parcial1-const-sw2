package pe.unas.demoapi.Application;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class FacultadService {
    public List<String> listar(){
    return List.of("Fiis","Agronomia");}

}
