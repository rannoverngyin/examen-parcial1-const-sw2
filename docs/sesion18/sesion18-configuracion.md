# Sesión 18 — Configuración dinámica y perfiles de entorno

**Curso:** Construcción de Software II  
**Unidad:** III — Variabilidad y configuración del software  
**Tema:** Configuración dinámica, perfiles `dev` / `test` / `prod` y variables de entorno  
**Proyecto:** `examen-parcial1-const-sw2`  
**Rama de trabajo:** `feature/s18-configuracion-perfiles`

---

## 1. Descripción general

En esta sesión se implementó un mecanismo de configuración dinámica en una aplicación Spring Boot, utilizando **perfiles de entorno** (`dev`, `test`, `prod`) y **variables de entorno del sistema operativo**. El objetivo principal es que el mismo código fuente pueda ejecutarse en distintos entornos sin necesidad de modificaciones, cambiando únicamente los archivos de propiedades o las variables del sistema.

---

## 2. Archivos de configuración creados

Se crearon los siguientes archivos dentro de `src/main/resources/`:

| Archivo | Perfil | Propósito |
|---|---|---|
| `application.properties` | — | Configuración base y perfil activo por defecto |
| `application-dev.properties` | `dev` | Propiedades para desarrollo local |
| `application-test.properties` | `test` | Propiedades para pruebas automatizadas |
| `application-prod.properties` | `prod` | Propiedades para despliegue en producción |

### `application.properties`
```properties
spring.application.name=examen-parcial1-const-sw2
server.port=8080
spring.profiles.active=dev
```

### `application-dev.properties`
```properties
app.entorno=dev
app.mensaje=Entorno de desarrollo FIIS
app.version=1.0-DEV
```

### `application-test.properties`
```properties
app.entorno=test
app.mensaje=Entorno de pruebas FIIS
app.version=1.0-TEST
```

### `application-prod.properties`
```properties
app.entorno=prod
app.mensaje=${APP_MENSAJE:Entorno de produccion FIIS}
app.version=1.0-PROD
```

> En producción, el valor de `app.mensaje` puede ser sobreescrito por la variable de entorno `APP_MENSAJE`. Si no está definida, se usa el valor por defecto indicado.

---

## 3. Componentes implementados

### `ConfiguracionService.java` — capa `application`

Lee las propiedades del perfil activo mediante la anotación `@Value` e implementa sus respectivos métodos de acceso (`obtenerEntorno()`, `obtenerMensaje()`, `obtenerVersion()`).

### `ConfiguracionController.java` — capa `presentation`

Expone tres endpoints REST bajo la ruta base `/config`:

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/config/entorno` | Devuelve el nombre del entorno activo |
| GET | `/config/mensaje` | Devuelve el mensaje configurado para el entorno |
| GET | `/config/info` | Devuelve un JSON con entorno, mensaje y versión |

---

## 4. Evidencia de ejecución

### 4.1 Perfil `dev`

**Comando ejecutado:**
```cmd
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

**Verificación:**
```cmd
curl http://localhost:8080/config/info
```

**Resultado obtenido:**
```json
{
  "entorno": "dev",
  "mensaje": "Entorno de desarrollo FIIS",
  "version": "1.0-DEV"
}
```

**Log de inicio (extracto):**
```
The following 1 profile is active: "dev"
Tomcat started on port 8080 (http)
Started ExamenParcial1ConstSw2Application in 3.523 seconds
```

---

### 4.2 Perfil `prod` con variable de entorno

**Variable de entorno definida en PowerShell:**
```powershell
$env:APP_MENSAJE="Sistema FIIS en produccion"
```

**Comando ejecutado:**
```cmd
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

**Verificación:**
```cmd
curl http://localhost:8080/config/info
```

**Resultado obtenido:**
```json
{
  "entorno": "prod",
  "mensaje": "Sistema FIIS en produccion",
  "version": "1.0-PROD"
}
```

> El mensaje tomó el valor de la variable de entorno `APP_MENSAJE`, demostrando que la configuración es sobreescribible en tiempo de ejecución sin modificar el código fuente.

---

## 5. Registro en Git

```bash
git add .
git commit -m "Implementa configuracion dinamica y perfiles"
git push origin feature/s18-configuracion-perfiles
```

---

## 6. Conclusión

La implementación de perfiles de entorno en Spring Boot permite desplegar la misma aplicación en contextos de desarrollo, pruebas y producción con comportamiento controlado. Las propiedades cambian por entorno sin alterar el código fuente, y el uso de variables de entorno del sistema operativo añade una capa adicional de flexibilidad para entornos productivos. Este principio es fundamental en el desarrollo de software profesional y en pipelines de integración y despliegue continuo (CI/CD).

---

*FIIS — UNAS | Construcción de Software II | Sesión 18*
