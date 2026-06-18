# Validacion de configuracion y despliegue

## Perfil validado
- dev: ejecutado localmente
- prod: ejecutado en Docker

## Endpoints verificados
- GET /deploy/config
- GET /deploy/health
- GET /deploy/checklist
- GET /deploy/version

## Evidencias
- Captura de mvn test (BUILD SUCCESS)
- Captura de docker ps
- Captura de curl /deploy/config en dev y prod
- Commit y Pull Request en GitHub