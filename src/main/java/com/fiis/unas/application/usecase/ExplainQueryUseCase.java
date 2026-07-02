package com.fiis.unas.application.usecase;

import com.fiis.unas.application.port.DatabasePort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExplainQueryUseCase {
    private final DatabasePort databasePort;

    public ExplainQueryUseCase(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    public List<String> execute(String sql) {
        System.out.println("=== EXPLAIN QUERY PLAN ===");
        return databasePort.explainQueryPlan(sql);
    }
}
