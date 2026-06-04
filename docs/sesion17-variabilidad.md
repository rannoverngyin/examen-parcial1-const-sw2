# Documentación Técnica: Análisis de Variabilidad - Sesión 17

Este documento resume el análisis de variabilidad y los puntos de configuración del sistema implementados en el proyecto de la FIIS-UNAS.

## 1. Identificación del Punto de Variación

El sistema requiere adaptar el cálculo del precio final de venta de un producto dependiendo del perfil o categoría del cliente que realiza la compra. En lugar de codificar de forma rígida (hardcode) las reglas de descuento o crear múltiples flujos independientes, se define un **punto de variación** dinámico en la capa de aplicación.

- **Nombre del Punto de Variación:** Tipo de Cliente / Nivel de Descuento.
- **Ubicación en el código:** La lógica de negocio y las variantes están modeladas en el enumerado [VarianteCliente.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/domain/VarianteCliente.java) (capa de **Dominio**) y son coordinadas en la capa de **Aplicación** por [PrecioService.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/application/PrecioService.java) en el método `calcularPrecioFinal(double precio)`.
- **Tipo de Variación:** Tiempo de despliegue / inicialización (configurado mediante archivos de propiedades externas).


## 2. Lista de Variantes Implementadas

Se han configurado cuatro variantes de cliente con sus respectivas reglas de descuento aplicadas sobre el precio base:

| Variante | Porcentaje de Descuento | Multiplicador de Precio | Descripción / Regla de Negocio |
| :--- | :---: | :---: | :--- |
| **BASICO** | 0% | `1.0` | Cliente regular. No se aplica ningún tipo de descuento. |
| **PREMIUM** | 10% | `0.90` | Cliente premium. Se descuenta el 10% del precio total. |
| **VIP** | 20% | `0.80` | Cliente preferencial VIP. Se descuenta el 20% del precio total. |
| **ESTUDIANTE** | 30% | `0.70` | Variante de beneficio académico. Se descuenta el 30% del precio total. |

*Nota: Cualquier variante desconocida o errónea configurada por el administrador del sistema se derivará por defecto al comportamiento de **BASICO** para evitar errores o pérdidas no controladas en el negocio.*


## 3. Mecanismo de Configuración Externa Utilizado

Para lograr que el sistema sea configurable sin necesidad de recompilar el código fuente, se utiliza la infraestructura de inyección de valores externos de **Spring Boot**:

1. **Archivo de Configuración:** [application.properties](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/resources/application.properties).
   ```properties
   app.variante-cliente=PREMIUM
   ```
2. **Inyección en Código:** Se utiliza la anotación `@Value` sobre el atributo de clase `varianteCliente` en [PrecioService.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/application/PrecioService.java), con un valor de respaldo (fallback) por defecto:
   ```java
   @Value("${app.variante-cliente:BASICO}")
   private String varianteCliente;
   ```
3. **Modelado en el Dominio (Clean Architecture):** Para respetar el aislamiento de capas de Clean Architecture, el punto de variación y su comportamiento de negocio (regla de descuento y cálculo) se encapsulan dentro de un enumerado de Dominio: [VarianteCliente.java](file:///c:/Users/Anali/Downloads/Construcción/practica17/src/main/java/pe/unas/demoapi/domain/VarianteCliente.java). De este modo, la capa de Aplicación delega el cálculo al Dominio sin acoplarse directamente a las fórmulas específicas:
   ```java
   public enum VarianteCliente {
       BASICO(1.0), PREMIUM(0.90), VIP(0.80), ESTUDIANTE(0.70);
       // ... lógica de cálculo y parseo ...
   }
   ```

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

## 5. Diagnóstico de Problemas Comunes y Pruebas

### 5.1. El cambio en `application.properties` no se refleja inmediatamente
* **Causa:** Por defecto, los valores definidos en `application.properties` e inyectados mediante `@Value` son cargados por Spring Boot únicamente durante el arranque de la aplicación (fase de inicialización del contenedor de IoC). Cambiar el archivo `application.properties` en caliente no actualiza el estado en memoria de los beans Singleton ya instanciados.
* **Solución:** Cada vez que realices un cambio en `application.properties` (por ejemplo, cambiar de `ESTUDIANTE` a `PREMIUM`), debes **detener y volver a iniciar el servidor Spring Boot** para que los nuevos valores surtan efecto.

### 5.2. Error de NullPointerException en el output del IDE (`PropertiesJavaDefinitionHandler.java:92`)
* **Causa:** En la pestaña de salida (*Output*) o consola de VS Code, es común observar un error del tipo:
  ```text
  [Error - 8:35:01 AM] Request textDocument/definition failed.
  Message: Internal error.
  ...
  java.lang.NullPointerException: Cannot invoke "org.springframework.ide.vscode.java.properties.parser.PropertiesAst$Node.getOffset()" because "node" is null
  ```
  Este error es un bug interno del **Language Server de Spring Boot** (la extensión de VS Code "Spring Boot Tools") al intentar parsear dinámicamente el archivo `application.properties` e indexar la definición de las propiedades (por ejemplo, cuando se pasa el cursor sobre `app.variante-cliente` o se presiona `Ctrl+Click` en la propiedad).
* **Impacto en el sistema:** **Ninguno**. Este es un error puramente del entorno de desarrollo (IDE) y de sus herramientas de asistencia de código. **No afecta en absoluto la compilación ni el funcionamiento de la aplicación en ejecución**, por lo que el sistema no está roto ni corrupto.

