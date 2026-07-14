# Reporte Técnico - Sesión 27: Pruebas de Rendimiento Frontend y Backend

## 1. Escenario de Trabajo
* **Institución:** Universidad Nacional Agraria de la Selva (UNAS)
* **Facultad:** Facultad de Ingeniería en Informática y Sistemas (FIIS)
* **Curso:** Construcción de Software II
* **Endpoints Evaluados:**[cite: 1]
  * Base: `GET /rendimiento/productos/base`[cite: 1]
  * Optimizado: `GET /rendimiento/productos/optimizado`[cite: 1]
* **Dataset:** Colección estática de 5 productos (`Laptop`, `Mouse`, `Teclado`, `Monitor`, `Impresora`)[cite: 1].
* **Repeticiones:** 5 ciclos de calentamiento (Warmup) + 30 repeticiones de medición por cada endpoint[cite: 1].
* **Entorno de Pruebas:** Windows 10 (Build 26200), Java 17, Spring Boot, Python 3 (Biblioteca estándar)[cite: 1].

---

## 2. Hipótesis Inicial
La versión **Base** experimenta una degradación deliberada de rendimiento debido a la invocación de `Thread.sleep(200)` (retraso síncrono controlado) que simula una consulta pesada a base de datos o latencia externa de red, sumado al costo computacional de mapear la lista en cada petición mediante flujos (`Stream`)[cite: 1].

La versión **Optimizada** elimina por completo la latencia mediante dos estrategias clave[cite: 1]:
1. Eliminación del retraso síncrono ineficiente (`Thread.sleep`)[cite: 1].
2. Almacenamiento en caché de la lista inmutable ya procesada en mayúsculas (`productosOptimizados`), evitando la re-evaluación del stream en cada petición HTTP[cite: 1].

Se espera que la versión optimizada reduzca la latencia en más de un **90%** en el percentil 95 (p95), eliminando la acumulación de tiempos de espera.

---

## 3. Resultados Obtenidos (Consola & Automatización)

### Mediciones con script de automatización (`medir_rendimiento.py`):
Los resultados recolectados directamente desde la terminal de Powershell reflejan las siguientes métricas:

| Métrica | Versión Base | Versión Optimizada | Reducción / Mejora (%) |
| :--- | :---: | :---: | :---: |
| **Promedio** | 220.67 ms | 10.79 ms | **95.11%** |
| **Mediana** | 218.44 ms | 4.75 ms | **97.83%** |
| **p95 (Percentil 95)** | **233.07 ms** | **24.84 ms** | **89.34%** |
| **Mínimo** | 204.40 ms | 3.05 ms | **98.51%** |
| **Máximo** | 245.78 ms | 27.12 ms | **88.97%** |
| **Errores** | 0 | 0 | *Estable (0% fallos)* |

### Validación rápida con `curl` (Consola cmd):
* **Base:** `base=0.636683s status=200`
* **Optimizado:** `optimizado=0.026368s status=200`

---

## 4. Evidencias e Integración de Capturas

### A. Estructura de Carpetas de Evidencias
Para asegurar que tu docente visualice correctamente las capturas de pantalla, te sugiero crear una carpeta dentro de tu directorio `docs` llamada `evidencias` (`docs/evidencias/`)[cite: 1]:
* Captura de Red del Frontend (DevTools Network): `docs/evidencias/devtools_network.png`[cite: 1]
* Captura de Monitoreo JVM (VisualVM/JFR): `docs/evidencias/visualvm_monitor.png`[cite: 1]



#### 1. Evidencia: Chrome DevTools Network (F12)
Muestra el tiempo de respuesta total y el TTFB (Time to First Byte) desde la página `rendimiento.html`[cite: 1].
![DevTools Network](docs/evidencias/devtools_network.png)

#### 2. Evidencia: Chrome DevTools Performance
Muestra que en la versión optimizada no existen "Long Tasks" (tareas que bloquean el hilo principal del navegador por más de 50ms)[cite: 1].
![DevTools Performance](docs/evidencias/devtools_performance.png)

#### 3. Evidencia: Monitoreo de Recursos JVM con VisualVM
Muestra la estabilidad del consumo de CPU y la liberación de memoria (Garbage Collector) durante las ráfagas de pruebas[cite: 1].
![VisualVM Monitor](docs/evidencias/visualvm_monitor.png)

---

## 5. Conclusiones y Diagnóstico Técnico

1. **Localización del Cuello de Botella:** La latencia excesiva residía estrictamente en el **Backend**. El flujo de red (Network) y el tiempo de renderizado del navegador (Frontend) se mantuvieron mínimos (el tamaño de respuesta es menor a 1 KB), lo que confirma que el retardo percibido por el usuario final se debía enteramente al hilo de ejecución bloqueado por `Thread.sleep` en el servidor[cite: 1].
2. **Eficacia de la Optimización:** Al remover el retraso artificial y precalcular la lista, el **p95 disminuyó de 233.07 ms a solo 24.84 ms** (una mejora de rendimiento del **89.34%**). Esto previene que bajo concurrencia los hilos de Tomcat se saturen y colapsen la aplicación[cite: 1].
3. **Criterio de Éxito:** Se cumplió satisfactoriamente. El p95 optimizado es exponencialmente menor que el p95 base, registrando un **0% de errores HTTP** en ambas pruebas de estrés controlado[cite: 1].