package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.PrecioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class PrecioController {
    private final PrecioService service;

    public PrecioController(PrecioService service) {
        this.service = service;
    }

    @GetMapping("/precio-final")
    public double calcular(@RequestParam double precio) {
//    public double calcular(@RequestParam double precio, @RequestParam(required = false) String variante){
//      if (variante != null && !variante.isBlank()) {
//    return service.calcularPorVariante(precio, variante);
//}

//
    
        return service.calcularPrecioFinal(precio);
    }

    @GetMapping("/variante-activa")
    public String variante() {
        return service.obtenerVarianteActiva();
    }

}
