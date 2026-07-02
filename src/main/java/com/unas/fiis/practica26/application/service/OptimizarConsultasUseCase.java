package com.unas.fiis.practica26.application.service;

import com.unas.fiis.practica26.application.dto.ReporteOptimizacion;
import com.unas.fiis.practica26.domain.model.Medicion;
import com.unas.fiis.practica26.domain.repository.ConsultaRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptimizarConsultasUseCase {

    private static final Map<String, String> CONSULTAS = new LinkedHashMap<>();
    private static final List<String> INDICES = List.of(
            "CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo)",
            "CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id)",
            "CREATE INDEX IF NOT EXISTS idx_matriculas_nota ON matriculas(nota)"
    );

    static {
        CONSULTAS.put("Q1_busqueda_codigo",
                "SELECT * FROM estudiantes WHERE codigo = '202600120'");
        CONSULTAS.put("Q2_matriculas_semestre",
                "SELECT * FROM matriculas WHERE semestre = '2026-I'");
        CONSULTAS.put("Q3_join_escuela_semestre",
                "SELECT e.codigo, e.nombre, m.semestre, m.nota " +
                "FROM estudiantes e JOIN matriculas m ON e.id = m.estudiante_id " +
                "WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I'");
        CONSULTAS.put("Q4_fiis_nota_mayor_14",
                "SELECT e.codigo, e.nombre, m.nota " +
                "FROM estudiantes e JOIN matriculas m ON e.id = m.estudiante_id " +
                "WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I' AND m.nota >= 14");
    }

    private final ConsultaRunner consultaRunner;

    public OptimizarConsultasUseCase(ConsultaRunner consultaRunner) {
        this.consultaRunner = consultaRunner;
    }

    public List<Medicion> medirAntes() {
        return medir();
    }

    public ReporteOptimizacion optimizar() {
        List<Medicion> antes = medir();

        List<String> indicesCreados = new ArrayList<>();
        for (String idx : INDICES) {
            consultaRunner.ejecutarDDL(idx);
            indicesCreados.add(idx.substring(idx.lastIndexOf("idx_")));
        }

        List<Medicion> despues = medir();

        ReporteOptimizacion reporte = new ReporteOptimizacion(antes, despues, indicesCreados);
        reporte.setMensaje("Optimización completada: " + INDICES.size() + " índices creados");
        return reporte;
    }

    public List<Medicion> medirDespues() {
        return medir();
    }

    private List<Medicion> medir() {
        List<Medicion> resultados = new ArrayList<>();
        for (Map.Entry<String, String> entry : CONSULTAS.entrySet()) {
            Medicion m = consultaRunner.ejecutarConMedicion(entry.getKey(), entry.getValue());
            String plan = consultaRunner.obtenerPlan(entry.getValue());
            m.setPlan(plan);
            resultados.add(m);
        }
        return resultados;
    }

}
