package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

@Service
public class CalidadService {

    /**
     * Clasifica el nivel de cobertura según el porcentaje.
     *
     * @param porcentaje valor entre 0 y 100
     * @return "ALTA", "MEDIA" o "BAJA"
     * @throws IllegalArgumentException si el porcentaje está fuera de rango
     */
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

    /**
     * Indica si la cobertura es aceptable (>= 70%).
     *
     * @param porcentaje valor entre 0 y 100
     * @return true si es >= 70, false en caso contrario
     * @throws IllegalArgumentException si el porcentaje está fuera de rango
     */
    public boolean esAceptable(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Cobertura inválida");
        }
        return porcentaje >= 70;
    }
}
