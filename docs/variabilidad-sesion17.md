# Análisis de variabilidad – Sesión 17

## Descripción

La API REST permite calcular el precio final de un producto según el tipo de
cliente configurado externamente.

## Característica común

La característica común es el cálculo del precio final de un producto.

## Punto de variación

El punto de variación es el porcentaje de descuento aplicado al precio original.

## Variantes

| Variante | Regla |
|---|---|
| BASICO | No aplica descuento |
| PREMIUM | Aplica 10% de descuento |
| VIP | Aplica 20% de descuento |
| ESTUDIANTE | Aplica 30% de descuento |

## Mecanismo utilizado

Se utiliza configuración externa mediante el archivo `application.properties`.

## Propiedad configurable

```properties
app.variante-cliente=PREMIUM
```

La propiedad puede cambiar a `BASICO`, `PREMIUM`, `VIP` o `ESTUDIANTE`.

## Endpoints

- `GET /variante-activa`
- `GET /precio-final?precio=100`

## Resultados esperados

| Variante | Precio original | Precio final |
|---|---:|---:|
| BASICO | 100.0 | 100.0 |
| PREMIUM | 100.0 | 90.0 |
| VIP | 100.0 | 80.0 |
| ESTUDIANTE | 100.0 | 70.0 |

## Conclusión

La variabilidad permite cambiar el comportamiento del sistema mediante
configuración externa, sin duplicar código ni modificar la lógica principal.