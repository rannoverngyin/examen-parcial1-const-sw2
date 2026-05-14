package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

@Service
public class NotaService {

    public double promedio(double nota1, double nota2) {
        validarNota(nota1);
        validarNota(nota2);
        return (nota1 + nota2) / 2.0;
    }

    public boolean estaAprobado(double promedio) {
        return promedio >= 10.5;
    }

    private void validarNota(double nota) {
        if (nota < 0 || nota > 20) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 20");
        }
    }
}
