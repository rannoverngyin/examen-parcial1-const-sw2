package com.fiis.unas.domain.repository;

import com.fiis.unas.domain.entity.Curso;
import java.util.List;

public interface CursoRepository {
    List<Curso> findByCiclo(Integer ciclo);
    long countByDocente(String docente);
}
