package com.fiis.unas.presentation;

import com.fiis.unas.application.usecase.*;
import com.fiis.unas.domain.vo.BenchmarkResult;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.fiis.unas")
@EntityScan("com.fiis.unas.infrastructure.persistence.jpa")
public class Main implements CommandLineRunner {

    private final SetupDatabaseUseCase setupDatabaseUseCase;
    private final BenchmarkQueriesUseCase benchmarkQueriesUseCase;
    private final ExplainQueryUseCase explainQueryUseCase;
    private final CreateIndexUseCase createIndexUseCase;
    private final ReportGenerator reportGenerator;

    public Main(SetupDatabaseUseCase setupDatabaseUseCase,
                BenchmarkQueriesUseCase benchmarkQueriesUseCase,
                ExplainQueryUseCase explainQueryUseCase,
                CreateIndexUseCase createIndexUseCase,
                ReportGenerator reportGenerator) {
        this.setupDatabaseUseCase = setupDatabaseUseCase;
        this.benchmarkQueriesUseCase = benchmarkQueriesUseCase;
        this.explainQueryUseCase = explainQueryUseCase;
        this.createIndexUseCase = createIndexUseCase;
        this.reportGenerator = reportGenerator;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        setupDatabaseUseCase.execute();

        List<BenchmarkResult> results = benchmarkQueriesUseCase.execute();

        System.out.println("\nResultados del Benchmark:");
        System.out.println("+--------------------------------------+-------+------------+");
        System.out.println("| Consulta                             | Filas | Tiempo(ms) |");
        System.out.println("+--------------------------------------+-------+------------+");
        for (BenchmarkResult r : results) {
            System.out.printf("| %-36s | %5d | %10.4f |%n",
                    r.getConsulta(), r.getFilas(), r.getTiempoMs());
        }
        System.out.println("+--------------------------------------+-------+------------+");

        List<String> plan = explainQueryUseCase.execute("SELECT * FROM cursos WHERE ciclo = 7");
        System.out.println("Plan de ejecución:");
        for (String line : plan) {
            System.out.println("  " + line);
        }
        System.out.println();

        createIndexUseCase.execute();

        reportGenerator.generateReport(results, plan);
    }
}
