package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductoService {
    
    private final List<String> productos = new ArrayList<>();
    public ProductoService (){

        productos.add("laptop");
        productos.add("mouse");

    }

    public List<String> listar(){

        return productos;
    }
    

}
