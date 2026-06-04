# Documentación Técnica: Configuración de Perfiles - Sesión 18

Este documento recopila detalladamente la configuración de entornos utilizando perfiles (`dev`, `test`, `prod`) y las verificaciones manuales realizadas para el laboratorio de la FIIS-UNAS.

## 1. Archivos de Propiedades Generados

Se han creado tres archivos de propiedades específicos de entorno en `src/main/resources/` con las propiedades requeridas y la extensión de soporte técnico (`app.soporte`):

1. **`application-dev.properties`**:
   - `app.entorno=dev`
   - `app.mensaje=Entorno de desarrollo local activo`
   - `app.version=1.0.0-dev`
   - `app.soporte=soporte-dev@unas.edu.pe`

2. **`application-test.properties`**:
   - `app.entorno=test`
   - `app.mensaje=Entorno de pruebas automatizadas activo`
   - `app.version=1.0.0-test`
   - `app.soporte=soporte-test@unas.edu.pe`

3. **`application-prod.properties`** (con soporte para variables de entorno):
   - `app.entorno=prod`
   - `app.mensaje=${APP_MENSAJE:Entorno de produccion activo en la nube}`
   - `app.version=1.0.0-release`
   - `app.soporte=${SOPORTE_EMAIL:soporte@unas.edu.pe}`

Adicionalmente, se configuró `application.properties` con `spring.profiles.active=dev` para forzar que el perfil predeterminado sea `dev`.

## 2. Pruebas y Resultados Obtenidos

### 2.1. Entorno de Desarrollo (dev)
* **Comando ejecutado:**
  ```powershell
  mvn spring-boot:run
  ```
  *(Por defecto utiliza `dev` configurado en `application.properties`)*

* **Consulta a `/config/info`:**
  ```powershell
  Invoke-RestMethod -Uri http://localhost:8080/config/info
  ```
  **Resultado:**
  ```json
  {
    "mensaje": "Entorno de desarrollo local activo",
    "version": "1.0.0-dev",
    "entorno": "dev"
  }
  ```

* **Consulta a `/config/soporte`:**
  ```powershell
  Invoke-RestMethod -Uri http://localhost:8080/config/soporte
  ```
  **Resultado:**
  ```text
  soporte-dev@unas.edu.pe
  ```

---

### 2.2. Entorno de Pruebas (test)
* **Comando ejecutado:**
  ```powershell
  mvn spring-boot:run "-Dspring-boot.run.profiles=test"
  ```

* **Consulta a `/config/info`:**
  ```powershell
  Invoke-RestMethod -Uri http://localhost:8080/config/info
  ```
  **Resultado:**
  ```json
  {
    "mensaje": "Entorno de pruebas automatizadas activo",
    "version": "1.0.0-test",
    "entorno": "test"
  }
  ```

* **Consulta a `/config/soporte`:**
  ```powershell
  Invoke-RestMethod -Uri http://localhost:8080/config/soporte
  ```
  **Resultado:**
  ```text
  soporte-test@unas.edu.pe
  ```

---

### 2.3. Entorno de Producción (prod) con Variables de Entorno
* **Comando ejecutado (declarando variables de entorno en Windows PowerShell):**
  ```powershell
  $env:APP_MENSAJE='Mensaje Personalizado de Prod'
  $env:SOPORTE_EMAIL='admin-soporte@unas.edu.pe'
  mvn spring-boot:run "-Dspring-boot.run.profiles=prod"
  ```

* **Consulta a `/config/info`:**
  ```powershell
  Invoke-RestMethod -Uri http://localhost:8080/config/info
  ```
  **Resultado (sobreescrito dinámicamente):**
  ```json
  {
    "mensaje": "Mensaje Personalizado de Prod",
    "version": "1.0.0-release",
    "entorno": "prod"
  }
  ```

* **Consulta a `/config/soporte`:**
  ```powershell
  Invoke-RestMethod -Uri http://localhost:8080/config/soporte
  ```
  **Resultado (sobreescrito dinámicamente):**
  ```text
  admin-soporte@unas.edu.pe
  ```

---

## 3. Conclusiones del Laboratorio

1. **Variabilidad Dinámica a nivel de Entorno:** El uso de perfiles en Spring Boot permite desacoplar los parámetros de configuración (puertos, credenciales, niveles de logs, etc.) del código fuente, habilitando que una misma base de código se adapte automáticamente al contexto en el que se ejecuta.
2. **Inyección con Tolerancia a Fallos:** La sintaxis `${VARIABLE:fallback}` permite que la aplicación sea altamente robusta (cloud-friendly/container-friendly). Si una variable del sistema como `SOPORTE_EMAIL` está presente, Spring Boot la lee dinámicamente; de lo contrario, utiliza un fallback seguro por defecto (`soporte@unas.edu.pe`).
3. **Clean Architecture preservada:** El controlador delega de forma directa al servicio de configuración las operaciones de lectura, manteniendo limpia la capa de entrada del sistema y aislando la configuración.
