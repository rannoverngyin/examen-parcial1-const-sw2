
# Tabla de Priorización de Pruebas - Sesión 10

| Caso de prueba | Riesgo | Prioridad | Entrada | Resultado esperado |
|---|---|---|---|---|
| Promedio válido | Cálculo incorrecto de notas | Alta | 14, 16 | 15.0 |
| Nota límite aprobatoria | Error de regla académica | Alta | 10.5 | true |
| Nota menor a aprobatoria | Clasificación incorrecta | Media | 10.4 | false |
| Nota fuera de rango | Dato inválido no controlado | Alta | 21, 15 | Excepción |
| Promedio ponderado | Cálculo incorrecto de pesos | Alta | 15.0, 17.0 | 16.2 |

# FASE RED
![alt text](image.png)
Se crea el test y se ejecuta pero falla  porque NotaService todavía no existe.

Archivo: NotaServiceTest.java
package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.NotaService;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(IllegalArgumentException.class,
            () -> service.promedio(21.0, 15.0));
    }

    @Test
    void calculaPromedioPonderado() {
        assertEquals(16.2, service.promedioPonderado(15.0, 17.0), 0.001);
    }
}

----------------------------------------------------------
RESULTADO:

PS C:\Users\USER\Downloads\demoapi\demoapi> ./mvnw test
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< pe.unas:demoapi >---------------------------
[INFO] Building  0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ demoapi ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.14.1:compile (default-compile) @ demoapi ---
[INFO] Recompiling the module because of added or removed source files.
[INFO] Compiling 1 source file with javac [debug parameters release 21] to target\classes
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ demoapi ---
[INFO] skip non existing resourceDirectory C:\Users\USER\Downloads\demoapi\demoapi\src\test\resources
[INFO] 
[INFO] --- compiler:3.14.1:testCompile (default-testCompile) @ demoapi ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 2 source files with javac [debug parameters release 21] to target\test-classes
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[9,35] package pe.unas.demoapi.application does not exist
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[12,19] cannot find symbol
  symbol:   class NotaService
  location: class pe.unas.demoapi.NotaServiceTest
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[12,45] cannot find symbol
  symbol:   class NotaService
  location: class pe.unas.demoapi.NotaServiceTest
[INFO] 3 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.048 s
[INFO] Finished at: 2026-05-14T09:52:45-05:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.14.1:testCompile (default-testCompile) on project demoapi: Compilation failure: Compilation failure: 
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[9,35] package pe.unas.demoapi.application does not exist
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[12,19] cannot find symbol
[ERROR]   symbol:   class NotaService
[ERROR]   location: class pe.unas.demoapi.NotaServiceTest
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[12,45] cannot find symbol
[ERROR]   symbol:   class NotaService
[ERROR]   location: class pe.unas.demoapi.NotaServiceTest
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
PS C:\Users\USER\Downloads\demoapi\demoapi> 

----------------------------------------------------------
![alt text](image-1.png)
Luego agregamos NotaService pero sin el método promedioPonderado y nos arroja el siguiente error al ejecutar:
----------------------------------------------------------
PS C:\Users\USER\Downloads\demoapi\demoapi> ./mvnw test                    
[INFO] Scanning for projects... 
[INFO] 
[INFO] --------------------------< pe.unas:demoapi >---------------------------
[INFO] Building  0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ demoapi ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.14.1:compile (default-compile) @ demoapi ---
[INFO] Recompiling the module because of added or removed source files.
[INFO] Compiling 2 source files with javac [debug parameters release 21] to target\classes
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ demoapi ---
[INFO] skip non existing resourceDirectory C:\Users\USER\Downloads\demoapi\demoapi\src\test\resources
[INFO] 
[INFO] --- compiler:3.14.1:testCompile (default-testCompile) @ demoapi ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 2 source files with javac [debug parameters release 21] to target\test-classes
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[37,35] cannot find symbol
  symbol:   method promedioPonderado(double,double)
  location: variable service of type pe.unas.demoapi.application.NotaService
