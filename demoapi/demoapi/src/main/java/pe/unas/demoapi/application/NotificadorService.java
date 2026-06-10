package pe.unas.demoapi.application;

// Contrato base para notificaciones.
public interface NotificadorService {
    String enviar(String destino);
}