# Análisis de variabilidad – Sesión 17

## Punto de variación
Cálculo de precio final según tipo de cliente.

## Variantes
- BASICO: sin descuento.
- PREMIUM: 10% de descuento.
- VIP: 20% de descuento.
- ESTUDIANTE: 30% de descuento.

## Mecanismo usado
Configuración externa mediante application.properties,permitiendo alterar el comportamiento del negocio sin necesidad de recompilar el código fuente.

## Propiedad
app.variante-cliente=ESTUDIANTE

## Endpoints
- GET /variante-activa
- GET /precio-final?precio=100
