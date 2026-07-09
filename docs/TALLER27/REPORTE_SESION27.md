# Reporte técnico - Sesión 27

## 1. Escenario
- Endpoint base: `/rendimiento/productos/base`
- Endpoint optimizado: `/rendimiento/productos/optimizado`
- Dataset: 5 productos simulados para un dashboard académico.
- Repeticiones: 5 de calentamiento y 30 mediciones por endpoint.
- Equipo/entorno: Windows 10, Java 17, Spring Boot, navegador Chrome.
- Commit evaluado: completar después del commit.

## 2. Hipótesis inicial
Se espera que el endpoint base tenga mayor latencia debido a una espera simulada de 200 ms y al procesamiento repetido de la lista de productos en cada solicitud.  
El endpoint optimizado debería responder más rápido porque reutiliza una lista ya procesada.

## 3. Resultados

| Versión | Promedio | Mediana | p95 | Errores | CPU | Heap |
|---|---:|---:|---:|---:|---:|---:|
| Base | 213.91 ms | 214.53 ms | 220.10 ms | 0 | Pendiente captura VisualVM/JFR | Pendiente captura VisualVM/JFR |
| Optimizada | 14.79 ms | 15.16 ms | 16.24 ms | 0 | Pendiente captura VisualVM/JFR | Pendiente captura VisualVM/JFR |
| Mejora | 93.09 % | 92.93 % | 92.62 % | Sin errores | - | - |

## 4. Evidencias
- Captura DevTools Network. ![.](1_1.png) ![.](1_2.png)
- Captura Performance. ![.](2_1.png) ![.](2_2.png) 
- Captura VisualVM/JFR. ![.](3.png)
- Salida de medir_rendimiento.py. ![.](4.png)

## 5. Conclusión

El cuello de botella principal se encuentra en el backend. La versión base presenta una latencia alta porque incluye una espera controlada de 200 ms mediante `Thread.sleep(200)` y además transforma la lista de productos en cada solicitud.

La versión optimizada elimina la espera artificial y reutiliza una lista previamente procesada, evitando trabajo repetido en cada petición. Como resultado, el p95 disminuyó de 220.10 ms a 16.24 ms, lo que representa una mejora aproximada del 92.62 %.

No se registraron errores HTTP en ninguna versión, por lo que se cumple el criterio de éxito: el endpoint optimizado tiene un p95 menor que el endpoint base y ambos responden correctamente.