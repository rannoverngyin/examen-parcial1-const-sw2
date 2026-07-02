package com.unas.fiis.practica26.application.service;

import com.unas.fiis.practica26.domain.model.Curso;
import com.unas.fiis.practica26.domain.model.Estudiante;
import com.unas.fiis.practica26.domain.model.Matricula;
import com.unas.fiis.practica26.domain.repository.ConsultaRunner;
import com.unas.fiis.practica26.domain.repository.CursoRepository;
import com.unas.fiis.practica26.domain.repository.EstudianteRepository;
import com.unas.fiis.practica26.domain.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InicializarBaseDatosUseCase {

    private static final List<String> ESCUELAS = List.of("FIIS", "Agronomia", "Zootecnia", "Ambiental");
    private static final List<String> SEMESTRES = List.of("2025-I", "2025-II", "2026-I");

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;
    private final ConsultaRunner consultaRunner;

    public InicializarBaseDatosUseCase(EstudianteRepository estudianteRepository,
                                       CursoRepository cursoRepository,
                                       MatriculaRepository matriculaRepository,
                                       ConsultaRunner consultaRunner) {
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
        this.matriculaRepository = matriculaRepository;
        this.consultaRunner = consultaRunner;
    }

    public String ejecutar() {
        consultaRunner.ejecutarDDL("DROP TABLE IF EXISTS matriculas");
        consultaRunner.ejecutarDDL("DROP TABLE IF EXISTS cursos");
        consultaRunner.ejecutarDDL("DROP TABLE IF EXISTS estudiantes");

        estudianteRepository.crearTabla();
        cursoRepository.crearTabla();
        matriculaRepository.crearTabla();

        Random random = new Random();

        List<Estudiante> estudiantes = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            estudiantes.add(new Estudiante(null,
                    String.format("2026%05d", i),
                    "Estudiante " + i,
                    ESCUELAS.get(random.nextInt(ESCUELAS.size()))));
        }
        estudianteRepository.insertarBatch(estudiantes);

        List<Curso> cursos = List.of(
                new Curso(null, "Construccion de Software II", 7),
                new Curso(null, "Arquitectura de Software", 6),
                new Curso(null, "Base de Datos", 4),
                new Curso(null, "Inteligencia Artificial", 8)
        );
        cursoRepository.insertarBatch(cursos);

        List<Matricula> matriculas = new ArrayList<>();
        for (int i = 0; i < 80000; i++) {
            matriculas.add(new Matricula(null,
                    (long) (random.nextInt(5000) + 1),
                    (long) (random.nextInt(4) + 1),
                    SEMESTRES.get(random.nextInt(SEMESTRES.size())),
                    Math.round(random.nextDouble() * 2000.0) / 100.0));
        }
        matriculaRepository.insertarBatch(matriculas);

        return "Base de datos inicializada: 5000 estudiantes, 4 cursos, 80000 matriculas";
    }

}
