package pe.unas.demoapi14.application;

import org.springframework.stereotype.Service;

@Service
public class CalidadService {
    public String clasificarCobertura(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Cobertura invalida");
        }
        if (porcentaje >= 80) {
            return "ALTA";
        }
        if (porcentaje >= 50) {
            return "MEDIA";
        }
        return "BAJA";
    }
    public boolean esAceptable(int porcentaje) {
    if (porcentaje < 0 || porcentaje > 100) {
        throw new IllegalArgumentException("Cobertura invalida");
    }
    return porcentaje >= 70;
    }

}

