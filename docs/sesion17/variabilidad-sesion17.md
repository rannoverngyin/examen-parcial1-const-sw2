
## 1. ¿Qué es la variabilidad en software?

La **variabilidad** es la capacidad de un sistema de comportarse de manera diferente según configuración, tipo de cliente, entorno o regla de negocio — **sin modificar el código fuente**.

En esta práctica se implementa variabilidad en el cálculo del precio final de un producto según el tipo de cliente configurado.

---

## 2. Modelo de variabilidad

### 2.1 Punto de variación

> **Lugar del sistema donde puede cambiar el comportamiento.**

| Elemento | Descripción |
|---|---|
| **Punto de variación** | Cálculo del precio final según tipo de cliente |
| **Propiedad de control** | `app.variante-cliente` en `application.properties` |
| **Característica común** | Recibir un precio base y retornar un precio final |

### 2.2 Variantes disponibles

| Variante | Regla de negocio | Fórmula | Precio base 100 → resultado |
|---|---|---|---|
| `BASICO` | Sin descuento | `precioFinal = precio` | `100.0` |
| `PREMIUM` | 10% de descuento | `precioFinal = precio * 0.90` | `90.0` |
| `VIP` | 20% de descuento | `precioFinal = precio * 0.80` | `80.0` |

> Solo una variante está activa a la vez, controlada por configuración externa.

---

## 3. Mecanismo de variabilidad implementado

### Tipo: Configuración externa (`application.properties`)

El mecanismo utilizado es **configuración externa** mediante el archivo `application.properties` y la anotación `@Value` de Spring Boot.

```
Flujo de variabilidad:
┌─────────────────────────────┐
│  application.properties     │
│  app.variante-cliente=PREMIUM│
└────────────┬────────────────┘
             │ @Value inyecta el valor
             ▼
┌─────────────────────────────┐
│       PrecioService.java    │
│  varianteCliente = "PREMIUM"│
│  switch → precio * 0.90     │
└────────────┬────────────────┘
             │ retorna precio final
             ▼
┌─────────────────────────────┐
│     PrecioController.java   │
│  GET /precio-final?precio=X │
└─────────────────────────────┘
```

**Ventaja clave:** Cambiar de `PREMIUM` a `VIP` solo requiere editar una línea en el archivo de propiedades y reiniciar. No se toca el código Java.

---

## 4. Estructura de archivos del módulo

```
src/
├── main/
│   ├── java/pe/unas/demoapi/
│   │   ├── application/
│   │   │   └── PrecioService.java         ← Lógica de variabilidad
│   │   └── presentation/
│   │       └── PrecioController.java      ← Endpoints REST
│   └── resources/
│       └── application.properties         ← Propiedad de control
└── test/
    └── java/pe/unas/demoapi/
        └── PrecioServiceTest.java         ← Pruebas unitarias
docs/
└── variabilidad-sesion17.md               ← Este archivo
```

---

## 5. Configuración de variabilidad

**Archivo:** `src/main/resources/application.properties`

```properties
spring.application.name=examen-parcial1-const-sw2
server.port=8080

# ─── PUNTO DE VARIACIÓN ──────────────────────────────
# Controla el comportamiento del cálculo de precio final
# Valores válidos: BASICO | PREMIUM | VIP
app.variante-cliente=PREMIUM
# ─────────────────────────────────────────────────────
```

>Cambiar este valor y reiniciar el servidor activa una variante diferente sin tocar código.

---

## 6. Código fuente

### 6.1 `PrecioService.java` — Capa `application`

```java
package pe.unas.demoapi.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrecioService {

    // Spring inyecta el valor desde application.properties
    // Si la propiedad no existe, usa "BASICO" como valor por defecto
    @Value("${app.variante-cliente:BASICO}")
    private String varianteCliente;

    /**
     * Calcula el precio final usando la variante configurada externamente.
     */
    public double calcularPrecioFinal(double precio) {
        return calcularPorVariante(precio, varianteCliente);
    }

    /**
     * Método auxiliar puro: permite calcular para cualquier variante
     * sin depender de la inyección de Spring → facilita pruebas unitarias.
     */
    public double calcularPorVariante(double precio, String variante) {
        return switch (variante.toUpperCase()) {
            case "PREMIUM" -> precio * 0.90;   // 10% descuento
            case "VIP"     -> precio * 0.80;   // 20% descuento
            default        -> precio;           // BASICO: sin descuento
        };
    }

    /**
     * Retorna el nombre de la variante actualmente activa.
     */
    public String obtenerVarianteActiva() {
        return varianteCliente;
    }
}
```

**Puntos clave del servicio:**

| Elemento | Función |
|---|---|
| `@Service` | Registra la clase como bean de Spring (inyectable) |
| `@Value("${app.variante-cliente:BASICO}")` | Lee la propiedad externa; si no existe, usa `BASICO` |
| `calcularPorVariante()` | Método puro sin estado — testeable sin Spring |
| `switch` expresión | Selecciona la fórmula según la variante activa |

