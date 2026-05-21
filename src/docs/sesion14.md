# Creando Service

@Service
public class CalidadService {

    public String clasificarCobertura(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("Cobertura inválida");
        }
        if (porcentaje >= 80) {
            return "ALTA";
        }
        if (porcentaje >= 50) {
            return "MEDIA";
        }
        return "BAJA";
    }
}

# Creando prueba unitaria

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.CalidadService;

import static org.junit.jupiter.api.Assertions.*;

class CalidadServiceTest {

    private final CalidadService service = new CalidadService();

    @Test
    void clasificaCoberturaAlta() {
        assertEquals("ALTA", service.clasificarCobertura(85));
    }
}

Resultado

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running sesion14.cobertura.CalidadServiceTest
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.066 s - in sesion14.cobertura.CalidadServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jacoco:0.8.12:report (report) @ integracion ---
[INFO] Loading execution data file E:\CURSOS\CONSTRUCCION DE SOFTWARE II\cobertura\target\jacoco.exec
[INFO] Analyzed bundle 'integracion' with 2 classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.021 s
[INFO] Finished at: 2026-05-21T09:08:26-05:00
[INFO] ------------------------------------------------------------------------

![alt text](<Captura de pantalla 2026-05-21 085431.png>)

# Mejorando cobertura agregando pruebas faltantes
@Test
void clasificaCoberturaMedia() {
    assertEquals("MEDIA", service.clasificarCobertura(60));
}

@Test
void clasificaCoberturaBaja() {
    assertEquals("BAJA", service.clasificarCobertura(30));
}

@Test
void rechazaCoberturaNegativa() {
    assertThrows(IllegalArgumentException.class,
            () -> service.clasificarCobertura(-1));
}

@Test
void rechazaCoberturaMayorACien() {
    assertThrows(IllegalArgumentException.class,
            () -> service.clasificarCobertura(101));
}

RESULTADO

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running sesion14.cobertura.CalidadServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.066 s - in sesion14.cobertura.CalidadServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jacoco:0.8.12:report (report) @ integracion ---
[INFO] Loading execution data file E:\CURSOS\CONSTRUCCION DE SOFTWARE II\cobertura\target\jacoco.exec
[INFO] Analyzed bundle 'integracion' with 2 classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.021 s
[INFO] Finished at: 2026-05-21T09:08:37-05:00
[INFO] ------------------------------------------------------------------------

![alt text](<Captura de pantalla 2026-05-21 085547.png>)

# Agregando una nueva regla al servicio y probarla con cobertura

## Service

public boolean esAceptable(int porcentaje) {
    if (porcentaje < 0 || porcentaje > 100) {
        throw new IllegalArgumentException("Cobertura inválida");
    }
    return porcentaje >= 70;
}
## Test

    @Test
    void esAceptableCon70() {
        assertTrue(service.esAceptable(70));
    }

    @Test
    void esAceptableCon90() {
        assertTrue(service.esAceptable(90));
    }

    @Test
    void noEsAceptableCon40() {
        assertFalse(service.esAceptable(40));
    }

RESULTADO

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running sesion14.cobertura.CalidadServiceTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.048 s - in sesion14.cobertura.CalidadServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jacoco:0.8.12:report (report) @ integracion ---
[INFO] Loading execution data file E:\CURSOS\CONSTRUCCION DE SOFTWARE II\cobertura\target\jacoco.exec
[INFO] Analyzed bundle 'integracion' with 2 classes
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.044 s
[INFO] Finished at: 2026-05-21T09:15:17-05:00
[INFO] ------------------------------------------------------------------------
PS E:\CURSOS\CONSTRUCCION DE SOFTWARE II\cobertura> 

![alt text](image.png)

