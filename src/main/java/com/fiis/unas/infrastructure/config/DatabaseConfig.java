package com.fiis.unas.infrastructure.config;

import com.fiis.unas.application.port.DatabasePort;
import com.fiis.unas.domain.entity.Curso;
import com.fiis.unas.domain.vo.BenchmarkResult;
import com.fiis.unas.infrastructure.persistence.jpa.JpaCursoRepository;
import com.fiis.unas.infrastructure.persistence.jdbc.JdbcCursoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseConfig implements DatabasePort {

    private final DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    public DatabaseConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void setupDatabase() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS cursos");
            stmt.execute("CREATE TABLE cursos (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "codigo VARCHAR(255), " +
                    "nombre VARCHAR(255), " +
                    "ciclo INTEGER, " +
                    "creditos INTEGER, " +
                    "docente VARCHAR(255))");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_cursos_ciclo ON cursos(ciclo)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_cursos_docente ON cursos(docente)");

            String[][] cursosBase = {
                    {"IS040701", "Arquitectura de Software", "7", "4", "Dr. Garc\u00eda"},
                    {"IS040703", "Construcci\u00f3n de Software II", "7", "5", "Mg. Yanac"},
                    {"IS040602", "Anal\u00edtica de Datos", "6", "4", "Dra. R\u00edos"},
                    {"IS040801", "Calidad de Software", "8", "4", "Mg. Torres"}
            };

            String insertSQL = "INSERT INTO cursos (codigo, nombre, ciclo, creditos, docente) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                for (int i = 0; i < 5000; i++) {
                    String[] base = cursosBase[i % 4];
                    pstmt.setString(1, base[0] + "-" + i);
                    pstmt.setString(2, base[1]);
                    pstmt.setInt(3, Integer.parseInt(base[2]));
                    pstmt.setInt(4, Integer.parseInt(base[3]));
                    pstmt.setString(5, base[4]);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en setupDatabase", e);
        }
    }

    @Override
    public BenchmarkResult benchmarkFindByCicloOrm(Integer ciclo) {
        JpaCursoRepository repo = new JpaCursoRepository(entityManager);
        long start = System.nanoTime();
        List<Curso> result = repo.findByCiclo(ciclo);
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        return new BenchmarkResult("ORM: cursos ciclo " + ciclo, result.size(), ms);
    }

    @Override
    public BenchmarkResult benchmarkFindByCicloJdbc(Integer ciclo) {
        JdbcCursoRepository repo = new JdbcCursoRepository(dataSource);
        long start = System.nanoTime();
        List<Curso> result = repo.findByCiclo(ciclo);
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        return new BenchmarkResult("SQL: cursos ciclo " + ciclo, result.size(), ms);
    }

    @Override
    public BenchmarkResult benchmarkCountByDocenteOrm(String docente) {
        JpaCursoRepository repo = new JpaCursoRepository(entityManager);
        long start = System.nanoTime();
        long count = repo.countByDocente(docente);
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        return new BenchmarkResult("ORM: contar " + docente, (int) count, ms);
    }

    @Override
    public BenchmarkResult benchmarkCountByDocenteJdbc(String docente) {
        JdbcCursoRepository repo = new JdbcCursoRepository(dataSource);
        long start = System.nanoTime();
        long count = repo.countByDocente(docente);
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        return new BenchmarkResult("SQL: contar " + docente, (int) count, ms);
    }

    @Override
    public List<String> explainQueryPlan(String sql) {
        List<String> plan = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("EXPLAIN ANALYZE " + sql)) {
            while (rs.next()) {
                plan.add(rs.getString(1));
            }
        } catch (SQLException e) {
            plan.add("Error: " + e.getMessage());
        }
        return plan;
    }

    @Override
    public void createIndex(String indexName, String table, String column) {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE INDEX IF NOT EXISTS " + indexName + " ON " + table + "(" + column + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear \u00edndice", e);
        }
    }
}
