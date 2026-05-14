# Priorización de pruebas unitarias - Sesión 10

| Caso de prueba | Riesgo | Prioridad | Entrada | Resultado esperado |
|---|---|---|---|---|
| Promedio válido | Cálculo incorrecto de notas | Alta | 14, 16 | 15.0 |
| Nota límite aprobatoria | Error de regla académica | Alta | 10.5 | true |
| Nota menor a aprobatoria | Clasificación incorrecta | Media | 10.4 | false |
| Nota fuera de rango | Dato inválido no controlado | Alta | 21, 15 | Excepción `IllegalArgumentException` |
| Promedio ponderado válido | Cálculo incorrecto del promedio ponderado | Alta | Práctica: 15, Examen: 17 | 16.2 |

## Resumen del proceso TDD

En la fase RED se escribieron primero las pruebas unitarias antes de implementar la clase `NotaService`.

En la fase GREEN se implementó el código mínimo necesario para que las pruebas pasaran correctamente.

En la fase REFACTOR se mejoró la legibilidad del código usando constantes y métodos privados, manteniendo las pruebas en estado verde.

## Resultado esperado

Todas las pruebas deben ejecutarse correctamente con Maven, mostrando `BUILD SUCCESS`.