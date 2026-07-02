package com.unas.fiis.practica26.infrastructure.persistence;

import com.unas.fiis.practica26.domain.model.Medicion;
import com.unas.fiis.practica26.domain.repository.ConsultaRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaRunnerImpl implements ConsultaRunner {

    private final JdbcTemplate jdbcTemplate;

    public ConsultaRunnerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Medicion ejecutarConMedicion(String nombre, String sql) {
        long inicio = System.nanoTime();
        var resultados = jdbcTemplate.queryForList(sql);
        long fin = System.nanoTime();
        double tiempoSegundos = (fin - inicio) / 1_000_000_000.0;
        return new Medicion(nombre, sql, resultados.size(), tiempoSegundos, "");
    }

    @Override
    public String obtenerPlan(String sql) {
        try {
            return jdbcTemplate.query("EXPLAIN QUERY PLAN " + sql,
                (java.sql.ResultSet rs) -> {
                    StringBuilder plan = new StringBuilder();
                    while (rs.next()) {
                        if (!plan.isEmpty()) plan.append(" | ");
                        String detail = rs.getString("detail");
                        if (detail != null) plan.append(detail);
                    }
                    return plan.toString();
                });
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @Override
    public void ejecutarDDL(String sql) {
        jdbcTemplate.execute(sql);
    }

}
