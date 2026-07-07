package pe.unas.demoapi.controller;

import org.springframework.web.bind.annotation.*;
import pe.unas.demoapi.dto.ComparacionConsulta;
import pe.unas.demoapi.dto.ResultadoConsulta;
import pe.unas.demoapi.service.ConsultaService;
import pe.unas.demoapi.service.IndiceService;
import pe.unas.demoapi.service.SeederService;

import java.util.List;
import java.util.Map;

/**
 * Expone como endpoints REST cada paso de la guia de la Sesion 26:
 * crear BD -> medir sin indices -> crear indices -> medir con indices -> comparar.
 */
@RestController
@RequestMapping("/api")
public class OptimizacionController {

    private final SeederService seederService;
    private final ConsultaService consultaService;
    private final IndiceService indiceService;

    public OptimizacionController(SeederService seederService,
                                   ConsultaService consultaService,
                                   IndiceService indiceService) {
        this.seederService = seederService;
        this.consultaService = consultaService;
        this.indiceService = indiceService;
    }

    /** Paso 2: crea las tablas y genera 5000 estudiantes, 4 cursos y 80000 matriculas. */
    @PostMapping("/db/crear")
    public Map<String, String> crearBaseDeDatos() {
        long inicio = System.nanoTime();
        seederService.crearBaseDeDatos();
        double segundos = (System.nanoTime() - inicio) / 1_000_000_000.0;
        return Map.of(
                "mensaje", "Base de datos universidad.h2 creada correctamente",
                "segundos", String.valueOf(segundos)
        );
    }

    /** Paso 3: ejecuta las 3 consultas de la guia y captura tiempo + EXPLAIN. */
    @GetMapping("/consultas/medir")
    public List<ResultadoConsulta> medirConsultas() {
        return consultaService.ejecutar(consultaService.consultasBase());
    }

    /** Paso 5: crea los indices simples y compuestos descritos en la guia. */
    @PostMapping("/indices/crear")
    public Map<String, Object> crearIndices() {
        indiceService.crearIndices();
        return Map.of(
                "mensaje", "Indices creados correctamente",
                "indices", indiceService.listarIndices()
        );
    }

    /** Lista los indices actualmente presentes en la base de datos. */
    @GetMapping("/indices")
    public List<String> listarIndices() {
        return indiceService.listarIndices();
    }

    /**
     * Paso 6: ejecuta las consultas antes de los indices, crea los indices,
     * y vuelve a ejecutarlas, devolviendo la comparacion (tiempos, filas,
     * plan de ejecucion antes/despues y % de mejora) para completar la tabla
     * del reporte tecnico.
     */
    @PostMapping("/consultas/comparar")
    public List<ComparacionConsulta> compararConsultas() {
        Map<String, String> consultas = consultaService.consultasBase();

        List<ResultadoConsulta> antes = consultaService.ejecutar(consultas);
        indiceService.crearIndices();
        List<ResultadoConsulta> despues = consultaService.ejecutar(consultas);

        return List.of(
                new ComparacionConsulta(antes.get(0), despues.get(0)),
                new ComparacionConsulta(antes.get(1), despues.get(1)),
                new ComparacionConsulta(antes.get(2), despues.get(2))
        );
    }

    /**
     * Ejercicio aplicado (punto 12): estudiantes FIIS con nota >= 14 en 2026-I.
     * Mide antes y despues de crear el indice idx_matriculas_semestre_nota.
     */
    @PostMapping("/consultas/ejercicio-aplicado")
    public ComparacionConsulta ejercicioAplicado() {
        Map<String, String> consulta = consultaService.consultaEjercicioAplicado();

        ResultadoConsulta antes = consultaService.ejecutar(consulta).get(0);
        indiceService.crearIndiceEjercicioAplicado();
        ResultadoConsulta despues = consultaService.ejecutar(consulta).get(0);

        return new ComparacionConsulta(antes, despues);
    }
}
