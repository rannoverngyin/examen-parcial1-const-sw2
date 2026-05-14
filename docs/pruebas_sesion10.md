# Matriz de Priorización de Pruebas

| Caso de prueba | Riesgo | Prioridad | Entrada | Resultado esperado |
| :--- | :--- | :--- | :--- | :--- |
| Promedio válido | Cálculo incorrecto de notas | Alta | "14, 16" | 15.0 |
| Nota límite aprobatoria | Error de regla académica | Alta | 10.5 | true |
| Nota menor a aprobatoria | Clasificación incorrecta | Media | 10.4 | false |
| Nota fuera de rango | Dato inválido no controlado | Alta | "21, 15" | Excepción |
| Promedio ponderado | Fórmulas o pesos incorrectos | Alta | "15, 17" | 16.2 |
