package com.examen.medio.presentation;

import org.springframework.web.bind.annotation.RestController;

import com.examen.medio.application.RolService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class RolController {
    private final RolService service;
    public RolController(RolService service){
        this.service = service;
    }
    @GetMapping("/roles")
    public List<String> listar(){
        return service.listar();
    }
    }
