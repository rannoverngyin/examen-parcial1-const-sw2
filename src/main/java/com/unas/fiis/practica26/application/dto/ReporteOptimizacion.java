package com.unas.fiis.practica26.application.dto;

import com.unas.fiis.practica26.domain.model.Medicion;
import java.util.List;

public class ReporteOptimizacion {

    private List<Medicion> antes;
    private List<Medicion> despues;
    private List<String> indicesCreados;
    private String mensaje;

    public ReporteOptimizacion() {}

    public ReporteOptimizacion(List<Medicion> antes, List<Medicion> despues, List<String> indicesCreados) {
        this.antes = antes;
        this.despues = despues;
        this.indicesCreados = indicesCreados;
    }

    public List<Medicion> getAntes() { return antes; }
    public void setAntes(List<Medicion> antes) { this.antes = antes; }
    public List<Medicion> getDespues() { return despues; }
    public void setDespues(List<Medicion> despues) { this.despues = despues; }
    public List<String> getIndicesCreados() { return indicesCreados; }
    public void setIndicesCreados(List<String> indicesCreados) { this.indicesCreados = indicesCreados; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

}
