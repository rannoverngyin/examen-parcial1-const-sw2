Se implementó la clase NotaService aplicando estrictamente el ciclo TDD (Test-Driven Development).

# Metodología aplicada:

Red: Diseño inicial de la suite de pruebas NotaServiceTest.java cubriendo casos base, validaciones de rango (0-20), reglas académicas (nota mínima 10.5) y cálculos de promedios simples y ponderados.
Green: Implementación inicial para satisfacer los criterios de aceptación.
Refactor: Aplicación de Clean Code, extrayendo valores quemados hacia constantes estáticas (ej. NOTA_APROBATORIA, PESO_EXAMEN) para mejorar la mantenibilidad del sistema.
Documentación y Evidencia:

La estrategia y priorización de las pruebas se encuentra documentada en docs/pruebas_sesion10.md, donde se mapean las entradas, resultados esperados y riesgos evaluados.
Los reportes de ejecución de la automatización se encuentran exitosamente validados en target/surefire-reports, confirmando un BUILD SUCCESS del 100% de la batería de pruebas.

# Matriz de Priorización de Pruebas

| Caso de prueba | Riesgo | Prioridad | Entrada | Resultado esperado |
| :--- | :--- | :--- | :--- | :--- |
| Promedio válido | Cálculo incorrecto de notas | Alta | "14, 16" | 15.0 |
| Nota límite aprobatoria | Error de regla académica | Alta | 10.5 | true |
| Nota menor a aprobatoria | Clasificación incorrecta | Media | 10.4 | false |
| Nota fuera de rango | Dato inválido no controlado | Alta | "21, 15" | Excepción |
| Promedio ponderado | Fórmulas o pesos incorrectos | Alta | "15, 17" | 16.2 |
