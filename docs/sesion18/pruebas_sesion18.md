# Sesión 18 - Configuración dinámica y perfiles

## Perfil dev
Comando ejecutado:
./mvnw spring-boot:run "-Dspring-boot.run.profiles=dev"

Resultado:
/config/info -> entorno dev
![alt text](image.png)

## Perfil prod
Variable usada:
APP_MENSAJE=Sistema FIIS en produccion

Resultado:
/config/info -> entorno prod
![alt text](image-1.png)

## Implementacion de soporte
![alt text](image-2.png)

## Conclusión
La configuración cambia por entorno sin modificar el código fuente.

