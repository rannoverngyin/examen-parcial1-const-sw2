# CONFIGURATION.md

## Manual de Parametrización y Configuración Externa
**Sistema de Gestión Académica - FIIS-UNAS**

Este artefacto documental detalla las propiedades de configuración externa y las reglas de parametrización del sistema. La gestión de estas variables permite adaptar el comportamiento operativo del backend (variabilidad del software) sin alterar el código binario compilado (.class/.jar).

---

## 1. Arquitectura de Inyección de Parámetros

El sistema utiliza la anotación `@Value` de Spring Framework para resolver expresiones a partir del contexto del entorno (cargado desde `application.properties`, variables del sistema, de entorno o argumentos CLI).

```mermaid
graph TD
    A[Orígenes de Configuración] --> B(application.properties)
    A --> C(Variables de Entorno)
    A --> D(Argumentos de Línea de Comando)
    B --> E[Spring Environment]
    C --> E
    D --> E
    E -->|@Value| F[ParametroService]
    E -->|@ConditionalOnProperty| G[NotificadorService Beans]
```

---

## 2. Matriz de Propiedades y Parámetros Técnicos

| Propiedad | Tipo | Ejemplo de Valor | Inyección en Java | Descripción y Detalle de Impacto |
| :--- | :--- | :--- | :--- | :--- |
| `spring.application.name` | Texto | `examen-parcial1-const-sw2` | Inyectada por el framework | Identificador lógico de la aplicación para logs y registro de microservicios. |
| `server.port` | Numérico | `8080` | Configuración de Tomcat | Puerto de red TCP en el cual el servidor HTTP embebido escucha las peticiones REST. |
| `app.institucion` | Texto | `Universidad Nacional Agraria de la Selva` | `@Value("${app.institucion}")` | Nombre oficial de la institución que se despliega en las vistas o cabeceras del sistema. |
| `app.modo` | Texto | `ACADEMICO` | `@Value("${app.modo}")` | Modula el comportamiento del sistema (ej. `ACADEMICO`, `ADMINISTRATIVO`, `DESARROLLO`). |
| `app.limite-usuarios` | Entero | `100` | `@Value("${app.limite-usuarios}")` | Límite máximo de usuarios concurrentes. Determina el umbral de control de concurrencia. |
| `app.notificacion.proveedor` | Texto | `email` | Usado en `@ConditionalOnProperty` | Controla la instanciación condicional de beans. Los valores válidos son `email` y `mock`. |
| `app.version-sistema` | Texto | `1.0.0` | `@Value("${app.version-sistema}")` | Versión técnica expuesta para la trazabilidad y auditoría de despliegues (Ejercicio Aplicado). |

---

## 3. Detalle de Inyección y Consumo por Parámetro

### A. Nombre de la Institución (`app.institucion`)
*   **Tipo en Java:** `java.lang.String`
*   **Código de Consumo:**
    ```java
    @Value("${app.institucion}")
    private String institucion;
    ```
*   **Impacto de Modificación:** Al alterar este valor en el archivo de propiedades, el endpoint `/parametros/institucion` retornará inmediatamente el nuevo nombre asignado al arrancar la aplicación.

### B. Modo del Sistema (`app.modo`)
*   **Tipo en Java:** `java.lang.String`
*   **Código de Consumo:**
    ```java
    @Value("${app.modo}")
    private String modo;
    ```
*   **Impacto de Modificación:** Define qué perfiles o lógicas de negocio académicas se activan dentro de los servicios del sistema.

### C. Límite de Usuarios (`app.limite-usuarios`)
*   **Tipo en Java:** `int` (tipo primitivo entero)
*   **Código de Consumo:**
    ```java
    @Value("${app.limite-usuarios}")
    private int limiteUsuarios;
    ```
*   **Impacto de Modificación:** Regula la cantidad de sesiones. Si se introduce un valor no numérico, la inicialización de Spring fallará debido a un error de conversión.

### D. Proveedor de Alertas (`app.notificacion.proveedor`)
*   **Tipo en Java:** `java.lang.String`
*   **Uso en Inyección Condicional:**
    ```java
    @ConditionalOnProperty(name = "app.notificacion.proveedor", havingValue = "email")
    ```
*   **Impacto de Modificación:** Define qué Bean concreto se inyectará en el controlador `NotificacionController`. Si es `email`, se inyecta `EmailNotificadorService` y el flujo devuelve correos reales. Si es `mock`, se inyecta `MockNotificadorService` para pruebas de simulación offline.

### E. Versión de Sistema (`app.version-sistema`)
*   **Tipo en Java:** `java.lang.String`
*   **Código de Consumo:**
    ```java
    @Value("${app.version-sistema}")
    private String versionSistema;
    ```
*   **Impacto de Modificación:** Permite saber en producción de forma remota cuál versión exacta del código del parcial se encuentra desplegada llamando a `/parametros/version`.

---

## 4. Mecanismos de Sobrescritura en Caliente (Runtime Overrides)

De acuerdo con el principio de variabilidad, los parámetros definidos en `application.properties` pueden ser sobrescritos en el arranque sin modificar el archivo físico mediante los siguientes métodos:

### Opción A: Parámetros por Línea de Comando (CLI Arguments)
Al arrancar la aplicación empaquetada, se pueden pasar los parámetros como argumentos:
```bash
java -jar target/demoapi-1.0.0.jar --app.modo=DESARROLLO --app.limite-usuarios=500
```
*   **Resultado:** El modo pasará de `ACADEMICO` a `DESARROLLO` y el límite de usuarios concurrentes pasará de `100` a `500`.

### Opción B: Variables de Entorno (OS Environment Variables)
Spring Boot traduce variables de entorno del sistema operativo automáticamente:
```bash
# En Windows PowerShell
$env:APP_NOTIFICACION_PROVEEDOR="mock"
java -jar target/demoapi-1.0.0.jar
```
*   **Resultado:** El sistema iniciará usando el proveedor `mock` de notificaciones en lugar del valor configurado en el archivo properties.
