package com.unas.fiis.practica26.domain.model;

public class Matricula {

    private Long id;
    private Long estudianteId;
    private Long cursoId;
    private String semestre;
    private Double nota;

    public Matricula() {}

    public Matricula(Long id, Long estudianteId, Long cursoId, String semestre, Double nota) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.semestre = semestre;
        this.nota = nota;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }

}
