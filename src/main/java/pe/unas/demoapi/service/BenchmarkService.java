package pe.unas.demoapi.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import pe.unas.demoapi.model.Curso;
import pe.unas.demoapi.repository.CursoJdbcDao;
import pe.unas.demoapi.repository.CursoRepository;

@Service
public class BenchmarkService {

    private final CursoRepository cursoRepository;
    private final CursoJdbcDao cursoJdbcDao;

    public BenchmarkService(CursoRepository cursoRepository, CursoJdbcDao cursoJdbcDao) {
        this.cursoRepository = cursoRepository;
        this.cursoJdbcDao = cursoJdbcDao;
    }

    private record ResultadoMedicion(String nombre, long filasOResultado, double tiempoMs) {
    }

    private ResultadoMedicion medir(String nombre, Supplier<Long> funcion) {
        long inicio = System.nanoTime();
        long resultado = funcion.get();
        long fin = System.nanoTime();
        double tiempoMs = (fin - inicio) / 1_000_000.0;
        return new ResultadoMedicion(nombre, resultado, tiempoMs);
    }

    public void ejecutarBenchmark() {
        List<ResultadoMedicion> resultados = List.of(
                medir("ORM: cursos ciclo 7", () -> (long) ormListarCiclo7().size()),
                medir("SQL: cursos ciclo 7", () -> (long) sqlListarCiclo7().size()),
                medir("ORM: contar docente", this::ormContarPorDocente),
                medir("SQL: contar docente", this::sqlContarPorDocente)
        );

        System.out.println();
        System.out.printf("%-24s | %-16s | %10s%n", "Consulta", "Filas/Resultado", "Tiempo ms");
        System.out.println("-".repeat(58));
        for (ResultadoMedicion r : resultados) {
            System.out.printf("%-24s | %-16d | %10.4f%n", r.nombre(), r.filasOResultado(), r.tiempoMs());
        }
        System.out.println();
    }

    private List<Curso> ormListarCiclo7() {
        return cursoRepository.findByCiclo(7);
    }

    private List<Curso> sqlListarCiclo7() {
        return cursoJdbcDao.listarPorCiclo(7);
    }

    private Long ormContarPorDocente() {
        return cursoRepository.countByDocente("Mg. Yanac");
    }

    private Long sqlContarPorDocente() {
        return cursoJdbcDao.contarPorDocente("Mg. Yanac");
    }
}
