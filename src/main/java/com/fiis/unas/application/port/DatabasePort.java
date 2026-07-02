package com.fiis.unas.application.port;

import com.fiis.unas.domain.vo.BenchmarkResult;
import java.util.List;

public interface DatabasePort {
    void setupDatabase();
    BenchmarkResult benchmarkFindByCicloOrm(Integer ciclo);
    BenchmarkResult benchmarkFindByCicloJdbc(Integer ciclo);
    BenchmarkResult benchmarkCountByDocenteOrm(String docente);
    BenchmarkResult benchmarkCountByDocenteJdbc(String docente);
    List<String> explainQueryPlan(String sql);
    void createIndex(String indexName, String table, String column);
}
