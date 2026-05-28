package pe.unas.demoapi.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/productos")
public class ProductoController {
@Autowired
private ProductoService service; 

@DeleteMapping
public String eliminar(@RequestParam String nombre){
    service.eliminar(nombre);
    return "Producto eliminado";
}

@GetMapping("/listar")
public List<String>listar() {
    return service.listar();
}



}
