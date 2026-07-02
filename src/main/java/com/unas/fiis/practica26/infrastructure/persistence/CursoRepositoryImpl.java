package com.unas.fiis.practica26.infrastructure.persistence;

import com.unas.fiis.practica26.domain.model.Curso;
import com.unas.fiis.practica26.domain.repository.CursoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CursoRepositoryImpl implements CursoRepository {

    private final JdbcTemplate jdbcTemplate;

    public CursoRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void crearTabla() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS cursos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    ciclo INTEGER NOT NULL
                )
                """);
    }

    @Override
    public void insertarBatch(List<Curso> cursos) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO cursos(nombre, ciclo) VALUES (?, ?)",
                cursos,
                100,
                (ps, c) -> {
                    ps.setString(1, c.getNombre());
                    ps.setInt(2, c.getCiclo());
                }
        );
    }

}
