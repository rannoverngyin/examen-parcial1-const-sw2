# Análisis de variabilidad – Sesión 17

## Punto de variación
Cálculo de precio final según tipo de cliente.

## Variantes
- BASICO: sin descuento.
- PREMIUM: 10% de descuento.
- VIP: 20% de descuento.

## Mecanismo usado
Configuración externa mediante application.properties.

## Propiedad
app.variante-cliente=PREMIUM
![alt text](image-3.png)



## Evidencias de la variante PREMIUM
- GET /variante-activa
![alt text](image-1.png)

- GET /precio-final?precio=100
![alt text](image-2.png)

## Evidencias de la variante ESTUDIANTE
- GET /variante-activa
![alt text](image-4.png)

- GET /precio-final?precio=100
![alt text](image-5.png)

Probar ESTUDIANTE con nueva implementacion sin reiniciar es servidor:
curl "http://localhost:8080/precio-final?precio=100&variante=ESTUDIANTE"

## Captura de ejecución ./mvnw test con BUILD SUCCESS.

![alt text](image.png)