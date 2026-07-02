package com.unas.fiis.practica26.infrastructure.persistence;

import com.unas.fiis.practica26.domain.model.Estudiante;
import com.unas.fiis.practica26.domain.repository.EstudianteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EstudianteRepositoryImpl implements EstudianteRepository {

    private final JdbcTemplate jdbcTemplate;

    public EstudianteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void crearTabla() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS estudiantes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    codigo TEXT NOT NULL,
                    nombre TEXT NOT NULL,
                    escuela TEXT NOT NULL
                )
                """);
    }

    @Override
    public void insertarBatch(List<Estudiante> estudiantes) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO estudiantes(codigo, nombre, escuela) VALUES (?, ?, ?)",
                estudiantes,
                1000,
                (ps, est) -> {
                    ps.setString(1, est.getCodigo());
                    ps.setString(2, est.getNombre());
                    ps.setString(3, est.getEscuela());
                }
        );
    }

}
