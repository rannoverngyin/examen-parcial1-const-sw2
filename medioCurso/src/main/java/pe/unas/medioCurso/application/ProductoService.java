package pe.unas.medioCurso.application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<String> productos = new ArrayList<>();

    public ProductoService() {
        productos.add("Teclado");
    }

    public List<String> listar() {
        return productos;
    }

    public int total() {
        return productos.size();
    }
}