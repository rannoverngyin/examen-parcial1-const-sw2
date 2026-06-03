# Análisis de variabilidad – Sesión 17

## Punto de variación
[cite_start]Cálculo de precio final según el tipo de cliente seleccionado[cite: 147, 148].

## Variantes
* [cite_start]**BASICO**: No aplica ningún tipo de descuento ($precioFinal = precio$)[cite: 15, 150].
* [cite_start]**PREMIUM**: Aplica un 10% de descuento sobre el precio base ($precioFinal = precio \times 0.90$)[cite: 15, 151].
* [cite_start]**VIP**: Aplica un 20% de descuento sobre el precio base ($precioFinal = precio \times 0.80$)[cite: 15, 152].
* [cite_start]**ESTUDIANTE**: Aplica un 30% de descuento sobre el precio base ($precioFinal = precio \times 0.70$)[cite: 167, 169].

## Mecanismo usado
[cite_start]Configuración externa en tiempo de despliegue mediante el archivo de propiedades de Spring Boot (`application.properties`), permitiendo alterar el comportamiento del negocio sin necesidad de recompilar el código fuente[cite: 10, 153, 154].

## Propiedad de Configuración
```properties
# Controla la variante activa del sistema (Opciones: BASICO, PREMIUM, VIP, ESTUDIANTE)
app.variante-cliente=PREMIUM