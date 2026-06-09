package com.gonzalo.api_cs2;

import org.junit.jupiter.api.Test;
import com.gonzalo.api_cs2.application.EmailNotificadorService;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailNotificadorServiceTest {

    @Test
    void debeEnviarNotificacionPorEmail() {
        EmailNotificadorService service = new EmailNotificadorService();

        String resultado = service.enviar("correo@unas.edu.pe");

        assertTrue(resultado.contains("EMAIL"));
    }
}
