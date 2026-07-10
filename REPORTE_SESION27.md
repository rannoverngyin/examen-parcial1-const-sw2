# Reporte técnico — Sesión 27

## Pruebas de rendimiento en frontend y backend

**Curso:** Construcción de Software II  
**Unidad:** Rendimiento y optimización  
**Estudiante:** Hidalgo Dairon  
**Repositorio:** `examen-parcial1-const-sw2`  
**Rama:** `feature/sesion27-pruebas-rendimiento--hidalgo-dairon`  
**Sistema operativo:** Windows 11  
**Java:** 24.0.2  
**Maven:** 3.9.15  
**Python:** 3.14.6  

---

## 1. Objetivo

Medir el rendimiento de una función crítica desde el frontend y el backend, identificar un cuello de botella, aplicar una optimización y demostrar su efecto mediante métricas comparables antes y después.

---

## 2. Escenario de prueba

Se implementó un servicio que devuelve una lista de productos para simular una consulta utilizada por un dashboard académico.

Se desarrollaron dos versiones del mismo proceso:

- **Versión base:** incorpora un retraso controlado de 200 ms y transforma los nombres de los productos en cada solicitud.
- **Versión optimizada:** elimina el retraso y reutiliza una lista previamente transformada.

### Endpoints evaluados

```text
GET /rendimiento/productos/base
GET /rendimiento/productos/optimizado
```

### Página frontend

```text
http://localhost:8080/rendimiento.html
```

---

## 3. Configuración de la medición

| Parámetro | Valor |
|---|---:|
| Solicitudes de calentamiento | 5 por endpoint |
| Mediciones | 30 por endpoint |
| Tiempo máximo de espera | 5 segundos |
| Métrica principal | Percentil 95 (p95) |
| Condición de éxito | p95 optimizado menor que p95 base |
| Errores permitidos | 0 |

Para mantener la comparabilidad, ambas versiones fueron evaluadas en el mismo equipo, con la misma aplicación, la misma JVM y la misma cantidad de repeticiones.

---

## 4. Hipótesis inicial

La versión base presentará una latencia mayor debido a la espera controlada de 200 ms y a la transformación repetida de los productos en cada solicitud.

La versión optimizada deberá responder más rápido porque elimina la espera y reutiliza una respuesta preparada previamente.

---

## 5. Implementación

### 5.1 Versión base

La versión base realiza las siguientes operaciones en cada solicitud:

1. Ejecuta una espera controlada de 200 ms.
2. Recorre la lista de productos.
3. Convierte cada producto a mayúsculas.
4. Construye una nueva lista de respuesta.

### 5.2 Versión optimizada

La versión optimizada transforma la lista una sola vez durante la inicialización del servicio.

En cada solicitud únicamente devuelve la lista inmutable ya preparada, evitando repetir el procesamiento y eliminando la espera controlada.

---

## 6. Herramientas utilizadas

- Spring Boot
- Java
- Maven
- Python
- Chrome DevTools
- `curl`
- Visual Studio Code
- Git

---

## 7. Resultados obtenidos

### 7.1 Resultados generales

| Versión | Promedio | Mediana | p95 | Mínimo | Máximo | Errores |
|---|---:|---:|---:|---:|---:|---:|
| Base | 216.59 ms | 218.77 ms | 222.14 ms | 204.49 ms | 227.89 ms | 0 |
| Optimizada | 11.92 ms | 14.23 ms | 23.70 ms | 1.40 ms | 28.79 ms | 0 |

### 7.2 Comparación del p95

| Métrica | Resultado |
|---|---:|
| p95 base | 222.14 ms |
| p95 optimizado | 23.70 ms |
| Reducción del p95 | 198.44 ms |
| Mejora porcentual | 89.33 % |

---

## 8. Cálculo de la mejora

Se utilizó la siguiente fórmula:

```text
Mejora (%) = ((p95_base - p95_optimizado) / p95_base) × 100
```

