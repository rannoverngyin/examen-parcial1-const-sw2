package pe.unas.demoapi.presentation;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Arrays;
@RestController


public class ActiveProfileController {
    private final Environment environment;
    public ActiveProfileController(Environment environment) {
        this.environment = environment;
    }
    @GetMapping("/config/active-profile")
    public List<String>perfiles() {
        return List.of(environment.getActiveProfiles());
    }
}