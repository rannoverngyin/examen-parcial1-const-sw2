package pe.unas.demoapi20.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @GetMapping("/config/info")
    public String info() {
        return "API demoapi20 ejecutándose en Docker";
    }
}