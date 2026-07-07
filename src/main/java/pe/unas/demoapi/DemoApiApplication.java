package pe.unas.demoapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pe.unas.demoapi.service.BenchmarkService;
import pe.unas.demoapi.service.PlanIndiceService;
import pe.unas.demoapi.service.SetupService;

@SpringBootApplication
public class DemoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiApplication.class, args);
    }

    /*
     * Dispatcher equivalente a ejecutar por separado:
     *   python setup_db.py         -> java -jar app.jar setup
     *   python benchmark_queries.py -> java -jar app.jar benchmark
     *   python explain_query.py    -> java -jar app.jar explain
     *   python crear_indice.py     -> java -jar app.jar index
     *
     * Sin argumentos, ejecuta la secuencia completa: setup -> benchmark -> explain -> index -> benchmark
     */
    @Bean
    CommandLineRunner dispatcher(SetupService setupService,
                                  BenchmarkService benchmarkService,
                                  PlanIndiceService planIndiceService) {
        return args -> {
            if (args.length == 0) {
                setupService.generarDatosDePrueba();
                benchmarkService.ejecutarBenchmark();
                planIndiceService.mostrarPlanDeConsulta();
                planIndiceService.crearIndiceDocente();
                System.out.println("Benchmark despues de crear el indice:");
                benchmarkService.ejecutarBenchmark();
                return;
            }

            switch (args[0]) {
                case "setup" -> setupService.generarDatosDePrueba();
                case "benchmark" -> benchmarkService.ejecutarBenchmark();
                case "explain" -> planIndiceService.mostrarPlanDeConsulta();
                case "index" -> planIndiceService.crearIndiceDocente();
                default -> System.out.println(
                        "Argumento no reconocido: " + args[0] +
                        ". Usar: setup | benchmark | explain | index (o ninguno para ejecutar todo)."
                );
            }
        };
    }
}
