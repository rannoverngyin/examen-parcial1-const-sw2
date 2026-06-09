# Sesión 19 - Configuración y Parametrización

## Comando para correr sistema
mvn spring-boot:run

## Funcionamiento por CURL
curl http://localhost:8080/parametros/institucion
![alt text](institucion.png)

curl http://localhost:8080/parametros/modo
![alt text](modo.png)

curl http://localhost:8080/parametros/limite-usuarios
![alt text](limite-usuarios.png)

## EMAIL

app.notificacion.proveedor=email

curl.exe -X POST "http://localhost:8080/notificaciones/enviar?destino=correo@unas.edu.pe"
![alt text](email.png)

## MOCK

app.notificacion.proveedor=mock

curl.exe -X POST "http://localhost:8080/notificaciones/enviar?destino=correo@unas.edu.pe"
![alt text](mock.png)

## Ejercicio Aplicado
ParametroService.java
    Agregado:
    ![alt text](agregado-parametro.png)
    Mapping:
    ![alt text](mapping.png)
    Test:
    ![alt text](Test.png)

## Funcionamiento Evidencia
