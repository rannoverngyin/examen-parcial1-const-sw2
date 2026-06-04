# Análisis de Variabilidad - Sesión 17

## 1. Punto de Variación
* [cite_start]**Identificación:** Cálculo del precio final del producto basado en el tipo de cliente comercial[cite: 910].

## 2. Variantes Disponibles
* [cite_start]**BASICO:** No aplica ningún descuento sobre el precio base[cite: 912].
* [cite_start]**PREMIUM:** Aplica un 10% de descuento[cite: 913].
* [cite_start]**VIP:** Aplica un 20% de descuento[cite: 914].
* [cite_start]**ESTUDIANTE:** Aplica un 30% de descuento (implementado como caso de éxito del ejercicio práctico)[cite: 928, 929].

## 3. Mecanismo de Variabilidad Usado
* [cite_start]**Inyección en Tiempo de Ejecución:** Configuración externa controlada desde el archivo `application.properties` procesado mediante la anotación `@Value` de Spring Boot[cite: 915, 916].

## 4. Propiedad de Control
```properties
app.variante-cliente=ESTUDIANTE