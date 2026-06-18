package unas.pe.demo.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class DeploymentValidationService {

    @Value("${app.environment}")
    private String environment;

    @Value("${app.version}")
    private String version;

    @Value("${app.message}")
    private String message;

    public Map<String, String> config() {
        return Map.of(
                "environment", environment,
                "version", version,
                "message", message
        );
    }

    public Map<String, String> health() {
        return Map.of(
                "status", "OK",
                "environment", environment,
                "checkedAt", LocalDateTime.now().toString()
        );
    }

    // AGREGADO POR EJERCICIO APLICADO (Punto 18)
    public String version() {
        return this.version;
    }
}