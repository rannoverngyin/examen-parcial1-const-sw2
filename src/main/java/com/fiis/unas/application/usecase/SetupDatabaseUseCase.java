package com.fiis.unas.application.usecase;

import com.fiis.unas.application.port.DatabasePort;
import org.springframework.stereotype.Service;

@Service
public class SetupDatabaseUseCase {
    private final DatabasePort databasePort;

    public SetupDatabaseUseCase(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    public void execute() {
        System.out.println("=== SETUP: Creando base de datos e insertando 5000 registros ===");
        databasePort.setupDatabase();
        System.out.println("Base de datos lista.\n");
    }
}
