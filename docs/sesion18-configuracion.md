# Sesión 18 - Configuración dinámica y perfiles

## Perfil dev
Comando ejecutado:
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Resultado:
- `GET /config/entorno` → `dev`
- `GET /config/mensaje` → `Entorno de desarrollo FIIS`
- `GET /config/version` → (incluido en /config/info)
- `GET /config/info` → `{"entorno":"dev","mensaje":"Entorno de desarrollo FIIS","version":"1.0-DEV"}`
- `GET /config/soporte` → `soporte-dev@unas.edu.pe`

## Perfil test
Comando ejecutado:
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

Resultado:
- `GET /config/info` → `{"entorno":"test","mensaje":"Entorno de pruebas FIIS","version":"1.0-TEST"}`
- `GET /config/soporte` → `soporte-test@unas.edu.pe`

## Perfil prod
Variable usada:
```
export APP_MENSAJE="Sistema FIIS en produccion"
export SOPORTE_EMAIL="soporte@unas.edu.pe"
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

En Windows PowerShell:
```
$env:APP_MENSAJE="Sistema FIIS en produccion"
$env:SOPORTE_EMAIL="soporte@unas.edu.pe"
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod
```

Resultado:
- `GET /config/info` → `{"entorno":"prod","mensaje":"Sistema FIIS en produccion","version":"1.0-PROD"}`
- `GET /config/soporte` → `soporte@unas.edu.pe`

## Ejercicio aplicado - Propiedad app.soporte
Se agregó la propiedad `app.soporte` en los tres archivos de perfil:
- `application-dev.properties` → `soporte-dev@unas.edu.pe`
- `application-test.properties` → `soporte-test@unas.edu.pe`
- `application-prod.properties` → `${SOPORTE_EMAIL:soporte@unas.edu.pe}` (variable de entorno con fallback)

Endpoint expuesto: `GET /config/soporte`

Validación en dos perfiles:
```bash
# Perfil dev
curl http://localhost:8080/config/soporte
# → soporte-dev@unas.edu.pe

# Perfil prod con variable de entorno
export SOPORTE_EMAIL="soporte@unas.edu.pe"
curl http://localhost:8080/config/soporte
# → soporte@unas.edu.pe
```

## Conclusión
La configuración cambia por entorno sin modificar el código fuente.
El mismo artefacto (.jar) puede desplegarse en dev, test y prod usando únicamente propiedades externas y variables de entorno.
