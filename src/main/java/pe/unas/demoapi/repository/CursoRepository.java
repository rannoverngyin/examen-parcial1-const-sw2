package pe.unas.demoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.unas.demoapi.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Consulta ORM equivalente a: SELECT * FROM cursos WHERE ciclo = 7
    List<Curso> findByCiclo(Integer ciclo);

    // Consulta ORM equivalente a: SELECT COUNT(*) FROM cursos WHERE docente = ?
    long countByDocente(String docente);
}
