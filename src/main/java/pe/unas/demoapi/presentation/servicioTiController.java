package pe.unas.demoapi.presentation;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.servicioTiService;
@RestController
@RequestMapping ("/servicios-TI")
public class servicioTiController {
 private final servicioTiService service;
 public servicioTiController (servicioTiService service) {
    this.service = service;
 }
 @GetMapping 
 public List<String> listar(){
    return service.listar();
 }


    
}
