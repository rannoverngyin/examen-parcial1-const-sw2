package com.gonzalo.api_cs2.domain;

public class MovimientoTrazabilidad {
        public String nombretraza;
        public String trazabilidad;

        public MovimientoTrazabilidad(String nombretraza, String trazabilidad){
            this.nombretraza = nombretraza;
            this.trazabilidad = trazabilidad;
        }
        public String getNombreTraza(){
            return nombretraza;
        }
        public String getTrazabilidad(){
            return trazabilidad;
        }
}