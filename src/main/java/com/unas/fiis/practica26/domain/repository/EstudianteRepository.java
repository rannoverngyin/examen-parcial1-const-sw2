package com.unas.fiis.practica26.domain.repository;

import com.unas.fiis.practica26.domain.model.Estudiante;
import java.util.List;

public interface EstudianteRepository {

    void crearTabla();

    void insertarBatch(List<Estudiante> estudiantes);

}
