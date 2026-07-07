# Reporte Sesión 26 – Optimización de Consultas

## 1. Consultas Analizadas
Se evaluaron tres consultas base de gestión académica y una cuarta consulta avanzada correspondiente al ejercicio aplicado[cite: 75, 126]:
- **Q1 (Búsqueda por código):** Búsqueda puntual de un estudiante mediante su código único[cite: 98].
- **Q2 (Matrículas por semestre):** Filtro masivo de registros correspondientes a un periodo académico específico[cite: 98].
- **Q3 (Join Escuela/Semestre):** Cruce de información entre estudiantes de una escuela y sus matrículas en un semestre determinado[cite: 98].
- **Q4 (Ejercicio Aplicado):** Estudiantes de la escuela de 'FIIS' con notas aprobatorias sobresalientes ($\ge 14$) durante el semestre '2026-I'[cite: 126, 128].

## 2. Cuadro Comparativo de Rendimiento
Basado en las mediciones e inspección de los planes de ejecución obtenidos en la terminal[cite: 75]:

| Consulta | Filas | Tiempo Antes (s) | Tiempo Después (s) | Mejora (%) | Plan Optimizado Final |
|---|---:|---:|---:|---:|---|
| **Q1_busqueda_codigo** | 1 | 0.000733 | 0.000342 | **+53.34%** | `SEARCH estudiantes USING INDEX idx_estudiantes_codigo` |
| **Q2_matriculas_semestre** | 26528 | 0.033401 | 0.099144 | *-196.83%* | `SEARCH matriculas USING INDEX idx_matriculas_compuesto_nota` |
| **Q3_join_escuela_semestre** | 6515 | 0.018350 | 0.088105 | *-380.14%* | `SEARCH m USING INDEX idx_matriculas_compuesto_nota` + `SEARCH e` |
| **Q4_ejercicio_aplicado** | 1952 | N/A | 0.021525 | **Óptimo** | `SEARCH m USING INDEX idx_matriculas_compuesto_nota` + `SEARCH e` |

*Fórmula de mejora aplicada:* $\text{Mejora} = \frac{\text{Tiempo Antes} - \text{Tiempo Después}}{\text{Tiempo Antes}} \times 100$ 

## 3. Interpretación Técnica

1. **Optimización Exitosa en Búsquedas Puntuales (Q1):** En la consulta **Q1**, el motor pasó de realizar un escaneo completo de la tabla (`SCAN estudiantes`) a una búsqueda directa usando el árbol del índice (`SEARCH ... USING INDEX`)[cite: 104]. Esto redujo el tiempo de respuesta a más de la mitad (+53.34% de eficiencia).

2. **El Fenómeno de Q2 y Q3 (¿Por qué aumentó el tiempo?):**
   Las consultas **Q2** y **Q3** devolvieron un volumen masivo de datos (26,528 y 6,515 filas respectivamente). 
   - **Antes del índice:** SQLite leyó la tabla de manera secuencial (`SCAN`). Como los datos estaban contiguos en el disco/memoria caché, la lectura fue lineal y veloz.
   - **Después del índice:** El motor usó el índice compuesto `idx_matriculas_compuesto_nota`. Para cada una de las 26,528 filas, tuvo que buscar primero en el índice y luego saltar a la posición física de la tabla (*Bookmark Lookup*).Este constante "salto" en un volumen tan alto de filas genera una sobrecarga que supera el beneficio del índice[cite: 114, 141]. ¡Esto demuestra empíricamente que los índices no siempre aceleran consultas si la selectividad es baja o devuelven demasiadas filas[cite: 114, 141]!

3. **Análisis del Ejercicio Aplicado (Q4):**
   La consulta **Q4** demostró un rendimiento altamente competitivo (solo 0.021525 segundos) para procesar el volumen de matrículas de la FIIS. Gracias al índice compuesto `idx_matriculas_compuesto_nota (semestre=? AND nota>?)`, el motor filtró en un solo paso el periodo académico y el rango de notas ($\ge 14$), evitando cargar las más de 26,000 filas del semestre general en memoria y resolviendo el cruce mediante la llave primaria de estudiantes de forma directa.

## 4. Evidencias de Ejecución
- **Volumen de Carga:** Base de datos `universidad.db` poblada con 5,000 estudiantes y 80,000 registros de matrículas en un entorno local[cite: 89, 93].
- **Estrategia Remota:** Código fuente e informe técnico listos para ser versionados y consolidados en la rama de despliegue[cite: 133].