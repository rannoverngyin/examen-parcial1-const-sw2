package pe.unas.demoapi.application;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public String version() {
        return version;
    }

}
