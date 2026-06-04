# Sesión 18 - Configuración dinámica y perfiles

## Perfil dev
Comando ejecutado:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

Resultado `/config/info`:
- **entorno**: "dev"
- **mensaje**: "Entorno de desarrollo FIIS"
- **version**: "1.0-DEV"
- **soporte**: "soporte-dev@unas.edu.pe"

## Perfil prod
Variables de entorno configuradas:
- `APP_MENSAJE="Sistema FIIS en produccion"`
- `SOPORTE_EMAIL="soporte@unas.edu.pe"`

Comando ejecutado:
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

Resultado `/config/info`:
- **entorno**: "prod"
- **mensaje**: "Sistema FIIS en produccion"
- **version**: "1.0-PROD"
- **soporte**: "soporte@unas.edu.pe"

## Conclusión
La configuración cambia por entorno sin modificar el código fuente. Se logró implementar la variabilidad de la aplicación mediante el uso de perfiles de Spring Boot y variables de entorno externas, asegurando que el artefacto de software sea agnóstico al entorno donde se despliega.