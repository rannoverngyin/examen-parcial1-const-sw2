package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.BenchmarkService;

import java.util.List;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final BenchmarkService benchmarkService;

    public BenchmarkController(
            BenchmarkService benchmarkService
    ) {
        this.benchmarkService = benchmarkService;
    }

    @GetMapping("/baseline")
    public List<String> baseline() {
        return benchmarkService.consultaBaseline();
    }

    @GetMapping("/optimizado")
    public List<String> optimizado() {
        return benchmarkService.consultaOptimizada();
    }
}