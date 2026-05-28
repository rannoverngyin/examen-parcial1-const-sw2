package pe.unas.demoapi.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class LaboratorioService {

    public List<String> x = new ArrayList<>();

    public LaboratorioService() {
        x.add("A");
        x.add("B");
    }

    public List<String> listarLab() {
        return x;
    }

    public void elimnarLab(String nombre) {
        x.remove(nombre);
    }

    public void agregarLab(String nombre) {
        x.add(nombre);
    }

    public int total() {
        return x.size();
    }
}
