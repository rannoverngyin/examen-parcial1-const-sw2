package unas.pe.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unas.pe.demo.application.CalidadService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // Levanta el entorno para verificar la integración real
class CalidadServiceTest {

    @Autowired 
    private CalidadService service;

    @Test
    void noDebeRomperTotalInicialDeProductos() {
        // Al ejecutar 'mvn test', esto garantiza que el total siga siendo 2 
        // y que ninguna modificación reciente haya alterado el comportamiento base.
        assertEquals(2, service.total()); 
    }
}