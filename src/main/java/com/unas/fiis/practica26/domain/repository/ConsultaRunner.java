package com.unas.fiis.practica26.domain.repository;

import com.unas.fiis.practica26.domain.model.Medicion;

public interface ConsultaRunner {

    Medicion ejecutarConMedicion(String nombre, String sql);

    String obtenerPlan(String sql);

    void ejecutarDDL(String sql);

}
