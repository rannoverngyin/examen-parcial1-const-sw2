# examen-parcial1-const-sw2 — Sesion 22: Validacion de configuracion y despliegue

Java 17 + Spring Boot 3.2.5 + perfiles + Docker + GitHub

## Ejecutar localmente por perfil

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

```
curl http://localhost:8080/deploy/config
curl http://localhost:8080/deploy/health
curl http://localhost:8080/deploy/checklist
curl http://localhost:8080/deploy/version
```

Resultado esperado para `/deploy/config` en perfil dev:

```json
{"environment":"DEV","version":"1.0.0","message":"Entorno de desarrollo activo"}
```

## Pruebas automatizadas

```
./mvnw test
```

## Empaquetar

```
./mvnw clean package -DskipTests
```

## Construir y ejecutar en Docker

```
docker build -t demoapi-sesion22 .
docker run --name demoapi-sesion22 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod demoapi-sesion22
```

Resultado esperado dentro del contenedor (perfil prod):

```json
{"environment":"PROD","version":"1.0.0","message":"Entorno de produccion activo"}
```

## Despliegue con Docker Compose

```
docker compose up --build
```

## Ejercicio aplicado

Se agrego el endpoint `GET /deploy/version`, que devuelve `app.version` desde la
configuracion activa, junto con su prueba de integracion correspondiente
(`debeResponderVersionCorrecta`) en `DeploymentValidationControllerTest`.
