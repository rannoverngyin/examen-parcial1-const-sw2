package com.fiis.unas.domain.entity;

public class Curso {
    private Long id;
    private String codigo;
    private String nombre;
    private Integer ciclo;
    private Integer creditos;
    private String docente;

    public Curso() {}

    public Curso(String codigo, String nombre, Integer ciclo, Integer creditos, String docente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ciclo = ciclo;
        this.creditos = creditos;
        this.docente = docente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCiclo() { return ciclo; }
    public void setCiclo(Integer ciclo) { this.ciclo = ciclo; }
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    public String getDocente() { return docente; }
    public void setDocente(String docente) { this.docente = docente; }
}