Sustituyendo los valores obtenidos:

```text
Mejora (%) = ((222.14 - 23.70) / 222.14) × 100
```

Resultado:

```text
Mejora = 89.33 %
```

La versión optimizada redujo el percentil 95 en aproximadamente **89.33 %** respecto a la versión base.

---

## 9. Interpretación de resultados

La versión base obtuvo un p95 de **222.14 ms**, valor coherente con la espera controlada de 200 ms incorporada al servicio.

La versión optimizada obtuvo un p95 de **23.70 ms**, lo que demuestra una reducción considerable en el tiempo de respuesta.

La mediana disminuyó de **218.77 ms** a **14.23 ms**, mientras que el promedio pasó de **216.59 ms** a **11.92 ms**.

No se registraron errores HTTP en ninguna de las 60 mediciones realizadas. Esto confirma que la mejora observada corresponde al rendimiento y no a fallos funcionales.

Los resultados permiten concluir que el principal cuello de botella se encontraba en el backend, específicamente en la espera controlada y en el procesamiento repetido de la lista.

---

## 10. Medición desde el frontend

La página `rendimiento.html` utiliza `performance.now()` para medir el tiempo percibido por el usuario desde el navegador.

Durante la validación con Chrome DevTools deben registrarse:

- Estado HTTP.
- Tiempo total.
- TTFB.
- Tamaño de la respuesta.
- Actividad de scripting.
- Posibles tareas largas en el hilo principal.

Las pruebas deben repetirse bajo las mismas condiciones para ambos endpoints.

---

## 11. CPU y memoria

La guía solicita observar CPU y memoria mediante VisualVM o Java Flight Recorder.

Los valores se completarán después de obtener las capturas correspondientes.

| Versión | CPU pico | Heap pico |
|---|---:|---:|
| Base | Pendiente | Pendiente |
| Optimizada | Pendiente | Pendiente |

No se debe interpretar un pico aislado. La evaluación debe considerar la tendencia, la duración y la repetibilidad durante las solicitudes.

---

## 12. Prueba automatizada

Se implementó la prueba:

```text
src/test/java/pe/unas/demoapi/RendimientoControllerTest.java
```

La prueba verifica que ambos endpoints:

- respondan con estado HTTP 200;
- devuelvan contenido JSON;
- retornen cinco productos;
- presenten los nombres correctamente transformados.

Esta validación permite distinguir un problema funcional de un problema de rendimiento.

---

## 13. Evidencias

Las evidencias de la práctica se almacenan en:

```text
evidencia/sesion27/
```

Archivos y capturas requeridas:

- `resultados_rendimiento.txt`
- Captura de DevTools Network de la versión base.
- Captura de DevTools Network de la versión optimizada.
- Captura de DevTools Performance.
- Captura de VisualVM o Java Flight Recorder.
- Evidencia de la ejecución de las pruebas automatizadas.

---

## 14. Criterio de éxito

| Criterio | Resultado |
|---|---|
| p95 optimizado menor que p95 base | Cumplido |
| Errores en la versión base | 0 |
| Errores en la versión optimizada | 0 |
| Mejora porcentual | 89.33 % |
| Resultado final | **CUMPLIDO** |

---

## 15. Conclusión

La práctica permitió identificar un cuello de botella en el backend provocado por una espera controlada y por la repetición innecesaria de operaciones en cada solicitud.

Después de aplicar la optimización, el p95 disminuyó de **222.14 ms** a **23.70 ms**, lo que representa una mejora de **89.33 %**.

Asimismo, ambas versiones completaron las mediciones sin errores HTTP. Por lo tanto, el criterio de éxito definido para la práctica fue cumplido.

Los resultados demuestran la importancia de medir antes y después de optimizar, utilizar varias repeticiones, realizar calentamiento previo de la JVM y considerar métricas como la mediana y el p95 en lugar de basar una conclusión en una sola ejecución.