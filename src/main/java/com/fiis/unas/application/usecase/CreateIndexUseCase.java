package com.fiis.unas.application.usecase;

import com.fiis.unas.application.port.DatabasePort;
import org.springframework.stereotype.Service;

@Service
public class CreateIndexUseCase {
    private final DatabasePort databasePort;

    public CreateIndexUseCase(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    public void execute() {
        System.out.println("=== Creando \u00edndice en docente ===");
        databasePort.createIndex("idx_cursos_docente", "cursos", "docente");
        System.out.println("\u00cdndice idx_cursos_docente creado.\n");
    }
}
