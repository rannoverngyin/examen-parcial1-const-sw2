package com.unas.fiis.practica26.infrastructure.persistence;

import com.unas.fiis.practica26.domain.model.Matricula;
import com.unas.fiis.practica26.domain.repository.MatriculaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatriculaRepositoryImpl implements MatriculaRepository {

    private final JdbcTemplate jdbcTemplate;

    public MatriculaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void crearTabla() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS matriculas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    estudiante_id INTEGER NOT NULL,
                    curso_id INTEGER NOT NULL,
                    semestre TEXT NOT NULL,
                    nota REAL NOT NULL,
                    FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id),
                    FOREIGN KEY(curso_id) REFERENCES cursos(id)
                )
                """);
    }

    @Override
    public void insertarBatch(List<Matricula> matriculas) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO matriculas(estudiante_id, curso_id, semestre, nota) VALUES (?, ?, ?, ?)",
                matriculas,
                1000,
                (ps, m) -> {
                    ps.setLong(1, m.getEstudianteId());
                    ps.setLong(2, m.getCursoId());
                    ps.setString(3, m.getSemestre());
                    ps.setDouble(4, m.getNota());
                }
        );
    }

}
