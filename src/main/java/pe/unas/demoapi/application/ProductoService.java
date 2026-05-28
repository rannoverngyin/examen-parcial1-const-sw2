package pe.unas.demoapi.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private List<String>productos=new ArrayList<>();

    public ProductoService(){
        productos.add(new String("laptop"));
        productos.add(new String("Mouse"));
        productos.add(new String("Teclado"));
    }

    public List<String>listar(){
        return productos;
    }

    public void agregar(String nombre){
        productos.add(nombre);
    }

    public int contar(){
        return productos.size();
    }

    public void eliminar(String nombre){
            productos.remove(nombre);
    }


}
