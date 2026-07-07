package pe.unas.demoapi.dto;

public class ComparacionConsulta {

    private String nombre;
    private double segundosAntes;
    private double segundosDespues;
    private double mejoraPorcentaje;
    private java.util.List<String> planAntes;
    private java.util.List<String> planDespues;

    public ComparacionConsulta() {
    }

    public ComparacionConsulta(ResultadoConsulta antes, ResultadoConsulta despues) {
        this.nombre = antes.getNombre();
        this.segundosAntes = antes.getSegundos();
        this.segundosDespues = despues.getSegundos();
        this.mejoraPorcentaje = antes.getSegundos() == 0
                ? 0
                : ((antes.getSegundos() - despues.getSegundos()) / antes.getSegundos()) * 100.0;
        this.planAntes = antes.getPlan();
        this.planDespues = despues.getPlan();
    }

    public String getNombre() {
        return nombre;
    }

    public double getSegundosAntes() {
        return segundosAntes;
    }

    public double getSegundosDespues() {
        return segundosDespues;
    }

    public double getMejoraPorcentaje() {
        return mejoraPorcentaje;
    }

    public java.util.List<String> getPlanAntes() {
        return planAntes;
    }

    public java.util.List<String> getPlanDespues() {
        return planDespues;
    }
}
