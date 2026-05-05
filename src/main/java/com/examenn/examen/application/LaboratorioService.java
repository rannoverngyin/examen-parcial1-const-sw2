package com.examenn.examen.application;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LaboratorioService {
    public List<String> listar(){
        return List.of("Lab Redes", "Lab Software");
    }
}
 