package com.fiis.unas.application.usecase;

import com.fiis.unas.application.port.DatabasePort;
import com.fiis.unas.domain.vo.BenchmarkResult;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BenchmarkQueriesUseCase {
    private final DatabasePort databasePort;

    public BenchmarkQueriesUseCase(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    public List<BenchmarkResult> execute() {
        System.out.println("=== BENCHMARK: ORM vs SQL directo ===");
        List<BenchmarkResult> results = new ArrayList<>();

        results.add(databasePort.benchmarkFindByCicloOrm(7));
        results.add(databasePort.benchmarkFindByCicloJdbc(7));
        results.add(databasePort.benchmarkCountByDocenteOrm("Mg. Yanac"));
        results.add(databasePort.benchmarkCountByDocenteJdbc("Mg. Yanac"));

        return results;
    }
}
