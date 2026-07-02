package com.unas.fiis.practica26.domain.model;

public class Curso {

    private Long id;
    private String nombre;
    private Integer ciclo;

    public Curso() {}

    public Curso(Long id, String nombre, Integer ciclo) {
        this.id = id;
        this.nombre = nombre;
        this.ciclo = ciclo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCiclo() { return ciclo; }
    public void setCiclo(Integer ciclo) { this.ciclo = ciclo; }

}
