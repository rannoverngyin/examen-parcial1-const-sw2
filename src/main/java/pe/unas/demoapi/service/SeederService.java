package pe.unas.demoapi.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Equivalente Java/Spring Boot de crear_bd.py.
 * Crea las tablas estudiantes, cursos, matriculas y genera datos de prueba:
 * 5000 estudiantes, 4 cursos y 80000 matriculas.
 */
@Service
public class SeederService {

    private final JdbcTemplate jdbcTemplate;
    private final Random random = new Random();

    private static final List<String> ESCUELAS = List.of("FIIS", "Agronomia", "Zootecnia", "Ambiental");
    private static final List<String> SEMESTRES = List.of("2025-I", "2025-II", "2026-I");
    private static final List<Object[]> CURSOS = List.of(
            new Object[]{"Construccion de Software II", 7},
            new Object[]{"Arquitectura de Software", 6},
            new Object[]{"Base de Datos", 4},
            new Object[]{"Inteligencia Artificial", 8}
    );

    public SeederService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void crearBaseDeDatos() {
        eliminarTablas();
        crearTablas();
        insertarEstudiantes(5000);
        insertarCursos();
        insertarMatriculas(80000);
    }

    private void eliminarTablas() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS matriculas");
        jdbcTemplate.execute("DROP TABLE IF EXISTS cursos");
        jdbcTemplate.execute("DROP TABLE IF EXISTS estudiantes");
    }

    private void crearTablas() {
        jdbcTemplate.execute("""
                CREATE TABLE estudiantes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    codigo VARCHAR(20) NOT NULL,
                    nombre VARCHAR(100) NOT NULL,
                    escuela VARCHAR(50) NOT NULL
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE cursos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    ciclo INT NOT NULL
                )
                """);

        jdbcTemplate.execute("""
                CREATE TABLE matriculas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    estudiante_id INT NOT NULL,
                    curso_id INT NOT NULL,
                    semestre VARCHAR(10) NOT NULL,
                    nota DOUBLE NOT NULL,
                    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id),
                    FOREIGN KEY (curso_id) REFERENCES cursos(id)
                )
                """);
    }

    private void insertarEstudiantes(int cantidad) {
        String sql = "INSERT INTO estudiantes (codigo, nombre, escuela) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int numero = i + 1;
                ps.setString(1, String.format("2026%05d", numero));
                ps.setString(2, "Estudiante " + numero);
                ps.setString(3, ESCUELAS.get(random.nextInt(ESCUELAS.size())));
            }

            @Override
            public int getBatchSize() {
                return cantidad;
            }
        });
    }

    private void insertarCursos() {
        String sql = "INSERT INTO cursos (nombre, ciclo) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] curso = CURSOS.get(i);
                ps.setString(1, (String) curso[0]);
                ps.setInt(2, (Integer) curso[1]);
            }

            @Override
            public int getBatchSize() {
                return CURSOS.size();
            }
        });
    }

    private void insertarMatriculas(int cantidad) {
        String sql = "INSERT INTO matriculas (estudiante_id, curso_id, semestre, nota) VALUES (?, ?, ?, ?)";
        int lote = 1000;
        for (int inicio = 0; inicio < cantidad; inicio += lote) {
            int tamanoLote = Math.min(lote, cantidad - inicio);
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, random.nextInt(5000) + 1);
                    ps.setInt(2, random.nextInt(4) + 1);
                    ps.setString(3, SEMESTRES.get(random.nextInt(SEMESTRES.size())));
                    ps.setDouble(4, Math.round(random.nextDouble() * 2000) / 100.0);
                }

                @Override
                public int getBatchSize() {
                    return tamanoLote;
                }
            });
        }
    }
}
