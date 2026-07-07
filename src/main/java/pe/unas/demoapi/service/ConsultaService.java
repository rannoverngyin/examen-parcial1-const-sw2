package pe.unas.demoapi.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.unas.demoapi.dto.ResultadoConsulta;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Equivalente Java/Spring Boot de medir_consultas.py y optimizar_consultas.py.
 * El mismo metodo se usa antes y despues de crear los indices: el estado
 * "antes" o "despues" depende unicamente de si los indices ya existen en la
 * base de datos al momento de ejecutar.
 */
@Service
public class ConsultaService {

    private final JdbcTemplate jdbcTemplate;

    public ConsultaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /** Devuelve el mapa nombre->SQL de las 3 consultas base de la guia. */
    public Map<String, String> consultasBase() {
        Map<String, String> consultas = new LinkedHashMap<>();

        consultas.put("Q1_busqueda_codigo", """
                SELECT * FROM estudiantes WHERE codigo = '202600120'
                """);

        consultas.put("Q2_matriculas_semestre", """
                SELECT * FROM matriculas WHERE semestre = '2026-I'
                """);

        consultas.put("Q3_join_escuela_semestre", """
                SELECT e.codigo, e.nombre, m.semestre, m.nota
                FROM estudiantes e
                JOIN matriculas m ON e.id = m.estudiante_id
                WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I'
                """);

        return consultas;
    }

    /** Consulta del ejercicio aplicado (punto 12 de la guia). */
    public Map<String, String> consultaEjercicioAplicado() {
        Map<String, String> consultas = new LinkedHashMap<>();
        consultas.put("Q4_fiis_nota_mayor_igual_14", """
                SELECT e.codigo, e.nombre, m.nota
                FROM estudiantes e
                JOIN matriculas m ON e.id = m.estudiante_id
                WHERE e.escuela = 'FIIS'
                AND m.semestre = '2026-I'
                AND m.nota >= 14
                """);
        return consultas;
    }

    public List<ResultadoConsulta> ejecutar(Map<String, String> consultas) {
        return consultas.entrySet().stream()
                .map(entry -> ejecutarUna(entry.getKey(), entry.getValue()))
                .toList();
    }

    private ResultadoConsulta ejecutarUna(String nombre, String sql) {
        long inicio = System.nanoTime();
        List<Map<String, Object>> filas = jdbcTemplate.queryForList(sql);
        long fin = System.nanoTime();
        double segundos = (fin - inicio) / 1_000_000_000.0;

        List<String> plan = jdbcTemplate.query("EXPLAIN " + sql,
                (rs, rowNum) -> rs.getString(1));

        return new ResultadoConsulta(nombre, filas.size(), segundos, plan);
    }
}