[INFO] 1 error
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.025 s
[INFO] Finished at: 2026-05-14T09:56:09-05:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.14.1:testCompile (default-testCompile) on project demoapi: Compilation failure
[ERROR] /C:/Users/USER/Downloads/demoapi/demoapi/src/test/java/pe/unas/demoapi/NotaServiceTest.java:[37,35] cannot find symbol
[ERROR]   symbol:   method promedioPonderado(double,double)
[ERROR]   location: variable service of type pe.unas.demoapi.application.NotaService
[ERROR] 
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
PS C:\Users\USER\Downloads\demoapi\demoapi> 
----------------------------------------------------------
# FASE GREEN 
![alt text](image-2.png)
Al agregar el metodo y ejecutar nos sale el siguiente resultado , indicando que todas las pruebas pasaron :
Primera versión (mínima) — NotaService.java
package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

@Service
public class NotaService {

    public double promedio(double nota1, double nota2) {
        validarNota(nota1);
        validarNota(nota2);
        return (nota1 + nota2) / 2.0;
    }

    public boolean estaAprobado(double promedio) {
        return promedio >= 10.5;
    }

    private void validarNota(double nota) {
        if (nota < 0 || nota > 20) {
            throw new IllegalArgumentException(
                "La nota debe estar entre 0 y 20");
        }
    }
}


RESULTADO
----------------------------------------------------------
PS C:\Users\USER\Downloads\demoapi\demoapi> ./mvnw test
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< pe.unas:demoapi >---------------------------
[INFO] Building  0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ demoapi ---
[INFO] Copying 1 resource from src\main\resources to target\classes
[INFO] Copying 0 resource from src\main\resources to target\classes
[INFO] 
[INFO] --- compiler:3.14.1:compile (default-compile) @ demoapi ---
[INFO] Nothing to compile - all classes are up to date.
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ demoapi ---
[INFO] skip non existing resourceDirectory C:\Users\USER\Downloads\demoapi\demoapi\src\test\resources
[INFO] 
[INFO] --- compiler:3.14.1:testCompile (default-testCompile) @ demoapi ---
[INFO] Recompiling the module because of added or removed source files.
[INFO] Compiling 1 source file with javac [debug parameters release 21] to target\test-classes
[INFO] 
[INFO] --- surefire:3.5.5:test (default-test) @ demoapi ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running pe.unas.demoapi.NotaServiceTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.127 s -- in pe.unas.demoapi.NotaServiceTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.122 s
[INFO] Finished at: 2026-05-14T09:59:35-05:00
[INFO] ------------------------------------------------------------------------
PS C:\Users\USER\Downloads\demoapi\demoapi> 
--------------------------------------------------------------------------------

Fase REFACTOR 
Versión final refactorizada — NotaService.java
package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

@Service
public class NotaService {

    private static final double NOTA_MINIMA    = 0.0;
    private static final double NOTA_MAXIMA    = 20.0;
    private static final double NOTA_APROBATORIA = 10.5;

    public double promedio(double nota1, double nota2) {
        validarNota(nota1);
        validarNota(nota2);
        return calcularPromedio(nota1, nota2);
    }

    public boolean estaAprobado(double promedio) {
        return promedio >= NOTA_APROBATORIA;
    }

    public double promedioPonderado(double practica, double examen) {
        validarNota(practica);
        validarNota(examen);
        return practica * 0.40 + examen * 0.60;
    }

    private double calcularPromedio(double nota1, double nota2) {
        return (nota1 + nota2) / 2.0;
    }

    private void validarNota(double nota) {
        if (nota < NOTA_MINIMA || nota > NOTA_MAXIMA) {
            throw new IllegalArgumentException(
                "La nota debe estar entre 0 y 20");
        }
    }
}
