package com.fiis.unas.infrastructure.persistence.jdbc;

import com.fiis.unas.domain.entity.Curso;
import com.fiis.unas.domain.repository.CursoRepository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCursoRepository implements CursoRepository {

    private final DataSource dataSource;

    public JdbcCursoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Curso> findByCiclo(Integer ciclo) {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE ciclo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ciclo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cursos.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar por ciclo", e);
        }
        return cursos;
    }

    @Override
    public long countByDocente(String docente) {
        String sql = "SELECT COUNT(*) FROM cursos WHERE docente = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, docente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar por docente", e);
        }
        return 0;
    }

    private Curso mapRow(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId(rs.getLong("id"));
        c.setCodigo(rs.getString("codigo"));
        c.setNombre(rs.getString("nombre"));
        c.setCiclo(rs.getInt("ciclo"));
        c.setCreditos(rs.getInt("creditos"));
        c.setDocente(rs.getString("docente"));
        return c;
    }
}
