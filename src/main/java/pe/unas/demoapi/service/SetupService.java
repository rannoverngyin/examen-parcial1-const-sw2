package pe.unas.demoapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pe.unas.demoapi.model.Curso;
import pe.unas.demoapi.repository.CursoRepository;

@Service
public class SetupService {

    private final CursoRepository cursoRepository;

    private static final List<Object[]> CURSOS_BASE = List.of(
            new Object[]{"IS040701", "Arquitectura de Software", 7, 4, "Dr. Garcia"},
            new Object[]{"IS040703", "Construccion de Software II", 7, 5, "Mg. Yanac"},
            new Object[]{"IS040602", "Analitica de Datos", 6, 4, "Dra. Rios"},
            new Object[]{"IS040801", "Calidad de Software", 8, 4, "Mg. Torres"}
    );

    public SetupService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public void generarDatosDePrueba() {
        cursoRepository.deleteAll();

        List<Curso> cursos = new java.util.ArrayList<>(5000);
        for (int i = 0; i < 5000; i++) {
            Object[] base = CURSOS_BASE.get(i % CURSOS_BASE.size());
            cursos.add(new Curso(
                    base[0] + "-" + i,
                    (String) base[1],
                    (Integer) base[2],
                    (Integer) base[3],
                    (String) base[4]
            ));
        }
        cursoRepository.saveAll(cursos);

        System.out.println("Base de datos creada: fiis.mv.db con 5000 cursos");
    }
}
