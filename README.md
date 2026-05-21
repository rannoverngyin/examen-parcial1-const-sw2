# Guía de práctica – Sesión 14
## Cobertura de código y calidad

Curso: Construcción de Software II  
Stack: Java + Spring Boot + JUnit 5 + JaCoCo + SonarQube/SonarCloud  
Producto: Reporte de cobertura JaCoCo + pruebas mejoradas + commit en Git  
Duración: 60 minutos

## 1) Objetivo de la práctica
Evaluar la calidad del banco de pruebas mediante cobertura de código, identificar zonas no cubiertas y mejorar pruebas unitarias/integrales en una aplicación Spring Boot.

Al finalizar se debe:
- generar reporte de cobertura con JaCoCo,
- interpretar resultados,
- registrar avance con Git.

## 2) Requisitos previos
- Java 17 activo.
- Maven operativo (`mvn -version` o `./mvnw -version`).
- Proyecto Spring Boot con `spring-boot-starter-test`.
- Pruebas previas del curso.
- Git configurado.

## 3) Fundamento breve
La cobertura indica qué parte del código ejecutan las pruebas.

Métricas relevantes:
- **Instruction coverage**: instrucciones ejecutadas.
- **Branch coverage**: decisiones (`if/else`, `switch`, excepciones).
- **Line coverage**: líneas ejecutadas.
- **Method coverage**: métodos invocados.

Idea clave: cobertura alta ayuda, pero debe acompañarse de pruebas de calidad (no solo caminos felices).

## 4) Preparación inicial
1. Ubicarte en la raíz del proyecto.
2. Verificar que existan `pom.xml`, `src/`, `mvnw`, `mvnw.cmd`.
3. Confirmar dependencia de test en `pom.xml`:
   - `org.springframework.boot:spring-boot-starter-test` con `scope test`.

## 5) Configurar JaCoCo en `pom.xml`
Agregar `org.jacoco:jacoco-maven-plugin:0.8.12` dentro de `build.plugins` con:
- `prepare-agent`
- `report` en fase `test`

Opcional evaluable:
- regla `check` con umbral mínimo de cobertura de líneas (ejemplo: `0.70`).

## 6) Servicio para evaluar cobertura
Crear/verificar `src/main/java/pe/unas/demoapi/application/CalidadService.java` con lógica:
- `clasificarCobertura(int porcentaje)`:
  - inválido (<0 o >100) -> `IllegalArgumentException("Cobertura inválida")`
  - `>= 80` -> `"ALTA"`
  - `>= 50` -> `"MEDIA"`
  - caso contrario -> `"BAJA"`

## 7) Pruebas unitarias
Crear/verificar `src/test/java/pe/unas/demoapi/CalidadServiceTest.java`.

Casos mínimos:
- clasifica cobertura alta.
- clasifica cobertura media.
- clasifica cobertura baja.
- rechaza cobertura negativa.
- rechaza cobertura mayor a 100.

## 8) Ejecutar pruebas y generar reporte
Comando principal:
- `./mvnw clean test`

Alternativa:
- `mvn clean test`

Resultado esperado:
- `BUILD SUCCESS`
- generación de reporte en `target/site/jacoco/index.html`.

## 9) Interpretar reporte JaCoCo
Colores:
- Verde: cubierto.
- Rojo: no cubierto.
- Amarillo: ramas parcialmente cubiertas.

Comparar cobertura antes/después y anotar qué método/clase mejoró.

## 10) Calidad complementaria
Opciones sugeridas:
- SonarLint (rápido/local).
- SonarQube (análisis integral).
- SonarCloud (integración en nube con GitHub).

## 11) Ejercicio aplicado
Agregar en `CalidadService`:
- `esAceptable(int porcentaje)` retorna `true` si `porcentaje >= 70`.
- mantener validación de rango 0..100 (si no, excepción).

Agregar pruebas para:
- 70
- 90
- 40

Volver a ejecutar `./mvnw clean test` y revisar JaCoCo.

## 12) Registrar avance con Git
- `git status`
- `git add .`
- `git commit -m "Agrega cobertura de código con JaCoCo"`
- `git push`

## 13) Evidencias de entrega
- Captura de terminal con `BUILD SUCCESS`.
- Captura de `target/site/jacoco/index.html`.
- Captura de pruebas agregadas.
- Commit o Pull Request.
- Breve interpretación de mejora de cobertura.

## 14) Errores frecuentes
- No aparece `target/site/jacoco`: verificar plugin JaCoCo y ejecutar `mvn clean test`.
- `BUILD FAILURE`: revisar prueba fallida y lógica esperada.
- No compila: revisar paquetes, imports, llaves y rutas.
- Falla umbral de cobertura: agregar pruebas o ajustar mínimo temporalmente.
- `working tree clean`: no hay cambios pendientes.

## 15) Rúbrica (20 puntos)
- Configuración JaCoCo: 4
- Pruebas unitarias: 5
- Interpretación de cobertura: 4
- Calidad del código: 3
- Git y evidencia: 4

## Mensaje final
La cobertura no es un fin en sí mismo; es un indicador para fortalecer el banco de pruebas y construir software más confiable.
