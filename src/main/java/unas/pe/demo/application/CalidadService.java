package unas.pe.demo.application;

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

    // Ejercicio aplicado: Retorna true si es mayor o igual a 70%
    public boolean esAceptable(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Cobertura inválida");
        }
        return porcentaje >= 70;
    }
}