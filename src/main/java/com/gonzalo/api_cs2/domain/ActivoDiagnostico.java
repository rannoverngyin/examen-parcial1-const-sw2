package com.gonzalo.api_cs2.domain;

public class ActivoDiagnostico {
    public String codigo;
    public String nombre;
    public String tipo;
    public String estado;

    public ActivoDiagnostico(String codigo, String nombre, String tipo, String estado){
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.estado = estado;
    }
    public String getCodigo(){
        return codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public String getTipo(){
        return tipo;
    }
    public String getEstado(){
        return estado;
    }
}
