# Tabla de priorización de pruebas – Sesión 10

| Caso de prueba            | Riesgo                          | Prioridad | Entrada | Resultado esperado |
|---------------------------|---------------------------------|-----------|---------|--------------------|
| Promedio válido           | Cálculo incorrecto de notas     | Alta      | 14, 16  | 15.0               |
| Nota límite aprobatoria   | Error de regla académica        | Alta      | 10.5    | true               |
| Nota menor a aprobatoria  | Clasificación incorrecta        | Media     | 10.4    | false              |
| Nota fuera de rango       | Dato inválido no controlado     | Alta      | 21, 15  | Excepción          |
| Promedio ponderado válido | Cálculo incorrecto de ponderado | Alta      | 15, 17  | 16.2               |


## Evidencia de la Fase RED
img/Evidencia de FASE RED.png

## Evidencia de la Fase GREEN
img/Evidencia de FASE GREEN.png

## Evidencia de la Fase REFACTOR
img/Evidencia de FASE REFACTOR.png

## Evidencia de BUILD SUCCESS
img/Evidencia de BUILD SUCCESS.png

## Reporte Surefire
img/Reporte Surefire.png