---

### 6.2 `PrecioController.java` — Capa `presentation`

```java
package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.PrecioService;

@RestController
public class PrecioController {

    private final PrecioService service;

    // Inyección por constructor (buena práctica en Spring Boot)
    public PrecioController(PrecioService service) {
        this.service = service;
    }

    /**
     * Retorna el precio final aplicando la variante activa.
     * Ejemplo: GET /precio-final?precio=100
     */
    @GetMapping("/precio-final")
    public double calcular(@RequestParam double precio) {
        return service.calcularPrecioFinal(precio);
    }

    /**
     * Retorna el nombre de la variante actualmente configurada.
     * Ejemplo: GET /variante-activa
     */
    @GetMapping("/variante-activa")
    public String variante() {
        return service.obtenerVarianteActiva();
    }
}
```

---

## 7. Endpoints REST

### `GET /variante-activa`

Retorna la variante configurada actualmente en el sistema.

```
URL:     http://localhost:8080/variante-activa
Método:  GET
Params:  (ninguno)
```

**Ejemplo de uso:**
```bash
curl http://localhost:8080/variante-activa
```

**Respuesta esperada (con PREMIUM activo):**
```
PREMIUM
```

---

### `GET /precio-final`

Calcula y retorna el precio final aplicando el descuento de la variante activa.

```
URL:     http://localhost:8080/precio-final
Método:  GET
Params:  precio (double, requerido)
```

**Ejemplo de uso:**
```bash
curl "http://localhost:8080/precio-final?precio=100"
```

**Respuestas según variante activa:**

| Variante activa | Comando curl | Respuesta |
|---|---|---|
| `BASICO` | `curl ".../precio-final?precio=100"` | `100.0` |
| `PREMIUM` | `curl ".../precio-final?precio=100"` | `90.0` |
| `VIP` | `curl ".../precio-final?precio=100"` | `80.0` |

---

## 8. Pruebas unitarias

**Archivo:** `src/test/java/pe/unas/demoapi/PrecioServiceTest.java`

```java
package pe.unas.demoapi;

import org.junit.jupiter.api.Test;
import pe.unas.demoapi.application.PrecioService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrecioServiceTest {

    // Se instancia directamente, sin contexto de Spring
    // Esto es posible gracias al método calcularPorVariante()
    private final PrecioService service = new PrecioService();

    @Test
    void debeAplicarDescuentoPremium() {
        // 100 * 0.90 = 90.0
        assertEquals(90.0, service.calcularPorVariante(100, "PREMIUM"));
    }

    @Test
    void debeAplicarDescuentoVip() {
        // 100 * 0.80 = 80.0
        assertEquals(80.0, service.calcularPorVariante(100, "VIP"));
    }

    @Test
    void debeMantenerPrecioBasico() {
        // Sin descuento → 100.0
        assertEquals(100.0, service.calcularPorVariante(100, "BASICO"));
    }
}
```

**Ejecutar los tests:**
```bash
./mvnw test
```

**Resultado esperado:**
```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

>El método `calcularPorVariante(precio, variante)` recibe la variante como parámetro, lo que lo hace **independiente de `@Value`**. Por eso se puede probar sin necesidad de levantar el contexto de Spring.

---

## 9. Verificación completa del sistema

### Paso 1 — Levantar el servidor
```bash
cd ~/Documents/const_sw2/examen-parcial1-const-sw2
./mvnw spring-boot:run
```

Señal correcta en consola:
```
Tomcat started on port 8080
Started ExamenParcial1ConstSw2Application in X seconds
```

### Paso 2 — Probar con variante PREMIUM
```bash
# En otra terminal:
curl http://localhost:8080/variante-activa
# → PREMIUM

curl "http://localhost:8080/precio-final?precio=100"
# → 90.0
```

### Paso 3 — Cambiar a variante VIP
Editar `application.properties`:
```properties
app.variante-cliente=VIP
```
Reiniciar y probar:
```bash
./mvnw spring-boot:run

curl http://localhost:8080/variante-activa
# → VIP

curl "http://localhost:8080/precio-final?precio=100"
# → 80.0
```

---

## 10. Resumen del análisis de variabilidad

| Elemento | Detalle |
|---|---|
| **Punto de variación** | Cálculo del precio final |
| **Variantes** | `BASICO`, `PREMIUM`, `VIP` |
| **Mecanismo** | Configuración externa (`application.properties`) |
| **Propiedad clave** | `app.variante-cliente` |
| **Clase responsable** | `PrecioService.java` |
| **Anotación Spring** | `@Value("${app.variante-cliente:BASICO}")` |
| **Endpoints expuestos** | `GET /variante-activa`, `GET /precio-final?precio=X` |
| **Tests** | 3 pruebas unitarias — BASICO, PREMIUM, VIP |
| **Cambio de variante** | Solo editar `application.properties` + reiniciar |




