package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;

import pe.unas.demoapi.application.ReporteService;

public class ReporteController {
    private final ReporteService service;
   public ReporteController(ReporteService service){
    this.service=service;
   }
   @GetMapping("/reporte")
   public String generar(){
    return service.generar();
   }

}
