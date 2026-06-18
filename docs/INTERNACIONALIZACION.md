# Validación de configuración y despliegue

## Información general

**Proyecto:** examen-parcial1-const-sw2

**Tecnologías utilizadas:**

* Java 17
* Spring Boot
* Maven
* Docker
* Git y GitHub

## Perfiles validados

### Entorno de desarrollo (DEV)

La aplicación fue ejecutada localmente utilizando el perfil de desarrollo para verificar la carga correcta de la configuración por entorno.

Comando utilizado:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Resultado:

* Aplicación iniciada correctamente.
* Configuración DEV cargada satisfactoriamente.
* Endpoints accesibles desde localhost.

### Entorno de producción (PROD)

La aplicación fue desplegada mediante Docker utilizando el perfil de producción.

Comando utilizado:

```bash
docker run --name demoapi-sesion22 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod demoapi-sesion22
```

Resultado:

* Contenedor iniciado correctamente.
* Perfil PROD cargado satisfactoriamente.
* API accesible desde el puerto 8080.

---

## Endpoints verificados

### GET /deploy/config

Respuesta obtenida:

```json
{
  "version": "1.0.0",
  "message": "Entorno de produccion activo",
  "environment": "PROD"
}
```

Validación:

* Configuración cargada correctamente.
* Perfil de producción activo.
* Versión de la aplicación obtenida desde configuración.

### GET /deploy/health

Respuesta obtenida:

```json
{
  "checkedAt": "2026-06-18T13:43:43.450202441",
  "status": "OK",
  "environment": "PROD"
}
```

Validación:

* Estado del servicio correcto.
* Endpoint operativo.
* Información de entorno disponible.

### GET /deploy/checklist

Respuesta obtenida:

```json
[
  "perfil activo validado",
  "configuracion externa cargada",
  "api responde correctamente",
  "contenedor listo para despliegue"
]
```

Validación:

* Checklist de despliegue completado.
* Respuesta correcta del endpoint.

---

## Pruebas automatizadas

Comando ejecutado:

```bash
./mvnw test
```

Resultado:

```text
Tests run: 3
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```
---

## Construcción del artefacto

Comando ejecutado:

```bash
./mvnw clean package -DskipTests
```

Resultado:

* Archivo JAR generado correctamente dentro del directorio target.
* Aplicación lista para despliegue.

---

## Despliegue con Docker

Comandos ejecutados:

```bash
docker build -t demoapi-sesion22 .
docker run --name demoapi-sesion22 -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod demoapi-sesion22
```

Resultado:

* Imagen Docker construida correctamente.
* Contenedor ejecutado satisfactoriamente.
* Endpoints accesibles desde el host.

---

## Conclusiones

La aplicación Spring Boot fue configurada correctamente para trabajar con múltiples perfiles de entorno (DEV, TEST y PROD). Se verificó el funcionamiento de los endpoints de validación, se ejecutaron satisfactoriamente las pruebas automatizadas y se realizó el despliegue exitoso mediante Docker. El sistema quedó listo para su liberación y futuras etapas de integración continua.
