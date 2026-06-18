# Validacion de configuracion y despliegue

## Perfil validado
- dev: ejecutado localmente
![alt text](dev-exe.png)
- prod: ejecutado en Docker
![alt text](prod-exe.png)

## Endpoints verificados
- GET /deploy/config
- GET /deploy/health
- GET /deploy/checklist

![alt text](end-dev.png)

![alt text](end-prod.png)

## Evidencias
- Captura de mvn test
![alt text](test.png)
- Captura de docker ps
![alt text](docker.png)
- Captura de curl /deploy/config
![alt text](capture.png)
- Commit y Pull Request
