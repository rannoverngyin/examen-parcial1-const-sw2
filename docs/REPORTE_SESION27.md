# Reporte técnico - Sesión 27

## 1. Escenario
- **Endpoints:**
 **Endpoint base:** `GET /rendimiento/productos/base`
 **Endpoint optimizado:** `GET /rendimiento/productos/optimizado`
- **Dataset:** Laptop, Mouse, Teclado, Monitor e Impresora.
- **Página frontend:** `http://localhost:8080/rendimiento.html`
- **Equipo y entorno:** Windows con PowerShell, Java 17.0.18, Spring Boot, Python 3, Chrome DevTools y JDK Mission Control 8.3.1.

## 2. Hipótesis inicial
Se consideró que el posible cuello de botella estaba en el backend del endpoint base, debido a la espera artificial de 200 ms y al procesamiento repetido de la lista de productos en cada solicitud, lo que podía elevar significativamente el tiempo de respuesta y el p95.

## 3. Evidencias

- captura de levantamiento del spring boot

![alt text](image.png)

-validacion de respuesta y tiempo total

**base:**
![alt text](image-1.png)

**optimizado:**

![alt text](image-2.png)

-captura de automatizacion del rendimiento y p95:

![alt text](image-3.png)

- captura de prueba del frontend:
**base:**

![alt text](image-4.png)

**optimizado:**

![alt text](image-5.png)

- Captura DevTools Network.

![alt text](image-6.png)

**base:**

![alt text](image-7.png)


**optimizado:**

![alt text](image-8.png)

- Captura Performance.

almenos tres ejcuciones para los dos:

**base:**
primera:

![alt text](image-9.png)

segunda:

![alt text](image-11.png)

tercera:

![alt text](image-13.png)

**optimizado:**
primera:

![alt text](image-10.png)

segunda:

![alt text](image-12.png)

tercera:

![alt text](image-14.png)

- Captura VisualVM/JFR.

![alt text](image-15.png)

- captura de cpu pico y heap pico
**base:**

![alt text](image-16.png)

**optimizado:**

![alt text](image-17.png)

## reto aplicado:

- Objetivo:

Se agregó el parámetro `cantidad` a los endpoints base y optimizado para medir su comportamiento con 10, 1 000 y 10 000 productos, con el propósito de identificar desde qué tamaño comenzaba a incrementarse el p95.

- **prueba de los endponts:**

**10:**

![alt text](image-24.png)

**1000:**

![alt text](image-25.png)

**10000:**

![alt text](image-26.png)

- **capturas de cada cantidad:**

**10:**

![alt text](image-21.png)

**1000:**

![alt text](image-22.png)

**10000:**

![alt text](image-23.png)

## 4. Resultados
| Versión    | Promedio (ms) | Mediana (ms) |    p95 (ms) |     Errores |       CPU pico |      Heap pico |
| ---------- | ------------: | -----------: | ----------: | ----------: | -------------: | -------------: |
| Base       |        210.15 |       207.01 |      221.85 |           0 |         1.65 % |       42.6 MiB |
| Optimizada |         12.88 |         9.59 |       27.46 |           0 |         1.81 % |       68.6 MiB |
| Mejora (%) |   **93.87 %** |  **95.37 %** | **87.62 %** | Sin errores | No concluyente | No concluyente |


## Fórmulas desarrolladas
**Mejora del promedio:**
![alt text](image-18.png)

**Mejora de la mediana:**
![alt text](image-19.png)

**Mejora del p95:**

![alt text](image-20.png)

## 5. Conclusión
El principal cuello de botella se encontraba en el backend del endpoint base, debido a la espera artificial de 200 ms y al procesamiento repetido de la lista de productos en cada solicitud. La mejora aplicada consistió en eliminar dicha espera y reutilizar una lista previamente procesada, lo que redujo el p95 de 221.85 ms a 27.46 ms, equivalente a una mejora de 87.62 %. Ambos endpoints registraron cero errores HTTP.