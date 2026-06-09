package sesion19.configuracion;

import sesion19.configuracion.application.EmailNotificadorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailNotificadorServiceTest {

    @Test
    void debeEnviarNotificacionPorEmail() {
        EmailNotificadorService service = new EmailNotificadorService();

        String resultado = service.enviar("correo@unas.edu.pe");

        assertTrue(resultado.contains("Email"));
    }

    
}
