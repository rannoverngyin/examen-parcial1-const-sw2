# Priorización de pruebas unitarias - Sesión 10

| Caso de prueba | Riesgo | Prioridad | Entrada | Resultado esperado |
|---|---|---|---|---|
| Promedio válido | Cálculo incorrecto de notas | Alta | 14, 16 | 15.0 |
| Nota límite aprobatoria | Error de regla académica | Alta | 10.5 | true |
| Nota menor a aprobatoria | Clasificación incorrecta | Media | 10.4 | false |
| Nota fuera de rango | Dato inválido no controlado | Alta | 21, 15 | Excepción |

## Descripción

Los casos se priorizan según su impacto en la lógica de negocio:

- Alta: errores críticos que afectan el cálculo y la validación de notas.
- Media: casos de comportamiento límite que deben ser verificados, pero no ponen en riesgo inmediato la integridad de la regla.

## Resultados

Estas pruebas cubren el cálculo de promedios, la regla de aprobación, y la validación de rangos de nota para garantizar que el servicio `NotaService` funcione correctamente.