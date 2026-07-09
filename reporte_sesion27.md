# Reporte técnico - Sesión 27

## 1. Escenario
- Endpoint: `GET /rendimiento/productos/base` vs `GET /rendimiento/productos/optimizado`
- Dataset: 5 productos (Laptop, Mouse, Teclado, Monitor, Impresora)
- Repeticiones: 5 de calentamiento + 30 mediciones por endpoint
- Equipo/entorno: <completar: CPU, RAM, SO, versión de Java>
- Commit evaluado: <completar: hash del commit sobre feature/s27-apellido-nombre>

## 2. Hipótesis inicial
La versión base incluye una espera artificial de 200 ms (`Thread.sleep(200)`) que
simula una operación externa lenta (por ejemplo, una consulta a base de datos o
una llamada a un servicio externo), además de recalcular la transformación de
mayúsculas en cada invocación. Se espera que el p95 de la versión base sea
cercano o superior a 200 ms, mientras que la versión optimizada, al reutilizar
una respuesta inmutable ya precalculada y no tener espera, debería responder en
tiempos de un solo dígito de milisegundos.

## 3. Resultados

| Versión    | Promedio | Mediana | p95 | Errores | CPU | Heap |
|------------|---------:|--------:|----:|--------:|----:|-----:|
| Base       |          |         |     |         |     |      |
| Optimizada |          |         |     |         |     |      |

> Complete esta tabla con la salida real de `scripts/medir_rendimiento.py`
> ejecutada en su equipo, y con los picos de CPU/Heap observados en
> VisualVM o Java Flight Recorder.

**Mejora (%) en p95** = ((p95_base − p95_optimizado) / p95_base) × 100 = <completar>

**Criterio de éxito** (p95 optimizado < p95 base, sin errores HTTP): <cumplido / no cumplido>

## 4. Evidencias
- Captura DevTools Network (base y optimizado): `evidencia/sesion27/network-base.png`, `evidencia/sesion27/network-optimizado.png`
- Captura DevTools Performance: `evidencia/sesion27/performance.png`
- Captura VisualVM/JFR: `evidencia/sesion27/visualvm.png`
- Salida de `medir_rendimiento.py`: `evidencia/sesion27/salida_script.txt`

## 5. Conclusión
<completar tras ejecutar las mediciones: indique el cuello de botella
identificado (espera artificial en el backend, no en el navegador/red),
la optimización aplicada (eliminación del retraso y precálculo de la
respuesta inmutable) y si el criterio de éxito se cumplió.>

## 6. Reto aplicado (profundización)

Se agregó el parámetro opcional `cantidad` a ambos endpoints
(`?cantidad=N`), que genera un dataset sintético de N elementos en lugar
de la lista fija de 5 productos. Esto permite medir cómo se degrada el
p95 al crecer el tamaño de la respuesta.

Ejecutar:
```
GET /rendimiento/productos/base?cantidad=1000
GET /rendimiento/productos/optimizado?cantidad=1000
```

o el script `scripts/medir_reto_cantidad.py`, que mide automáticamente con
10, 1 000 y 10 000 elementos.

| Cantidad | p95 Base (ms) | p95 Optimizado (ms) |
|---------:|---------------:|----------------------:|
| 10       |                |                        |
| 1 000    |                |                        |
| 10 000   |                |                        |

**Análisis:** <completar tras ejecutar: para cantidades pequeñas, el
retraso artificial de 200 ms domina el tiempo total y ambas versiones se
comportan de forma similar en la parte de generación de datos. A medida
que `cantidad` crece a 1 000 y 10 000, el costo de generar la lista y
serializarla a JSON empieza a aportar una fracción creciente del tiempo
total, incluso en la versión "optimizada" (que ya no tiene el sleep de
200 ms pero sí genera el dataset en cada llamada cuando se usa
`cantidad`). Indique a partir de qué tamaño el p95 optimizado deja de ser
prácticamente constante y si el cuello de botella parece estar en
generación de datos, serialización JSON, red o navegador.>
