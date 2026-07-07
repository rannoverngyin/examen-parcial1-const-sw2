package pe.unas.demoapi.dto;

import java.util.List;

public class ResultadoConsulta {

    private String nombre;
    private int filas;
    private double segundos;
    private List<String> plan;

    public ResultadoConsulta() {
    }

    public ResultadoConsulta(String nombre, int filas, double segundos, List<String> plan) {
        this.nombre = nombre;
        this.filas = filas;
        this.segundos = segundos;
        this.plan = plan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public double getSegundos() {
        return segundos;
    }

    public void setSegundos(double segundos) {
        this.segundos = segundos;
    }

    public List<String> getPlan() {
        return plan;
    }

    public void setPlan(List<String> plan) {
        this.plan = plan;
    }
}
