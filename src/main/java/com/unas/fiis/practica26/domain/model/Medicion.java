package com.unas.fiis.practica26.domain.model;

public class Medicion {

    private String nombre;
    private String sql;
    private int filas;
    private double tiempoSegundos;
    private String plan;

    public Medicion() {}

    public Medicion(String nombre, String sql, int filas, double tiempoSegundos, String plan) {
        this.nombre = nombre;
        this.sql = sql;
        this.filas = filas;
        this.tiempoSegundos = tiempoSegundos;
        this.plan = plan;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getSql() { return sql; }
    public void setSql(String sql) { this.sql = sql; }
    public int getFilas() { return filas; }
    public void setFilas(int filas) { this.filas = filas; }
    public double getTiempoSegundos() { return tiempoSegundos; }
    public void setTiempoSegundos(double tiempoSegundos) { this.tiempoSegundos = tiempoSegundos; }
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }

}
