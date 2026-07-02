package com.unas.fiis.practica26.domain.repository;

import com.unas.fiis.practica26.domain.model.Matricula;
import java.util.List;

public interface MatriculaRepository {

    void crearTabla();

    void insertarBatch(List<Matricula> matriculas);

}
