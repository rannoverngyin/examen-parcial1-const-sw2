package com.unas.fiis.practica26.domain.repository;

import com.unas.fiis.practica26.domain.model.Curso;
import java.util.List;

public interface CursoRepository {

    void crearTabla();

    void insertarBatch(List<Curso> cursos);

}
