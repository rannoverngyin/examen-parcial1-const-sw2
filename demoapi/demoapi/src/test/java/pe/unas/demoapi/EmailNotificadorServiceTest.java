package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.EmailNotificadorService;

// Verifica el envío por correo.
class EmailNotificadorServiceTest {

    @Test
    void debeEnviarNotificacionPorEmail() {
        EmailNotificadorService service = new EmailNotificadorService();

        String resultado = service.enviar("correo@unas.edu.pe");

        // Confirma el canal usado.
        assertTrue(resultado.contains("EMAIL"));
    }
}
