package com.examen.medio.domain;

public class Ensayo {
    private String nombre;
    private Boolean activo;
    private String estado;

    public Ensayo() {
    }

    public Ensayo(String nombre, Boolean activo, String estado) {
        this.nombre = nombre;
        this.activo = activo;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
