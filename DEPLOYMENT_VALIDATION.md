# Validacion de configuracion y despliegue

## Perfil validado

- dev: ejecutado localmente
- prod: ejecutado en Docker
- prod: ejecutado con Docker Compose

## Endpoints verificados

- GET /deploy/config
- GET /deploy/health
- GET /deploy/checklist
- GET /deploy/version

## Evidencias

- Captura de ./mvnw.cmd test con BUILD SUCCESS
- Captura de curl /deploy/config en perfil dev
- Captura de docker build
- Captura de docker ps mostrando el contenedor activo
- Captura de curl /deploy/config en perfil prod dentro de Docker
- Captura de docker compose up --build
- Captura de curl /deploy/checklist usando Docker Compose
- Commit y Pull Request

## Resultado de validacion local

Se ejecuto la aplicacion con el perfil dev:

```bash
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"