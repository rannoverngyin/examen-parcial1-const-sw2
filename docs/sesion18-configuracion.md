# Sesión 18 - Configuración dinámica y perfiles

Este documento detalla la implementación de variabilidad en la API REST mediante perfiles de entorno (`dev`, `test`, `prod`), variables de entorno, y la resolución de la colisión de PowerShell con Maven.

---

## 💻 Entorno de Desarrollo (dev)

El perfil activo por defecto se define en `application.properties`:
```properties
spring.profiles.active=dev
```

### Ejecución
Comando ejecutado en PowerShell para iniciar el entorno de desarrollo:
```powershell
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Resultados Obtenidos
Peticiones `curl` enviadas al endpoint `/config/info` y `/config/soporte`:

```powershell
curl http://localhost:8080/config/info
# Retorna:
# {
#   "entorno": "dev",
#   "mensaje": "Entorno de desarrollo FIIS",
#   "version": "1.0-DEV",
#   "soporte": "soporte-dev@unas.edu.pe"
# }

curl http://localhost:8080/config/soporte
# Retorna: soporte-dev@unas.edu.pe
```

### Evidencia Visual
![Evidencia de ejecución en entorno de desarrollo (dev)](./terminal_evidence_dev.png)

---

## 🚀 Entorno de Producción (prod)

En el entorno de producción, la API permite sobrescribir los valores por defecto mediante variables de entorno del sistema (`APP_MENSAJE` y `SOPORTE_EMAIL`), utilizando las expresiones de fallback en `application-prod.properties`.

### Ejecución
Comando ejecutado para configurar las variables de entorno e iniciar la aplicación en PowerShell:
```powershell
$env:APP_MENSAJE="Sistema FIIS en produccion"
$env:SOPORTE_EMAIL="soporte-corporativo@unas.edu.pe"
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Resultados Obtenidos
Respuestas de las peticiones a la API:

```powershell
curl http://localhost:8080/config/info
# Retorna:
# {
#   "entorno": "prod",
#   "mensaje": "Sistema FIIS en produccion",
#   "version": "1.0-PROD",
#   "soporte": "soporte-corporativo@unas.edu.pe"
# }

curl http://localhost:8080/config/soporte
# Retorna: soporte-corporativo@unas.edu.pe
```

### Evidencia Visual
![Evidencia de ejecución en entorno de producción (prod) con variables de entorno](./terminal_evidence_prod.png)

---

## 🧪 Entorno de Pruebas (test)

El perfil `test` se utiliza para la ejecución automática de la suite de pruebas del proyecto. 

Comando de verificación de Maven:
```powershell
./mvnw verify
```

### Pruebas de Integración Ejecutadas
- **ConfiguracionControllerDevTest**: Inicia el contexto con `@ActiveProfiles("dev")` y valida que la API retorne los valores de desarrollo.
- **ConfiguracionControllerTestTest**: Inicia el contexto con `@ActiveProfiles("test")` y valida que retorne los valores del perfil de pruebas (`soporte-test@unas.edu.pe`).
- **ConfiguracionControllerProdTest**: Inicia el contexto con `@ActiveProfiles("prod")` y valida que retorne los valores correspondientes al perfil de producción.

Todos los tests finalizan con éxito y la cobertura de JaCoCo supera el 70%.

---

## 🔬 Ejercicio Aplicado: Soporte Técnico

Se agregó la propiedad configurable `app.soporte` en los perfiles:
- **dev**: `soporte-dev@unas.edu.pe`
- **test**: `soporte-test@unas.edu.pe`
- **prod**: `${SOPORTE_EMAIL:soporte@unas.edu.pe}` (sobrescribible con la variable de entorno `SOPORTE_EMAIL`).

Y se expuso un endpoint GET `/config/soporte` que devuelve directamente esta dirección de correo electrónico.

---

## 🛠️ Resolución de la Colisión con PowerShell
Se solventó la fragmentación que hace PowerShell al enviar argumentos con puntos y signos de igual a Maven. Adicionalmente, se implementó un filtro en `mvnw.cmd` que identifica propiedades de configuración y las une de forma forzada para evitar colisiones con fases del ciclo de vida de Maven como `test`.

## 📌 Conclusión
El comportamiento de la aplicación varía dinámicamente según el perfil activado en tiempo de ejecución, sin necesidad de modificar o recompilar el código fuente, logrando una arquitectura limpia y configurable por entornos.
