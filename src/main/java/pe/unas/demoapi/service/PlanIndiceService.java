package pe.unas.demoapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import pe.unas.demoapi.repository.CursoJdbcDao;

@Service
public class PlanIndiceService {

    private final CursoJdbcDao cursoJdbcDao;

    public PlanIndiceService(CursoJdbcDao cursoJdbcDao) {
        this.cursoJdbcDao = cursoJdbcDao;
    }

    public void mostrarPlanDeConsulta() {
        List<Map<String, Object>> plan = cursoJdbcDao.explicarConsultaPorCiclo(7);
        System.out.println();
        System.out.println("Plan de ejecucion para: SELECT * FROM cursos WHERE ciclo = 7");
        for (Map<String, Object> fila : plan) {
            fila.values().forEach(System.out::println);
        }
        System.out.println();
    }

    public void crearIndiceDocente() {
        cursoJdbcDao.crearIndiceDocente();
        System.out.println("Indice creado: idx_cursos_docente");
    }
}
