# Sesión 18 - Configuración dinámica y perfiles

## Perfil dev
Comando ejecutado:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

Resultado:
/config/info -> entorno dev
![Entorno dev](<img/sesion18/Entorno Dev.png>)
![Entorno test](<img/sesion18/Entorno Test.png>)

## Perfil prod
Variable usada:
APP_MENSAJE=Sistema FIIS en produccion

Resultado:
/config/info -> entorno prods
![Pruebas de entorno](<img/sesion18/Pruebas de entorno.png>)
![Evidencia de Entorno](<img/sesion18/Envidecia de Entorno.png>)

## Conclusión
La configuración cambia por entorno sin modificar el código fuente.

![Evidencia Final](<img/sesion18/Envidecia Final.png>)
