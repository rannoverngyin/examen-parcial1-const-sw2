package com.examenn.examen.presentation;

import org.springframework.web.bind.annotation.RestController;

import com.examenn.examen.application.LaboratorioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;



@RestController
public class LaboratorioController {
    private final LaboratorioService service;
    public LaboratorioController(LaboratorioService service) {
        this.service = service;
    }
    @GetMapping("/Laboratorios")
    public List<String> listar() {
        return service.listar();

    }
    
}
