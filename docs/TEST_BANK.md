# TEST_BANK.md

## Banco de Pruebas y Manual de Verificación
**Sistema de Gestión Académica - FIIS-UNAS**

Este documento detalla el banco de pruebas diseñado y ejecutado para asegurar el correcto funcionamiento del software bajo diferentes configuraciones operativas, describiendo los escenarios de prueba correspondientes.

---

## 1. Matriz de Casos de Prueba (Test Cases)

| ID | Tipo | Componente / Endpoint | Objetivo | Entradas / Configuración | Resultado Esperado | Estado |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **UT-01** | Unitaria | `EmailNotificadorService` | Verificar el envío aislado de notificaciones por email. | `destino = "correo@unas.edu.pe"` | Mensaje de texto que contiene la palabra `"EMAIL"`. | **Aprobado** |
| **IT-01** | Integración | `/parametros/institucion` | Validar que el endpoint de institución responda correctamente. | Petición `GET` a `/parametros/institucion` | HTTP 200 OK y cuerpo con la palabra `"Universidad"`. | **Aprobado** |
| **IT-02** | Integración | `/parametros/version` | Validar que el endpoint de versión responda correctamente (Ejercicio). | Petición `GET` a `/parametros/version` | HTTP 200 OK y cuerpo con la versión `"1.0.0"`. | **Aprobado** |
| **BT-01** | Borde / Error | Inicialización del Contexto | Verificar la respuesta ante la falta de una propiedad obligatoria. | Comentar `app.institucion` en properties | Fallo de arranque con error `Could not resolve placeholder`. | **Aprobado** |
| **BT-02** | Borde / Error | Inicialización del Contexto | Verificar la respuesta ante tipos de datos incompatibles. | `app.limite-usuarios=cien` | Fallo de arranque por error de conversión de tipos. | **Aprobado** |
| **BT-03** | Borde / Error | Inicialización del Contexto | Verificar comportamiento si no se define un proveedor válido. | `app.notificacion.proveedor=sms` | Fallo de arranque por ausencia del Bean `NotificadorService`. | **Aprobado** |

---

## 2. Detalle de Escenarios de Prueba

### A. Pruebas Unitarias
*   **UT-01: Verificación de EmailNotificadorService**
    *   **Propósito:** Comprobar de forma aislada (sin levantar el servidor web) que la clase de servicio de correo procesa y genera la cadena de respuesta correcta.
    *   **Verificación:** Instanciación manual del servicio, llamada al método de envío y comparación del String resultante.

### B. Pruebas de Integración (MockMvc)
*   **IT-01: Verificación del Endpoint de Institución**
    *   **Propósito:** Validar que el controlador intercepta la ruta `/parametros/institucion`, lee el valor desde el servicio inyectado y responde un código de estado de red correcto.
    *   **Verificación:** Simulación de llamada HTTP GET y validación de cabecera HTTP 200 con aserción del cuerpo.
*   **IT-02: Verificación del Endpoint de Versión (Ejercicio Aplicado)**
    *   **Propósito:** Validar que la nueva propiedad `app.version-sistema` es accesible desde la capa REST externa a través del endpoint `/parametros/version`.
    *   **Verificación:** Petición HTTP GET simulada contra `/parametros/version` verificando el retorno exacto de `"1.0.0"`.

### C. Pruebas de Borde y Escenarios de Error (Boundary Tests)
*   **BT-01: Omisión de Propiedades obligatorias en Properties**
    *   **Comportamiento esperado:** Spring Boot detiene el inicio de la aplicación y arroja `IllegalArgumentException: Could not resolve placeholder`.
*   **BT-02: Tipo de dato no coincidente**
    *   **Comportamiento esperado:** Lanzamiento de `BeanCreationException` y `TypeMismatchException` si el límite de usuarios recibe caracteres no numéricos.
*   **BT-03: Proveedor de notificaciones no soportado**
    *   **Comportamiento esperado:** Falla de inyección de dependencias en `NotificacionController` al no instanciarse ningún bean calificado para `NotificadorService`, arrojando `NoSuchBeanDefinitionException`.

---

## 3. Evidencias de Pruebas (Capturas de Pantalla)

A continuación se adjuntan las capturas de pantalla del funcionamiento correcto de los tests y endpoints en el sistema:

### A. Prueba de Compilación y Test Automatizados
Evidencia de que la ejecución local de `.\mvnw test` finaliza con éxito sin fallos.

![Pruebas Unitarias e Integración Exitosas](images/mvnw_test_success_1781015119839.png)

### B. Endpoint de Parámetros Funcionando
Evidencia de que el servidor responde el nombre de la institución mediante HTTP GET.

![Endpoint Parametros Funcionando](images/api_endpoint_institucion_1781015136263.png)

### C. Notificaciones mediante Proveedor EMAIL
Respuesta obtenida tras realizar la petición HTTP POST con el proveedor configurado en `email`.

![Notificación por Email](images/curl_notificacion_email_1781015151000.png)

### D. Notificaciones mediante Proveedor MOCK
Respuesta obtenida tras realizar la petición HTTP POST tras cambiar la propiedad a `mock`.

![Notificación Simulada Mock](images/curl_notificacion_mock_1781015169348.png)
