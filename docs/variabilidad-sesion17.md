# Análisis de variabilidad – Sesión 17

## Punto de variación

Cálculo de precio final según tipo de cliente.

## Variantes

| Variante   | Regla              | Fórmula              |
|------------|--------------------|----------------------|
| BASICO     | Sin descuento      | precioFinal = precio |
| PREMIUM    | 10% de descuento   | precioFinal = precio * 0.90 |
| VIP        | 20% de descuento   | precioFinal = precio * 0.80 |
| ESTUDIANTE | 30% de descuento   | precioFinal = precio * 0.70 |

## Mecanismo usado

Configuración externa mediante `application.properties` + inyección con `@Value`.

## Propiedad

```properties
app.variante-cliente=PREMIUM
```

Cambiar el valor a `VIP`, `BASICO` o `ESTUDIANTE` y reiniciar el servidor para activar otra variante.

## Endpoints

| Método | URL                          | Descripción                          |
|--------|------------------------------|--------------------------------------|
| GET    | `/variante-activa`           | Devuelve la variante configurada.    |
| GET    | `/precio-final?precio=100`   | Devuelve el precio con descuento.    |

## Ejemplos de respuesta (variante PREMIUM)

```
GET /variante-activa  → PREMIUM
GET /precio-final?precio=100  → 90.0
```

## Ejemplos de respuesta (variante VIP)

```
GET /variante-activa  → VIP
GET /precio-final?precio=100  → 80.0
```

## Ejemplos de respuesta (variante ESTUDIANTE)

```
GET /variante-activa  → ESTUDIANTE
GET /precio-final?precio=100  → 70.0
```

## Características comunes

- Listar / consultar productos es independiente de la variante.
- La variante solo afecta el cálculo de precio.

## Estructura del proyecto

```
src/
├── main/
│   ├── java/pe/unas/demoapi/
│   │   ├── ExamenParcial1ConstSw2Application.java
│   │   ├── application/
│   │   │   └── PrecioService.java
│   │   └── presentation/
│   │       └── PrecioController.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/pe/unas/demoapi/
        └── PrecioServiceTest.java
docs/
└── variabilidad-sesion17.md
```

## Conclusión

La variabilidad se implementa sin duplicar código ni crear ramas de proyecto separadas.
El sistema parametriza la decisión de negocio en una propiedad externa, manteniendo
una arquitectura limpia y completamente verificable con pruebas unitarias.

> **Un sistema flexible no duplica código; parametriza sus decisiones.**

FIIS-UNAS · Construcción de Software II · Sesión 17
