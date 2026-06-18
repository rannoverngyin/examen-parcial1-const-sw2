# Comandos Docker - Sesión 22

## Paso 14: Construir y ejecutar contenedor Docker

### 1. Compilar el proyecto (generar el JAR)
```bash
./mvnw package -DskipTests
```

### 2. Construir la imagen Docker
```bash
docker build -t demoapi-sesion22 .
```

### 3. Ejecutar el contenedor
```bash
docker run --name demoapi-sesion22 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod -d demoapi-sesion22
```

### 4. Verificar endpoints (en otra terminal)
```bash
curl http://localhost:8080/deploy/config
curl http://localhost:8080/deploy/health
```

**Resultado esperado `/deploy/config`:**
```json
{
  "environment": "PROD",
  "version": "1.0.0",
  "message": "Entorno de produccion activo"
}
```

---

## Paso 15: Despliegue con Docker Compose

### 1. Eliminar el contenedor anterior (liberar puerto y nombre)
```bash
docker rm -f demoapi-sesion22
```

### 2. Levantar con Docker Compose
```bash
docker compose up --build
```

### 3. Verificar endpoint checklist (en otra terminal)
```bash
curl http://localhost:8080/deploy/checklist
```

### 4. Detener los servicios
```bash
docker compose down
```

---

## Fixes aplicados durante la sesión

| Problema | Solución |
|---|---|
| PowerShell rompe `-Dspring-boot.run.profiles=dev` | Usar comillas: `"-Dspring-boot.run.profiles=dev"` |
| Typo `app.envirroment` en `DeploymentValidationService.java` | Corregido a `app.environment` |
| Puerto 8080 ocupado por proceso anterior | `taskkill /PID <pid> /F` |
| `.dockerignore` excluía `target/` | Cambiado a `target/*` + `!target/*.jar` |



git checkout -b feature/sesion22-validacion-despliegue_tapullima-meselemias
git push -u origin feature/sesion22-validacion-despliegue_tapullima-meselemias