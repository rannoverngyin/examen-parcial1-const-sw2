# Sesión 18 - Configuración dinámica y perfiles

## Perfil dev
Comando ejecutado:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
![alt text](image.png)


Resultado:

![alt text](image-3.png)

![alt text](image-2.png)
/config/info -> entorno dev
![alt text](image-1.png)

## perfil test
![alt text](image-5.png)
Resultado:
![alt text](image-6.png)
![alt text](image-7.png)
![alt text](image-8.png)


## Perfil prod
Variable usada:
APP_MENSAJE=Sistema FIIS en produccion
![alt text](image-9.png)

Resultado:
![alt text](image-12.png)
![alt text](image-11.png)
/config/info -> entorno prod
![alt text](image-10.png)

## Ejercicio aplicado
dev
![alt text](image-13.png)
test
![alt text](image-14.png)
prod
![alt text](image-15.png)

## Conclusión
La configuración cambia por entorno sin modificar el código fuente.

