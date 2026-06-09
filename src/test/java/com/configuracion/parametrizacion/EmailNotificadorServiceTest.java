package com.configuracion.parametrizacion;

import org.junit.jupiter.api.Test;
import com.configuracion.parametrizacion.application.EmailNotificadorService;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailNotificadorServiceTest {
    @Test
    void debeEnviarNotificacionPorEmail() {
        EmailNotificadorService service = new EmailNotificadorService();
        String resultado = service.enviar("correo@unas.edu.pe");
        assertTrue(resultado.contains("EMAIL"));
    }
}