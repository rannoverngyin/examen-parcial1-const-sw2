# Reporte de Evidencias - Sesión 14

## Evidencias del Laboratorio (Capturas de Pantalla)

### 1. Pruebas Agregadas en el Editor
*Las capturas de las pruebas unitarias y el método `esAceptable` implementados en el IDE se encuentran adjuntas en la carpeta de documentación:*
* `![Pruebas en el Editor](docs/reporteJaCoCo-1.png)`

### 2. Reporte de Cobertura Interactiva (`target/site/jacoco/index.html`)
*Validación visual del porcentaje de líneas y ramas cubiertas por el banco de pruebas tras las modificaciones:*
* `![Reporte JaCoCo Final](docs/reporteJaCoCo-2.png)`

### 3. Validación del Umbral Mínimo de Cobertura (`pom.xml`)
*Configuración de la regla de control de calidad para asegurar un piso mínimo del 70%:*
* `![Umbral Mínimo Cobertura](docs/umbral-minimo-cobertura.png)`

---

## Ejecución de Pruebas en Consola (`BUILD SUCCESS`)

Ejecución exitosa del comando `./mvnw clean test` que demuestra que el proyecto compila de manera limpia, pasa todos los tests y supera la regla del umbral de JaCoCo:

```bash
[INFO] Scanning for projects...
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