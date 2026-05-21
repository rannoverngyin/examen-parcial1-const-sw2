# Guía de práctica – Sesión 13
## Mantenimiento del banco de pruebas

Curso: Construcción de Software II  
Unidad: Unidad II – Pruebas unitarias  
Tema: Mantenimiento del banco de pruebas, automatización y documentación de pruebas  
Producto esperado: Banco de pruebas organizado + test de regresión + workflow de GitHub Actions

## Objetivo de la práctica
Mantener y organizar el banco de pruebas de una aplicación Spring Boot, incorporando pruebas unitarias de regresión, documentación mínima de casos de prueba y automatización de ejecución con Maven y GitHub Actions.

Al finalizar, se debe:
- Identificar qué es un banco de pruebas y por qué debe mantenerse.
- Organizar pruebas por capa o responsabilidad.
- Agregar pruebas de regresión ante nuevas reglas del sistema.
- Documentar pruebas unitarias en `TEST_BANK.md`.
- Automatizar la ejecución de pruebas con GitHub Actions.

## Requisitos previos
- Java 17 activo.
- Maven funcionando (`mvn -version` o `./mvnw -version`).
- Dependencia `spring-boot-starter-test` en `pom.xml`.
- Repositorio Git local y remoto.

## Desarrollo paso a paso
### 1) Ubicarse en la raíz del proyecto
Verificar que existan `pom.xml`, `src/`, `mvnw`, `mvnw.cmd` y `README.md`.

### 2) Crear rama de trabajo
Usar una rama de práctica, por ejemplo:
- `feature/sesion13-banco-pruebas`

### 3) Verificar implementación de `ProductoService`
Archivo objetivo: `src/main/java/pe/unas/demoapi/application/ProductoService.java`

Reglas principales:
- Productos iniciales: `Laptop` y `Mouse`.
- Rechazar nombre vacío o en blanco (`IllegalArgumentException`).
- Rechazar productos duplicados (`IllegalArgumentException` con mensaje `El producto ya existe`).
- Soportar listar, agregar, eliminar, total y verificación de existencia.

### 4) Mantener banco de pruebas unitarias
Archivo objetivo: `src/test/java/pe/unas/demoapi/application/ProductoServiceTest.java`

Casos mínimos:
- Debe listar productos iniciales.
- Debe agregar producto válido.
- Debe eliminar producto existente.
- No debe aceptar producto vacío.
- No debe aceptar producto duplicado (regresión).

### 5) Ejecutar pruebas
Comando:
- `./mvnw test`

Resultado esperado:
- `BUILD SUCCESS`
- pruebas sin fallos.

### 6) Documentar banco de pruebas
Actualizar `TEST_BANK.md` con:
- módulo evaluado,
- casos de prueba con prioridad y resultado esperado,
- comando de ejecución,
- evidencia de ejecución.

### 7) Automatizar con GitHub Actions
Workflow requerido en `.github/workflows/tests.yml`:
- disparador por `push` y `pull_request`,
- Java 17 (Temurin),
- permisos de ejecución a `mvnw`,
- ejecución de `./mvnw test`.

### 8) Registrar evidencia con Git
- `git status`
- `git add .`
- `git commit`
- `git push`
- crear Pull Request hacia `main`.

## Ejercicio aplicado
Agregar la regla: no se permite registrar un producto duplicado.

Flujo esperado:
1. Crear primero la prueba que falle para duplicado.
2. Ajustar `ProductoService.agregar()` para cumplir la regla.
3. Ejecutar `./mvnw test`.
4. Actualizar `TEST_BANK.md` con el caso `PU-05`.

## Evidencias de entrega
- Captura de consola con `./mvnw test` y `BUILD SUCCESS`.
- `ProductoServiceTest.java` implementado/actualizado.
- `TEST_BANK.md` actualizado.
- `.github/workflows/tests.yml` creado/actualizado.
- Commit y push en rama `feature`.
- Pull Request creado en GitHub.

## Errores frecuentes y solución
- No encuentra JUnit: revisar `spring-boot-starter-test` en `pom.xml`.
- Tests fallan por datos compartidos: usar `@BeforeEach`.
- GitHub Actions falla por permisos: incluir `chmod +x mvnw`.
- Pruebas dependientes del orden: cada test debe ser independiente.
- Workflow no aparece: validar ruta exacta `.github/workflows/tests.yml` y hacer push.

## Rúbrica de evaluación
- Organización del banco de pruebas: 4
- Pruebas unitarias: 5
- Documentación (`TEST_BANK.md`): 4
- Automatización CI: 4
- Git y evidencia: 3

Total: 20 puntos.
