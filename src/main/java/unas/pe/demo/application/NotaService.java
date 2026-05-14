
//
package unas.pe.demo.application;

import org.springframework.stereotype.Service;

/*  

Paso B: Fase GREEN (Código mínimo para pasar)

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
*/

// Paso C: Fase REFACTOR (Mejorar el diseño)


@Service
public class NotaService {
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 20.0;
    private static final double NOTA_APROBATORIA = 10.5;

    public double promedio(double nota1, double nota2) {
        validarNota(nota1);
        validarNota(nota2);
        return (nota1 + nota2) / 2.0;
    }

    public boolean estaAprobado(double promedio) {
        return promedio >= NOTA_APROBATORIA;
    }

    private void validarNota(double nota) {
        if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 20");
        }
    }

    // Paso F: complementamos la logica del paso E
    public double promedioPonderado(double practica, double examen) {
    validarNota(practica);
    validarNota(examen);
    return (practica * 0.40) + (examen * 0.60);
}
}


