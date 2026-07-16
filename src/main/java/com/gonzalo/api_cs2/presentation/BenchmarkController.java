package com.gonzalo.api_cs2.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gonzalo.api_cs2.application.BenchmarkService;
import java.util.List;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final BenchmarkService service;

    public BenchmarkController(BenchmarkService service) {
        this.service = service;
    }

    @GetMapping("/baseline")
    public List<String> baseline() {
        return service.consultaBaseline();
    }

    @GetMapping("/optimizado")
    public List<String> optimizado() {
        return service.consultaOptimizada();
    }
}
