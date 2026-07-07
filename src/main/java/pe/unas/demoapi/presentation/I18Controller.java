package pe.unas.demoapi.presentation;



import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.unas.demoapi.application.MensajeServicio;

@RestController

public class I18Controller {
  private final MensajeServicio service;
  
  public I18Controller(MensajeServicio service){
        this.service=service;
    }

    @GetMapping("/i18n/saludo")

    public String saludo(@RequestParam String lang){
        return service.obtenerMensaje("saludo", lang);
    }

 }

