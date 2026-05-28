package pe.unas.demoapi.application;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private final List <String> productos = new   CopyOnWriteArrayList<>();
    public void agregar(String nombre)
    {

        productos.add(nombre);
    }
    public List<String> listar()
    {
        return productos;
    }
}
