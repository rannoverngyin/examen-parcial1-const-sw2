# Sesión 18 - Configuración dinámica y perfiles

## Resumen
Se añadieron perfiles `dev`, `test` y `prod` con propiedades externas y soporte para variables de entorno. Se expusieron endpoints para verificar la configuración:

- `GET /config/entorno`
- `GET /config/mensaje`
- `GET /config/info`
- `GET /config/soporte`

## Comandos usados (Windows PowerShell)

Iniciar con perfil `dev`:

```powershell
$env:SPRING_PROFILES_ACTIVE='dev'
.\mvnw.cmd spring-boot:run
```

Parar la aplicación: Ctrl + C

Iniciar con perfil `test`:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=test
```

Iniciar con perfil `prod` sobrescribiendo valores desde variables de entorno:

```powershell
$env:APP_MENSAJE='Sistema FIIS en produccion'
$env:SOPORTE_EMAIL='soporte@unas.edu.pe'
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

## Evidencia (ejecución en perfil `dev`)

Comando ejecutado:

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/config/info' | ConvertTo-Json -Compress
Invoke-RestMethod -Uri 'http://localhost:8080/config/soporte'
```

Resultado observado:

```
{"soporte":"soporte-dev@unas.edu.pe","version":"1.0-DEV","mensaje":"Entorno de desarrollo FIIS","entorno":"dev"}
soporte-dev@unas.edu.pe
```

## Validación esperada

- Perfil `test`: `/config/info` debe devolver `entorno=test` y `mensaje=Entorno de pruebas FIIS`.
- Perfil `prod`: al definir `APP_MENSAJE` y/o `SOPORTE_EMAIL` en el entorno, `/config/info` y `/config/soporte` deben reflejar los valores proporcionados.

## Notas

- Se agregó la propiedad `app.soporte` en `application-*.properties` y las clases `ConfiguracionService` y `ConfiguracionController` para exponer el endpoint `/config/soporte`.
- La rama con los cambios es `feature/garcia_jorge_s18-configuracion-perfiles` y ya fue empujada al remoto.

## Conclusión

La aplicación carga propiedades por perfil y permite sobrescribir valores críticos desde variables de entorno sin modificar código fuente.
