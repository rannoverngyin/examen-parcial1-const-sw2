package com.example.demos10.presentation;

import org.springframework.web.bind.annotation.*;
import com.example.demos10.application.EquipoService;
import java.util.List;
@RestController
public class EquipoController {
    private final EquipoService service;
    public EquipoController(EquipoService service){
        this.service = service ;
    }
    @GetMapping("/equipos")
    public List<String> Listar(){
        return service.listar();
    }
}
