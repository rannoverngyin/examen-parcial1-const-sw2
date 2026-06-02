# Análisis de variabilidad – Sesión 17

## Punto de variación
Cálculo de precio final según tipo de cliente.

## Variantes
- BASICO: ![alt text](image-6.png) ![alt text](image-5.png)sin descuento (precio original)
- PREMIUM:![alt text](image-1.png) ![alt text](image-2.png)10% de descuento (precio * 0.90)
- VIP:![alt text](image-3.png) ![alt text](image-4.png) 20% de descuento (precio * 0.80)

## Mecanismo usado
Configuración externa mediante application.properties.

## Propiedad
app.variante-cliente=BASICO
app.variante-cliente=PREMIUM
app.variante-cliente=VIP

## Endpoints
- GET /variante-activa
- GET /precio-final?precio=100

## Pruebas

![alt text](image-7.png)

## Evidencia
- /variante-activa → PREMIUM
- /precio-final?precio=100 con PREMIUM → 90.0
- /precio-final?precio=100 con VIP → 80.0
- BUILD SUCCESS con 4 pruebas