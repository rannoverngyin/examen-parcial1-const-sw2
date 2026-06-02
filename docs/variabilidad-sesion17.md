# Documentación Técnica: Análisis de Variabilidad - Sesión 17

Este documento resume el análisis de variabilidad y los puntos de configuración del sistema implementados en el proyecto de la FIIS-UNAS.

---

## 1. Identificación del Punto de Variación

El sistema requiere adaptar el cálculo del precio final de venta de un producto dependiendo del perfil o categoría del cliente que realiza la compra. En lugar de codificar de forma rígida (hardcode) las reglas de descuento o crear múltiples flujos independientes, se define un **punto de variación** dinámico en la capa de aplicación.

- **Nombre del Punto de Variación:** Tipo de Cliente / Nivel de Descuento.
- **Ubicación en el código:** [PrecioService.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/application/PrecioService.java) en el método `calcularPrecioFinal(double precio)`.
- **Tipo de Variación:** Tiempo de despliegue / inicialización (configurado mediante archivos de propiedades externas).

---

## 2. Lista de Variantes Implementadas

Se han configurado cuatro variantes de cliente con sus respectivas reglas de descuento aplicadas sobre el precio base:

| Variante | Porcentaje de Descuento | Multiplicador de Precio | Descripción / Regla de Negocio |
| :--- | :---: | :---: | :--- |
| **BASICO** | 0% | `1.0` | Cliente regular. No se aplica ningún tipo de descuento. |
| **PREMIUM** | 10% | `0.90` | Cliente premium. Se descuenta el 10% del precio total. |
| **VIP** | 20% | `0.80` | Cliente preferencial VIP. Se descuenta el 20% del precio total. |
| **ESTUDIANTE** | 30% | `0.70` | Variante de beneficio académico. Se descuenta el 30% del precio total. |

*Nota: Cualquier variante desconocida o errónea configurada por el administrador del sistema se derivará por defecto al comportamiento de **BASICO** para evitar errores o pérdidas no controladas en el negocio.*

---

## 3. Mecanismo de Configuración Externa Utilizado

Para lograr que el sistema sea configurable sin necesidad de recompilar el código fuente, se utiliza la infraestructura de inyección de valores externos de **Spring Boot**:

1. **Archivo de Configuración:** [application.properties](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/resources/application.properties).
   ```properties
   app.variante-cliente=PREMIUM
   ```
2. **Inyección en Código:** Se utiliza la anotación `@Value` en el constructor de [PrecioService.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/application/PrecioService.java) con un valor de respaldo (fallback) por defecto:
   ```java
   public PrecioService(@Value("${app.variante-cliente:BASICO}") String varianteCliente) {
       this.varianteCliente = varianteCliente.trim().toUpperCase();
   }
   ```

---

## 4. Endpoints Disponibles (Capa de Presentación)

La capa de presentación expone dos endpoints REST a través de [PrecioController.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/presentation/PrecioController.java):

### 4.1. Obtener Variante Activa
- **Ruta:** `GET /variante-activa`
- **Descripción:** Devuelve un String que indica qué variante de cliente está activa en el sistema.
- **Ejemplo de Respuesta:**
  ```text
  PREMIUM
  ```

### 4.2. Calcular Precio Final
- **Ruta:** `GET /precio-final`
- **Parámetros de Consulta (Query Param):**
  - `precio` (double): El precio base del producto a evaluar.
- **Descripción:** Calcula y retorna el valor numérico tras aplicar el descuento de la variante activa.
- **Ejemplo de Petición:** `GET /precio-final?precio=100.0`
- **Ejemplo de Respuesta:**
  ```text
  90.0
  ```
