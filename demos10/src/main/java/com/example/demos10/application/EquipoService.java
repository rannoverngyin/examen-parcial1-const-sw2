package com.example.demos10.application;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EquipoService {
    public List<String> listar() {
        return List.of("PC-LAB01", "PC-LAB02");
    } 
}
