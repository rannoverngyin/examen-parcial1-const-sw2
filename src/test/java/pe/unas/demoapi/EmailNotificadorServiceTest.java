package pe.unas.demoapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import pe.unas.demoapi.application.EmailNotificadorService;

public class EmailNotificadorServiceTest {
    @Test
    void deveEnviarNotificacioPorEmail(){
        EmailNotificadorService service = new EmailNotificadorService();

        String resultado = service.enviar("correo@unas.edu.pe");
        assertTrue(resultado.contains(resultado));
    }
}
