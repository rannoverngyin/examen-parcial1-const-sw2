# SESIÓN 10 – TDD EN JAVA Y REFACTORIZACIÓN SEGURA

## Curso
Construcción de Software II

## Alumno
Nilver

## Proyecto
examen-parcial1-const-sw2

---

# Objetivo de la práctica

Aplicar el ciclo TDD (Test Driven Development) utilizando pruebas unitarias en Java con JUnit 5 y Maven para implementar y validar reglas de negocio relacionadas con cálculo de notas.

---

# Herramientas utilizadas

| Herramienta | Descripción |
|---|---|
| Java 17 | Lenguaje de programación |
| Maven Wrapper | Gestión y ejecución del proyecto |
| Spring Boot | Framework base |
| JUnit 5 | Pruebas unitarias |
| Git | Control de versiones |

---

# Estructura del proyecto

```plaintext
src/main/java/pe/unas/demoapi/application
src/test/java/pe/unas/demoapi/application
```

---

# FASE RED

## Objetivo

Escribir primero las pruebas unitarias antes de implementar la lógica de negocio.

---

## Archivo de prueba creado

```plaintext
src/test/java/pe/unas/demoapi/application/NotaServiceTest.java
```

---

## Código de pruebas implementado

```java
package pe.unas.demoapi.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotaServiceTest {

    private final NotaService service = new NotaService();

    @Test
    void calculaPromedioSimple() {
        assertEquals(15.0,
                service.promedio(14.0, 16.0),
                0.001);
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
        assertEquals(16.2,
                service.promedioPonderado(15.0, 17.0),
                0.001);
    }

    @Test
    void rechazaNotasInvalidasEnPromedioPonderado() {
        assertThrows(IllegalArgumentException.class,
                () -> service.promedioPonderado(25.0, 18.0));
    }
}
```

---

## Ejecución de pruebas

Comando ejecutado:

```powershell
.\mvnw.cmd test
```

---

## Resultado obtenido en RED

Las pruebas fallaron inicialmente porque los métodos aún no existían en `NotaService`.

Errores detectados:

```plaintext
cannot find symbol
method promedio(double,double)

cannot find symbol
method estaAprobado(double)

cannot find symbol
method promedioPonderado(double,double)
```

---

## Evidencia RED

```plaintext
BUILD FAILURE
```

---

## Explicación

La fase RED se cumplió correctamente porque primero se escribieron las pruebas unitarias y luego se verificó que fallaran debido a que la funcionalidad todavía no había sido implementada.

---

# FASE GREEN

## Objetivo

Implementar el código mínimo necesario para que todas las pruebas pasen correctamente.

---

## Archivo implementado

```plaintext
src/main/java/pe/unas/demoapi/application/NotaService.java
```

---

## Código implementado

```java
package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

@Service
public class NotaService {

    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 20.0;
    private static final double NOTA_APROBATORIA = 10.5;

    public double promedio(double nota1, double nota2) {
        validarNota(nota1);
        validarNota(nota2);
        return calcularPromedio(nota1, nota2);
    }

    public double promedioPonderado(double practica, double examen) {
        validarNota(practica);
        validarNota(examen);
        return practica * 0.40 + examen * 0.60;
    }

    public boolean estaAprobado(double promedio) {
        return promedio >= NOTA_APROBATORIA;
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
```

---

## Ejecución de pruebas

Comando ejecutado:

```powershell
.\mvnw.cmd test
```

---

## Resultado obtenido en GREEN

```plaintext
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

[INFO] BUILD SUCCESS
```

---

## Evidencia completa de ejecución

```plaintext
WARNING: Dynamic loading of agents will be disallowed by default in a future release

[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] Results:

[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

---

## Explicación

La fase GREEN se completó correctamente porque se implementó el código mínimo necesario para que todas las pruebas unitarias pasaran exitosamente.

Las advertencias mostradas pertenecen a la JVM de Java y no afectan el funcionamiento del proyecto.

---

# Priorización de pruebas unitarias

| Caso de prueba | Riesgo | Prioridad | Entrada | Resultado esperado |
|---|---|---|---|---|
| Promedio válido | Error en cálculo | Alta | 14,16 | 15.0 |
| Nota aprobatoria | Regla académica incorrecta | Alta | 10.5 | true |
| Nota desaprobatoria | Clasificación errónea | Media | 10.4 | false |
| Nota fuera de rango | Datos inválidos | Alta | 21,15 | Excepción |
| Promedio ponderado válido | Error de cálculo | Alta | 15,17 | 16.2 |
| Promedio ponderado inválido | Datos fuera de rango | Alta | 25,18 | Excepción |

---

# Conclusión parcial

Se aplicó correctamente el enfoque TDD mediante:

1. Escritura inicial de pruebas unitarias.
2. Verificación de fallos iniciales (RED).
3. Implementación mínima funcional (GREEN).
4. Validación mediante Maven y JUnit 5.