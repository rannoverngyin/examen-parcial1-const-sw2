package com.fiis.unas.domain.vo;

public class BenchmarkResult {
    private final String consulta;
    private final int filas;
    private final double tiempoMs;

    public BenchmarkResult(String consulta, int filas, double tiempoMs) {
        this.consulta = consulta;
        this.filas = filas;
        this.tiempoMs = tiempoMs;
    }

    public String getConsulta() { return consulta; }
    public int getFilas() { return filas; }
    public double getTiempoMs() { return tiempoMs; }
}
