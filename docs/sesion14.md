# Reporte de Evidencias - Sesión 14

## Evidencias del Laboratorio

### A. Control de Calidad y Cobertura (JaCoCo)
A continuación se presentan las capturas del reporte interactivo generado por JaCoCo, donde se valida que el código cumple con las reglas de negocio y los caminos alternativos probados.

| Descripción del Reporte | Captura de Pantalla |
| :--- | :--- |
| **Reporte Inicial:** Cobertura de caminos principales en `CalidadService`. | `![Reporte JaCoCo 1](docs/reporteJaCoCo-1.png)` |
| **Reporte Final:** Cobertura incremental con el método `esAceptable`. | `![Reporte JaCoCo 2](docs/reporteJaCoCo-2.png)` |
| **Umbral Mínimo:** Validación de la regla del 70% configurada en el `pom.xml`. | `![Umbral Mínimo Cobertura](docs/umbral-minimo-cobertura.png)` |

---

### B. Ejecución de Pruebas en Consola (`BUILD SUCCESS`)
Ejecución del comando `./mvnw clean test`. Este proceso compila el proyecto de manera limpia, ejecuta la totalidad de las pruebas unitarias e integradas, y valida el umbral de cobertura automatizado antes del empaquetado:

```bash
[INFO] Scanning for projects...
[INFO] 
[INFO] --- maven-clean-plugin:3.3.2:clean (default-clean) @ examen-parcial1-const-sw2 ---
[INFO] 
[INFO] --- jacoco-maven-plugin:0.8.12:prepare-agent (default) @ examen-parcial1-const-sw2 ---
[INFO] argLine set to -javaagent:C:\\Users\\...\\jacoco.exec
[INFO] 
[INFO] --- maven-surefire-plugin:3.2.5:test (default-test) @ examen-parcial1-const-sw2 ---
[INFO] Running pe.unas.demoapi.presentation.ProductoControllerIntegrationTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.428 s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] --- jacoco-maven-plugin:0.8.12:report (report) @ examen-parcial1-const-sw2 ---
[INFO] Loading execution data file C:\U\construcción_sw\examen\examen-parcial1-const-sw2\target\jacoco.exec
[INFO] Analyzed bundle 'examen-parcial1-const-sw2' with 4 classes
[INFO] 
[INFO] --- jacoco-maven-plugin:0.8.12:check (check) @ examen-parcial1-const-sw2 ---
[INFO] All coverage checks have been met.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.314 s
[INFO] Finished at: 2026-05-21T08:40:46-05:00