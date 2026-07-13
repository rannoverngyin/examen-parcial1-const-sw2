# Validacion de configuracion y despliegue

## Perfil validado
- dev: ejecutado localmente
- prod: ejecutado en Docker

## Endpoints verificados
- GET /deploy/config

![alt text](image.png)

- GET /deploy/health

![alt text](image-1.png)

- GET /deploy/checklist

![alt text](image-2.png)


## Evidencias
- Captura de mvn test

![alt text](image-3.png)

- Captura de docker ps

![alt text](image-4.png)

- Captura de curl /deploy/config

![alt text](image-5.png)

- Captura de curl /deploy/health

![alt text](image-6.png)

- Captura de curl /deploy/config

![alt text](image-7.png)

- Commit y Pull Request
