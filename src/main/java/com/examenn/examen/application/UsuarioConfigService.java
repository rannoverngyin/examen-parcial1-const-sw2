package com.examenn.examen.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UsuarioConfigService {
    @Value("${app.maxUsuarios:50}")
    private int maxUsuarios;
    public int obtenerMaxUsuarios() {
        return maxUsuarios;
    }
}
