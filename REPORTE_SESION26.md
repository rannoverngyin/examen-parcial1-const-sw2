# Reporte Sesión 26 – Optimización de consultas

## 1. Consulta analizada
**Q1_busqueda_codigo:** `SELECT * FROM estudiantes WHERE codigo = '202600120'`

**Q2_matriculas_semestre:** `SELECT * FROM matriculas WHERE semestre = '2026-I'`

**Q3_join_escuela_semestre:** `SELECT e.codigo, e.nombre, m.semestre, m.nota FROM estudiantes e JOIN matriculas m ON e.id = m.estudiante_id WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I'`

**Q4_nota_14 (extra):** `SELECT e.codigo, e.nombre, m.nota FROM estudiantes e JOIN matriculas m ON e.id = m.estudiante_id WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I' AND m.nota >= 14`

## 2. Tiempo antes de optimizar
| Consulta | Tiempo (s) | Plan de ejecución |
|----------|------------|-------------------|
| Q1 | 0.000586 | SCAN estudiantes |
| Q2 | 0.024283 | SCAN matriculas |
| Q3 | 0.016911 | SCAN m / SEARCH e PRIMARY KEY |
| Q4 | 0.028371 | SEARCH m idx_compuesto / SEARCH e PRIMARY KEY |

## 3. Índice o cambio aplicado
```sql
CREATE INDEX idx_estudiantes_codigo ON estudiantes(codigo);
CREATE INDEX idx_estudiantes_escuela ON estudiantes(escuela);
CREATE INDEX idx_matriculas_semestre ON matriculas(semestre);
CREATE INDEX idx_matriculas_estudiante ON matriculas(estudiante_id);
CREATE INDEX idx_matriculas_compuesto ON matriculas(semestre, estudiante_id);
CREATE INDEX idx_matriculas_semestre_nota ON matriculas(semestre, nota); -- Para Q4

## 4. Tiempo después de optimizar
Registrar tiempo y nuevo plan.
Consulta	 Tiempo (s)	         Nuevo plan de ejecución
Q1	        0.000165	         SEARCH idx_estudiantes_codigo
Q2	        0.044529	         SEARCH idx_matriculas_compuesto
Q3      	0.019022	         SEARCH m idx_compuesto / SEARCH e PRIMARY KEY
Q4	        0.023731	         SEARCH idx_matriculas_semestre_not

## 5. Interpretación técnica
Explicar por qué mejoró o no mejoró.


Q1 (mejora 71.8%): Índice sobre codigo permite búsqueda binaria directa. Es altamente selectivo porque el código es único.

Q2 (empeora 83.4%): Contraintuitivo. El índice no es selectivo porque devuelve 26,797 filas de 80,000 (33.5% de la tabla). El costo de navegar el índice y acceder a la tabla supera al escaneo secuencial. Los índices no siempre son la solución.

Q3 (empeora 12.5%): Similar a Q2, devuelve 7,151 filas. La mejora es marginal porque el filtro semestre no es muy restrictivo y el JOIN con estudiantes requiere accesos adicionales.

Q4 (mejora 16.4%): El índice compuesto (semestre, nota) filtra por dos condiciones, reduciendo el conjunto de 26,797 a 2,107 filas (7.9%). Más selectivo = mejor rendimiento.

Conclusión: Los índices funcionan mejor cuando son altamente selectivos (Q1, Q4). Para consultas que devuelven >30% de la tabla, el escaneo completo puede ser más eficiente (Q2). Siempre medir antes y después.