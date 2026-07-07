package pe.unas.demoapi.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.unas.demoapi.model.Curso;

@Repository
public class CursoJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Curso> CURSO_ROW_MAPPER = (rs, rowNum) -> new Curso(
            rs.getString("codigo"),
            rs.getString("nombre"),
            rs.getInt("ciclo"),
            rs.getInt("creditos"),
            rs.getString("docente")
    );

    public CursoJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SQL directo equivalente a: SELECT * FROM cursos WHERE ciclo = ?
    public List<Curso> listarPorCiclo(int ciclo) {
        return jdbcTemplate.query(
                "SELECT codigo, nombre, ciclo, creditos, docente FROM cursos WHERE ciclo = ?",
                CURSO_ROW_MAPPER,
                ciclo
        );
    }

    // SQL directo equivalente a: SELECT COUNT(*) FROM cursos WHERE docente = ?
    public long contarPorDocente(String docente) {
        Long total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cursos WHERE docente = ?",
                Long.class,
                docente
        );
        return total == null ? 0L : total;
    }

    // EXPLAIN de H2 (equivalente a EXPLAIN QUERY PLAN de SQLite)
    // Se usa parametro ligado (?) en vez de concatenar texto, para no modelar
    // un patron vulnerable a inyeccion SQL (ver pregunta de reflexion 5).
    public List<Map<String, Object>> explicarConsultaPorCiclo(int ciclo) {
        return jdbcTemplate.queryForList(
                "EXPLAIN SELECT * FROM cursos WHERE ciclo = ?",
                ciclo
        );
    }

    public void crearIndiceDocente() {
        jdbcTemplate.execute("CREATE INDEX IF NOT EXISTS idx_cursos_docente ON cursos(docente)");
    }
}
