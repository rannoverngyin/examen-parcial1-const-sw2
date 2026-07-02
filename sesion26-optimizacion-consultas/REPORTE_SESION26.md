# Reporte Sesión 26 -- Optimización de consultas

**Curso:** Construcción de Software II
**Base de datos:** `universidad.db` (SQLite) -- 5000 estudiantes, 4 cursos, 80 000 matrículas

---

## 1. Consulta analizada

Se evaluaron tres consultas sobre el módulo de estudiantes, cursos y matrículas de la FIIS-UNAS:

- **Q1** -- Búsqueda puntual de un estudiante por código exacto.
- **Q2** -- Listado de todas las matrículas de un semestre (`2026-I`), consulta de rango/igualdad sobre una tabla grande (80 000 filas).
- **Q3** -- JOIN entre `estudiantes` y `matriculas`, filtrando por escuela y semestre.
- **Q4 (ejercicio aplicado)** -- JOIN con triple condición: escuela `FIIS`, semestre `2026-I` y nota `>= 14`.

## 2. Tiempo antes de optimizar

| Consulta | Filas | Tiempo (s) | Plan de ejecución |
|---|--:|--:|---|
| Q1_busqueda_codigo | 1 | 0.000700 | `SCAN estudiantes` |
| Q2_matriculas_semestre | 26 598 | 0.032215 | `SCAN matriculas` |
| Q3_join_escuela_semestre | 7 010 | 0.020917 | `SCAN m` + `SEARCH e USING INTEGER PRIMARY KEY` |

Las tres consultas recorrían la tabla completa (`SCAN`) al menos en un punto del plan, ya que no existía ningún índice más allá de las claves primarias.

## 3. Índice o cambio aplicado

Se crearon los siguientes índices con `optimizar_consultas.py`:

```sql
CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo);
CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela);
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre);
CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id);
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id);
```

Adicionalmente, para el ejercicio aplicado (Q4) se creó un índice compuesto dedicado:

```sql
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota);
```

## 4. Tiempo después de optimizar

| Consulta | Filas | Tiempo (s) | Plan de ejecución |
|---|--:|--:|---|
| Q1_busqueda_codigo | 1 | 0.000117 | `SEARCH estudiantes USING INDEX idx_estudiantes_codigo (codigo=?)` |
| Q2_matriculas_semestre | 26 598 | 0.071958 | `SEARCH matriculas USING INDEX idx_matriculas_semestre_estudiante (semestre=?)` |
| Q3_join_escuela_semestre | 7 010 | 0.029395 | `SEARCH m USING INDEX ...` + `SEARCH e USING INTEGER PRIMARY KEY` |

**Ejercicio aplicado -- Q4:**

| Momento | Filas | Tiempo (s) | Plan de ejecución |
|---|--:|--:|---|
| Antes del índice dedicado | 2 114 | 0.024499 | `SEARCH m USING INDEX idx_matriculas_semestre_estudiante (semestre=?)` |
| Después del índice dedicado | 2 114 | 0.010722 | `SEARCH m USING INDEX idx_matriculas_semestre_nota (semestre=? AND nota>?)` |

Mejora de Q4: **((0.024499 - 0.010722) / 0.024499) × 100 ≈ 56.2 %**

## 5. Interpretación técnica

**Q1 (mejoró, 0.0007 s → 0.000117 s, ~83 %):** al ser una búsqueda por igualdad sobre una columna muy selectiva (`codigo` es único), el índice permite localizar la fila directamente en lugar de recorrer 5000 registros. Es el caso ideal para un índice.

**Q2 (empeoró, 0.0322 s → 0.0720 s):** este es el hallazgo más importante del laboratorio. El índice `idx_matriculas_semestre_estudiante` sí se usa (el plan cambió de `SCAN` a `SEARCH`), pero la consulta es poco selectiva: el semestre `2026-I` representa cerca de un tercio de las 80 000 matrículas (26 598 filas, ~33%). Cuando un índice devuelve una fracción tan grande de la tabla, SQLite debe hacer un "bookmark lookup" fila por fila para traer las columnas no incluidas en el índice (con `SELECT *`), lo cual puede ser más lento que un `SCAN` secuencial directo sobre la tabla. Esto confirma una de las buenas prácticas de la guía: **no todo índice mejora el rendimiento**; depende de la selectividad del filtro y de si la consulta usa `SELECT *`.

**Q3 (tiempo similar, 0.0209 s → 0.0294 s):** el JOIN sigue dominado por el volumen de filas que cumplen la condición de semestre (igual problema de selectividad que Q2), aunque el filtro adicional por escuela reduce el resultado final a 7010 filas. El overhead de recorrer el índice no compensa claramente frente al escaneo original en este volumen de datos.

**Q4 (mejoró claramente, ~56 %):** a diferencia de Q2 y Q3, aquí el índice compuesto `(semestre, nota)` sí ayuda de forma efectiva porque el plan de ejecución muestra `SEARCH ... (semestre=? AND nota>?)`: SQLite puede aplicar ambas condiciones directamente sobre el índice antes de acceder a la tabla, reduciendo el conjunto de filas candidatas de 26 598 (solo por semestre) a 2114 (semestre + nota ≥ 14) sin tener que evaluar la condición de nota fila por fila después de un `SEARCH` parcial.

**Conclusión general:** los índices no garantizan mejora automática. Ayudan cuando el filtro es selectivo (Q1, Q4) y pueden empeorar el rendimiento cuando la condición indexada devuelve una fracción grande de la tabla y la consulta trae columnas no cubiertas por el índice (Q2, Q3). La decisión de indexar debe basarse en medir, no en suponer.

## 6. Evidencias

- `crear_bd.py` → salida: `Base de datos universidad.db creada correctamente`
- `medir_consultas.py` → resultados "antes" (sección 2)
- `optimizar_consultas.py` → creación de 5 índices + resultados "después" (sección 4)
- `ejercicio_aplicado.py` → Q4 antes/después con índice compuesto dedicado (sección 4)
- Commit y push en rama `feature/sesion26-optimizacion`
