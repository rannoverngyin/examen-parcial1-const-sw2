package pe.unas.demoapi.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ProductoService {

    public List<String> pr = new ArrayList<>();

    public ProductoService() {
        pr.add("Laptop");
        pr.add("Pc");
    }

    public List<String> listar() {
        return pr;
    }

    public int total() {
        return pr.size();
    }

}
