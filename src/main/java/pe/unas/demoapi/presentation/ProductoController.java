package pe.unas.demoapi.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.ProductoService;

@RestController
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping("/productos")
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping("/productos")
    public ResponseEntity<String> agregar(@RequestParam String nombre) {
        service.agregar(nombre);
        return ResponseEntity.ok("Producto agregado");
    }

    @DeleteMapping("/productos")
    public ResponseEntity<String> eliminar(@RequestParam String nombre) {
        service.eliminar(nombre);
        return ResponseEntity.ok("Producto eliminado");
    }

    @GetMapping("/productos/total")
    public ResponseEntity<Integer> total() {
        return ResponseEntity.ok(service.total());
    }

    @GetMapping("/productos/existe")
    public ResponseEntity<Boolean> existe(@RequestParam String nombre) {
        return ResponseEntity.ok(service.existe(nombre));
    }
}
