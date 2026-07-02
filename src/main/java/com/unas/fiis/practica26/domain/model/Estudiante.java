package com.unas.fiis.practica26.domain.model;

public class Estudiante {

    private Long id;
    private String codigo;
    private String nombre;
    private String escuela;

    public Estudiante() {}

    public Estudiante(Long id, String codigo, String nombre, String escuela) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.escuela = escuela;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEscuela() { return escuela; }
    public void setEscuela(String escuela) { this.escuela = escuela; }

}
