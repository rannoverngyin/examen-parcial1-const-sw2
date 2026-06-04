## Perfil dev
Comando ejecutado:
mvn "-Dspring-boot.run.profiles=dev" spring-boot:run
![alt text](image.png)
![alt text](image-1.png)
Resultado:
/config/info -> entorno dev
![alt text](image-2.png)

## Perfil test
Comando ejecutado:
mvn "-Dspring-boot.run.profiles=test" spring-boot:run
![alt text](image-8.png)
![alt text](image-9.png)
Resultado:
/config/info -> entorno test
![alt text](image-4.png)

## Perfil prod
Variable usada:
APP_MENSAJE=Sistema FIIS en produccion

Resultado:
/config/info -> entorno prod
![alt text](image-3.png)
![alt text](image-10.png) >> evidencia con la nueva propiedad app.soporte

## app.properties de los entornos
dev:![alt text](image-5.png)
test:![alt text](image-6.png)
prob: ![alt text](image-7.png)

## Conclusión
La configuración cambia por entorno sin modificar el código fuente.

