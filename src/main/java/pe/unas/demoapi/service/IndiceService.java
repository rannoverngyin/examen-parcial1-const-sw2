package pe.unas.demoapi.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Crea los indices simples y compuestos descritos en el paso 5 de la guia,
 * mas el indice adicional del ejercicio aplicado (punto 12).
 */
@Service
public class IndiceService {

    private final JdbcTemplate jdbcTemplate;

    private static final List<String> INDICES = List.of(
            "CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo)",
            "CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id)"
    );

    // Indice adicional para el ejercicio aplicado: FIIS + semestre + nota >= 14
    private static final String INDICE_EJERCICIO_APLICADO =
            "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota)";

    public IndiceService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void crearIndices() {
        INDICES.forEach(jdbcTemplate::execute);
    }

    public void crearIndiceEjercicioAplicado() {
        jdbcTemplate.execute(INDICE_EJERCICIO_APLICADO);
    }

    public List<String> listarIndices() {
        return jdbcTemplate.query(
                "SELECT INDEX_NAME FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_SCHEMA = 'PUBLIC' ORDER BY INDEX_NAME",
                (rs, rowNum) -> rs.getString(1));
    }
}
