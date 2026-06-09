package pe.unas.demoapi19;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi19.application.EmailNotificadorService;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailNotificadorServiceTest {
    @Test
    void debeEnviarNotificacionPorEmail() {
        EmailNotificadorService service = new EmailNotificadorService();

        String resultado = service.enviar("correo@unas.edu.pe");

        assertTrue(resultado.contains("EMAIL"));
    }

}
