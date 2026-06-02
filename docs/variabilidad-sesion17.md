# Análisis de variabilidad – Sesión 17

## Punto de variación
Cálculo de precio final según tipo de cliente.

## Variantes
- BASICO: sin descuento.
- PREMIUM: 10% de descuento.
- VIP: 20% de descuento.
- ESTUDIANTE: 30% de descuento.

## Mecanismo usado
Configuración externa mediante application.properties.

## Propiedad
app.variante-cliente=PREMIUM

## Endpoints
- GET /variante-activa
- GET /precio-final?precio=100

---

## Evidencias (Capturas de Pantalla)
A continuación, adjunta las siguientes capturas solicitadas en la práctica:

1. **Variante Activa**: 
*(Inserta aquí tu imagen de `http://localhost:8080/variante-activa`)* 

2. **Cálculo de Precio (Variante 1 - Ej: PREMIUM)**: 
*(Inserta aquí tu imagen de `http://localhost:8080/precio-final?precio=100` devolviendo 90.0)*

3. **Cálculo de Precio (Variante 2 - Ej: ESTUDIANTE o VIP)**: 
*(Inserta aquí tu imagen de `http://localhost:8080/precio-final?precio=100` después de cambiar application.properties y reiniciar)*

4. **Pruebas Unitarias (BUILD SUCCESS)**: 
*(Inserta aquí la captura de tu terminal ejecutando `./mvnw test` mostrando que las pruebas pasaron)*
