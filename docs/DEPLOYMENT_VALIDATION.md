# Validacion de configuracion y despliegue

## Perfil validado
- dev: ejecutado localmente
- prod: ejecutado en Docker

## Endpoints verificados
- GET `/deploy/config`
- GET `/deploy/health`
- GET `/deploy/checklist`
- GET `/deploy/version` (Ejercicio aplicado)

## Evidencias

**Captura de mvn test**
![Evidencia mvn test](./e1_mvnw_test.png)

**Captura de curl /deploy/config (Local DEV)**
![Evidencia config local](./e2_curl_config_dev.png)

**Captura de docker ps**
![Evidencia docker ps](./e3_docker_ps.png)

**Captura de curl /deploy/config (Docker PROD)**
![Evidencia config docker](./e4_curl_config_prod.png)
