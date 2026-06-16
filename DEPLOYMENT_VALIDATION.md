# Validación de Configuración y Despliegue

## Perfil Validado
- **DEV**: Ejecutado y validado localmente con perfiles de desarrollo.
- **TEST**: Validado automáticamente mediante pruebas de integración.
- **PROD**: Ejecutado y validado en contenedores de Docker (usando Dockerfile y Docker Compose).

## Endpoints Verificados

### 1. GET /deploy/config
Muestra la configuración activa del entorno.
- **Respuesta en Docker (PROD)**:
  ```json
  {"version":"1.0.0","environment":"PROD","message":"Entorno de produccion activo"}
  ```

### 2. GET /deploy/health
Muestra el estado de salud y la hora de verificación.
- **Respuesta en Docker (PROD)**:
  ```json
  {"environment":"PROD","status":"OK","checkedAt":"2026-06-16T15:38:42.735349035"}
  ```

### 3. GET /deploy/checklist
Muestra una lista de verificación técnica de despliegue.
- **Respuesta en Docker (PROD)**:
  ```json
  ["perfil activo validado","configuracion externa cargada","api responde correctamente","contenedor listo para despliegue"]
  ```

### 4. GET /deploy/version (Ejercicio Aplicado)
Devuelve únicamente la versión activa cargada de la configuración.
- **Respuesta en Docker (PROD)**:
  ```text
  1.0.0
  ```

---

## Evidencias Técnicas

### 1. Ejecución de Pruebas Automatizadas (./mvnw test)
```text
[INFO] Running pe.unas.demoapi.DeploymentValidationControllerTest
...
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 8.116 s -- in pe.unas.demoapi.DeploymentValidationControllerTest
[INFO] Running pe.unas.demoapi.InternacionalizacionControllerTest
...
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.435 s -- in pe.unas.demoapi.InternacionalizacionControllerTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 13, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### 2. Contenedor Activo (docker ps)
```text
CONTAINER ID   IMAGE            COMMAND               CREATED         STATUS         PORTS                                         NAMES
9eed3fd77d3a   practica21-api   "java -jar app.jar"   3 minutes ago   Up 8 seconds   0.0.0.0:8080->8080/tcp, [::]:8080->8080/tcp   demoapi-sesion22
```

### 3. Evidencia de Peticiones CURL
```bash
$ curl.exe http://localhost:8080/deploy/config
{"version":"1.0.0","environment":"PROD","message":"Entorno de produccion activo"}

$ curl.exe http://localhost:8080/deploy/health
{"environment":"PROD","status":"OK","checkedAt":"2026-06-16T15:38:42.735349035"}

$ curl.exe http://localhost:8080/deploy/version
1.0.0
```

### 4. Registro y Commit en Git
- Rama: `feature/sesion22-validacion-despliegue`
- Pull Request creado en GitHub.
