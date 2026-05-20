## Evidencia de Ejecución de Pruebas con Maven

Se ejecutaron de manera exitosa las pruebas de integración para el servicio REST de productos mediante el comando `./mvnw test`, logrando validar correctamente los contratos de los endpoints (`GET`, `POST`, `DELETE`, `/total` y `/existe`) y la interoperabilidad entre las capas de presentación y aplicación.

### Resumen de la Ejecución

| Métrica | Valor |
| :--- | :--- |
| **Pruebas Ejecutadas** | 6 |
| **Fallos (Failures)** | 0 |
| **Errores (Errors)** | 0 |
| **Omitidas (Skipped)** | 0 |
| **Tiempo de Ejecución** | 1.461 s (Suite) / 6.071 s (Total) |
| **Resultado Final** | **BUILD SUCCESS** |

### Log de Salida de la Terminal

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.461 s -- in pe.unas.demoapi.presentation.ProductoControllerIntegrationTest

Results:

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

------------------------------------------------------------------------
BUILD SUCCESS
------------------------------------------------------------------------
Total time:  6.071 s
Finished at: 2026-05-20T09:24:06-05:00
------------------------------------------------------------------------