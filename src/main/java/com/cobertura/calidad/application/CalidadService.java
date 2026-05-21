package com.cobertura.calidad.application;

import org.springframework.stereotype.Service;

@Service
public class CalidadService {

    public String clasificarCobertura(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Cobertura inválida");
        }
        if (porcentaje >= 80) {
            return "ALTA";
        }
        if (porcentaje >= 50) {
            return "MEDIA";
        }
        return "BAJA";
    }
}
