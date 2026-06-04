# Análisis de variabilidad – Sesión 17

## 📌 Contexto
Se implementó un mecanismo de variabilidad en una API REST de productos. 
El comportamiento (cálculo de precio final) puede cambiar sin modificar código fuente.

## 🔍 Punto de variación identificado
**Lugar:** Método `calcularPrecioFinal()` en `PrecioService`
**Motivo:** Diferentes tipos de cliente requieren diferentes descuentos

## 🎨 Variantes implementadas

| Variante | Descuento | Fórmula | Precio final (base 100) |
|----------|-----------|---------|------------------------|
| BASICO   | 0%        | precio * 1.00 | 100.0 |
| PREMIUM  | 10%       | precio * 0.90 | 90.0 |
| VIP      | 20%       | precio * 0.80 | 80.0 |
| ESTUDIANTE| 30%      | precio * 0.70 | 70.0 |

## ⚙️ Mecanismo de variabilidad usado
**Tipo:** Configuración externa (archivo properties)
**Ventaja:** No requiere recompilar ni cambiar código

### Propiedad de configuración
```properties
# En application.properties
app.variante-cliente=PREMIUM   # Puede ser: BASICO, PREMIUM, VIP, ESTUDIANTE


# PREMIUM
 ![alt text](image.png)
# VIP
![alt text](image.png)
# BASICO
![alt text](image.png)

ESTUDIANTE
![alt text](image.png)

#Pruebas unitarias
![alt text](image.png)
![alt text](image.png)
+ ESTUDIANTE 
![alt text](image.png)
![alt text](image.png)