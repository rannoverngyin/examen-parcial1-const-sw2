# Análisis de variabilidad – Sesión 17

## Punto de variación
Cálculo de precio final según tipo de cliente.

## Variantes
- BASICO: sin descuento.
- PREMIUM: 10% de descuento.
- VIP: 20% de descuento.
- Estudaintes : 30% de descuento.

## Mecanismo usado
Configuración externa mediante application.properties.

Aplication.properties.png

## Propiedad
app.variante-cliente=PREMIUM

Prueba3.png

## Endpoints
- GET /variante-activa
- GET /precio-final?precio=100

Prueba1.png
Prueba2.png