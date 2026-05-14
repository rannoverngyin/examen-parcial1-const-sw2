##RED

Agregar pruebas de test

package prueba.tdd;

import org.junit.jupiter.api.Test;
import prueba.tdd.applicaction.NotaService;

import static org.junit.jupiter.api.Assertions.*;

//Fase RED: Escribir pruebas que fallen inicialmente, definiendo el comportamiento esperado del sistema.
class NotaServiceTest {

    private final NotaService service = new NotaService();

    @Test
    void calculaPromedioSimple() {
        assertEquals(15.0, service.promedio(14.0, 16.0), 0.001);
    }

    @Test
    void determinaAprobadoCuandoPromedioEsMayorOIgualA105() {
        assertTrue(service.estaAprobado(10.5));
    }

    @Test
    void determinaDesaprobadoCuandoPromedioEsMenorA105() {
        assertFalse(service.estaAprobado(10.4));
    }

    @Test
    void rechazaNotasFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> service.promedio(21.0, 15.0));
    }
}

    Resultado:

    [ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /E:/CURSOS/CONSTRUCCION DE SOFTWARE II/tdd/src/test/java/prueba/tdd/NotaServiceTest.java:[4,31] package prueba.tdd.applicaction does not exist
[ERROR] /E:/CURSOS/CONSTRUCCION DE SOFTWARE II/tdd/src/test/java/prueba/tdd/NotaServiceTest.java:[11,19] cannot find symbol
  symbol:   class NotaService
  location: class prueba.tdd.NotaServiceTest
[INFO] 2 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.472 s
[INFO] Finished at: 2026-05-14T10:46:28-05:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.14.1:testCompile (default-testCompile) on project tdd: Compilation failure: Compilation failure: 
[ERROR] /E:/CURSOS/CONSTRUCCION DE SOFTWARE II/tdd/src/test/java/prueba/tdd/NotaServiceTest.java:[4,31] package prueba.tdd.applicaction does not exist
[ERROR] /E:/CURSOS/CONSTRUCCION DE SOFTWARE II/tdd/src/test/java/prueba/tdd/NotaServiceTest.java:[11,19] cannot find symbol
[ERROR]   symbol:   class NotaService
[ERROR]   location: class prueba.tdd.NotaServiceTest
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException

##GREEN
package prueba.tdd.applicaction;


//Fase GREEN: Implementar el código mínimo necesario para que las pruebas pasen, asegurando que el sistema cumple con los requisitos definidos en las pruebas.

import org.springframework.stereotype.Service;

@Service
public class NotaService {
 
    public double promedio(double nota1, double nota2) {
        validarNota(nota1);
        validarNota(nota2);
        return calcularPromedio(nota1, nota2);
    }

    public boolean estaAprobado(double promedio) {
        return promedio >= NOTA_APROBATORIA;
    }

    private double calcularPromedio(double nota1, double nota2) {
        return (nota1 + nota2) / 2.0;
    }

    private void validarNota(double nota) {
        if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 20");
        }
    }
}

Resultado:

[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------------< prueba:tdd >-----------------------------
[INFO] Building  0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ tdd ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.14.1:compile (default-compile) @ tdd ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ tdd ---
[INFO] skip non existing resourceDirectory E:\CURSOS\CONSTRUCCION DE SOFTWARE II\tdd\src\test\resources
[INFO] 
[INFO] --- compiler:3.14.1:testCompile (default-testCompile) @ tdd ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- surefire:3.5.5:test (default-test) @ tdd ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running prueba.tdd.NotaServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.110 s -- in prueba.tdd.NotaServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.202 s
[INFO] Finished at: 2026-05-14T10:47:41-05:00
[INFO] ------------------------------------------------------------------------


##REFACTOR

//Refactorting: Mejorar el diseño del código sin cambiar su comportamiento externo, optimizando la legibilidad, mantenibilidad y eficiencia.   
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 20.0;
    private static final double NOTA_APROBATORIA = 10.5;



    RESULTADO
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running prueba.tdd.NotaServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.097 s -- in prueba.tdd.NotaServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.499 s
[INFO] Finished at: 2026-05-14T10:50:12-05:00
[INFO] ------------------------------------------------------------------------



##PRUEBA UNITARARIAS

1. Promedio válido (14,16)
 @Test
    void calculaPromedioSimple() {
        assertEquals(15.0, service.promedio(14.0, 16.0), 0.001);
    }
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running prueba.tdd.NotaServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.102 s -- in prueba.tdd.NotaServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.343 s
[INFO] Finished at: 2026-05-14T10:35:10-05:00
[INFO] ------------------------------------------------------------------------
    


2. Nota límite aprobatoria (10.5)

private static final double NOTA_APROBATORIA = 10.5;

[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running prueba.tdd.NotaServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.102 s -- in prueba.tdd.NotaServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.343 s
[INFO] Finished at: 2026-05-14T10:35:10-05:00
[INFO] ------------------------------------------------------------------------


3. Nota menor a aprobatoria (10.4)
private static final double NOTA_APROBATORIA = 10.4;

[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   NotaServiceTest.determinaDesaprobadoCuandoPromedioEsMenorA105:25 expected: <false> but was: <true>
[INFO] 
[ERROR] Tests run: 5, Failures: 1, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.467 s
[INFO] Finished at: 2026-05-14T10:39:39-05:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.5.5:test (default-test) on project tdd: There are test failures.
[ERROR] 
[ERROR] See E:\CURSOS\CONSTRUCCION DE SOFTWARE II\tdd\target\surefire-reports for the individual test results.
[ERROR] See dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException


4. Nota fuera de rango (21,15)

private static final double NOTA_MINIMA = 15.0;
private static final double NOTA_MAXIMA = 21.0;
private static final double NOTA_APROBATORIA = 10.5;

[INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   NotaServiceTest.rechazaNotasFueraDeRango:30 Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.
[ERROR] Errors: 
[ERROR]   NotaServiceTest.calculaPromedioSimple:15 ≫ IllegalArgument La nota debe estar entre 0 y 20
[INFO] 
[ERROR] Tests run: 5, Failures: 1, Errors: 1, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.318 s
[INFO] Finished at: 2026-05-14T10:42:44-05:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.5.5:test (default-test) on project tdd: There are test failures.
[ERROR] 
[ERROR] See E:\CURSOS\CONSTRUCCION DE SOFTWARE II\tdd\target\surefire-reports for the individual test results.
[ERROR] See dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException


##NUEVO CICLO TDD

@Test
void calculaPromedioPonderado() {
    assertEquals(16.2, service.promedioPonderado(15.0, 17.0), 0.001);
}

Implementación esperada:
public double promedioPonderado(double practica, double examen) {
    validarNota(practica);
    validarNota(examen);
    return practica * 0.40 + examen * 0.60;
}

RESULTADO

[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running prueba.tdd.NotaServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.097 s -- in prueba.tdd.NotaServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  2.499 s
[INFO] Finished at: 2026-05-14T10:50:12-05:00
[INFO] ------------------------------------------------------------------------