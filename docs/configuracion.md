# Sesión 18 - Configuración dinámica y perfiles

## PASO 1: Perfil dev
Comando ejecutado:
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

![alt text](image.png)

Resultado:
/config/info -> entorno dev

![alt text](image-1.png)

## PASO 2: Perfil prod
Variable usada:
APP_MENSAJE=Sistema FIIS en produccion

![alt text](image-2.png)

Resultado:
/config/info -> entorno prod

![alt text](image-3.png)

## PASO 3:: Perfil test

Comando ejecutado:
./mvnw spring-boot:run -Dspring-boot.run.profiles=test

![alt text](image-4.png)


Resultado:
/config/info -> entorno test

![alt text](image-5.png)

## PASO 4: CAMBIO DE MENSAJE EN EL PERFIL DE PERDIL PROD

## COMANDO 

![alt text](image-6.png)

## RESULTADO

![alt text](image-7.png)



## PASO 5: CAMBIO DE MENSAJE EN EL PERFIL DE PERDIL PROD 2

## COMANDO 

![alt text](image-8.png)

## RESULTADO

![alt text](image-9.png)

## PASO 6 EJERCICIO APLICANDO app.soporte 

## perfil dev 

![alt text](image-10.png)

## perfil test

![alt text](image-11.png)

## perfil prod 

![alt text](image-12.png)
 


 ## git commit 

 ![alt text](image-13.png)

 ## git push

 ![alt text](image-14.png)

 
## Conclusión
La configuración cambia por entorno sin modificar el código fuente.


