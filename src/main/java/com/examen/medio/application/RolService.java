package com.examen.medio.application;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RolService {
    public List<String> listar() {
        return List.of("ADMIN","DOCENTE");
    }
}
