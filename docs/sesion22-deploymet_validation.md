# Sesión 22 - Validación de configuración y despliegue

## Paso 1 - Aplicación corriendo con perfil dev

Comando ejecutado:

```bash
./mvnw spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Resultado: la aplicación inició correctamente con el perfil `dev`, mostrando que Tomcat se ejecuta en el puerto 8080.

![Aplicación corriendo en dev](docs/img/sesion22/app_dev_corriendo.png)

---

## Paso 2 - Validación del endpoint de configuración en dev

Endpoint validado:

```http
GET /deploy/config
```

URL utilizada:

```text
http://localhost:8080/deploy/config
```

Resultado esperado:

```json
{"environment":"DEV","version":"1.0.0","message":"Entorno de desarrollo activo"}
```

![Configuración dev](docs/img/sesion22/config_dev.png)

---

## Paso 3 - Validación del endpoint de salud en dev

Endpoint validado:

```http
GET /deploy/health
```

URL utilizada:

```text
http://localhost:8080/deploy/health
```

Resultado: el servicio responde correctamente con estado `OK` y muestra el entorno activo `DEV`.

![Health dev](docs/img/sesion22/health_dev.png)

---

## Paso 4 - Validación del checklist de despliegue

Endpoint validado:

```http
GET /deploy/checklist
```

URL utilizada:

```text
http://localhost:8080/deploy/checklist
```

Resultado: se muestra la lista de validaciones realizadas para el despliegue.

![Checklist dev](docs/img/sesion22/checklist_dev.png)

---

## Paso 5 - Pruebas automatizadas con MockMvc

Comando ejecutado:

```bash
./mvnw test
```

Resultado: `BUILD SUCCESS`, con las pruebas ejecutadas correctamente.

![Pruebas MockMvc](docs/img/sesion22/test_build_success.png)

---

## Paso 6 - Empaquetado de la aplicación

Comando ejecutado:

```bash
./mvnw clean package -DskipTests
```

Resultado: se generó correctamente el archivo `.jar` dentro de la carpeta `target`.

![Empaquetado correcto](docs/img/sesion22/package_build_success.png)

---

## Paso 7 - Ejecución en Docker con perfil prod

Comando ejecutado:

```bash
docker run --name demoapi-sesion22 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod demoapi-sesion22
```

Resultado: la aplicación se ejecutó correctamente dentro del contenedor Docker usando el perfil `prod`.

![Docker run prod](docs/img/sesion22/docker_run_prod.png)

---

## Paso 8 - Validación del endpoint de configuración en prod

Endpoint validado:

```http
GET /deploy/config
```

URL utilizada:

```text
http://localhost:8080/deploy/config
```

Resultado esperado:

```json
{"version":"1.0.0","message":"Entorno de produccion activo","environment":"PROD"}
```

![Configuración prod](docs/img/sesion22/config_prod.png)

---

## Paso 9 - Validación del endpoint de salud en prod

Endpoint validado:

```http
GET /deploy/health
```

URL utilizada:

```text
http://localhost:8080/deploy/health
```

Resultado: el servicio responde correctamente con estado `OK` y muestra el entorno activo `PROD`.

![Health prod](docs/img/sesion22/health_prod.png)

---

## Paso 10 - Despliegue con Docker Compose

Comando ejecutado:

```bash
docker compose up --build
```

Resultado: Docker Compose construyó la imagen y recreó el contenedor `demoapi-sesion22` correctamente.

![Docker Compose](docs/img/sesion22/docker_compose_up.png)

---

## Paso 11 - Validación del checklist con Docker Compose

Endpoint validado:

```http
GET /deploy/checklist
```

URL utilizada:

```text
http://localhost:8080/deploy/checklist
```

Resultado: el endpoint responde correctamente desde el contenedor ejecutado con Docker Compose.

![Checklist Docker Compose](docs/img/sesion22/compose_checklist.png)

---

## Paso 12 - Contenedor activo

Comando ejecutado:

```bash
docker ps
```

Resultado: se verificó que el contenedor `demoapi-sesion22` se encuentra activo y exponiendo el puerto 8080.

![Docker ps](docs/img/sesion22/docker_ps.png)

---

## Paso 13 - Ejercicio aplicado: endpoint de versión

Como actividad aplicada de la sesión, se agregó el endpoint:

```http
GET /deploy/version
```

Este endpoint permite consultar directamente la versión actual de la aplicación, leyendo el valor configurado en la propiedad:

```properties
app.version=1.0.0
```

Para ello, se agregó el método `version()` en el servicio `DeploymentValidationService` y se expuso la ruta `/deploy/version` en el controlador `DeploymentValidationController`.

Comando de validación ejecutado:

```bash
curl http://localhost:8080/deploy/version
```

Resultado esperado:

```text
1.0.0
```

![Endpoint versión](docs/img/sesion22/version_endpoint.png)

---

## Paso 14 - Prueba de integración del endpoint de versión

Se agregó una prueba de integración con MockMvc para verificar que el endpoint `/deploy/version` responda correctamente con estado HTTP 200 y que el contenido incluya la versión `1.0.0`.

Comando ejecutado:

```bash
./mvnw test
```

Resultado: `BUILD SUCCESS`, confirmando que las pruebas automatizadas se ejecutaron correctamente.

![Prueba endpoint versión](docs/img/sesion22/version_test_success.png)


## Conclusión

La aplicación Spring Boot fue configurada correctamente para trabajar con diferentes perfiles de entorno. Se validó la ejecución local con el perfil `dev`, la ejecución en contenedor Docker con el perfil `prod`, la respuesta de los endpoints de validación y la ejecución de pruebas automatizadas. Además, se documentaron las evidencias necesarias para sustentar el proceso de configuración y despliegue.
