#nano DEPLOYMENT_VALIDATION.md
# Validacion de configuracion y despliegue

## Perfil validado
- dev: ejecutado localmente
![alt text](image-7.png)
![alt text](image-8.png)
- prod: ejecutado en Docker
![alt text](image-9.png)
![alt text](image-10.png)

## Endpoints verificados
- GET /deploy/config
- GET /deploy/health
- GET /deploy/checklist
![alt text](image-3.png)

## Evidencias
- Captura de mvn test
![alt text](image-1.png)
- Captura de docker ps
- Captura de curl /deploy/config, /deploy/health
![alt text](image-2.png)
- Captura de curl /deploy/checklist
![alt text](image-4.png)

- ejercicio aplicado 
Agregar un nuevo endpoint GET /deploy/version que devuelva solo la version de la aplicacion. Debe leer app.version desde la configuracion y tener una prueba de integracion que valide que responde HTTP 200 y contiene 1.0.0.
 TEST ![alt text](image-5.png)
 CURL
 ![alt text](image-6.png)

- Commit y Pull Request



