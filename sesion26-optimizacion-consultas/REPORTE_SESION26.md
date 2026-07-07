# Reporte Sesión 26 – Optimización de consultas

## 1. Consulta analizada
Se evaluaron y compararon 4 consultas en la base de datos `universidad.db` (que contiene 5,000 estudiantes y 80,000 registros de matrículas) para identificar cuellos de botella y analizar el comportamiento de SQLite frente a la adición de índices.

Las consultas analizadas fueron:
- **Q1_busqueda_codigo**: Búsqueda puntual de un estudiante por su código (`codigo = '202600120'`).
- **Q2_matriculas_semestre**: Obtención de todas las matrículas del semestre `'2026-I'`.
- **Q3_join_escuela_semestre**: JOIN entre `estudiantes` y `matriculas` para listar estudiantes de la escuela `'FIIS'` matriculados en el semestre `'2026-I'`.
- **Q4_fiis_nota_mayor_igual_14** (Ejercicio Aplicado): Listar estudiantes de la escuela `'FIIS'` con nota $\ge 14$ en el semestre `'2026-I'`.

---

## 2. Tiempo antes de optimizar
Tiempos de ejecución y planes de consulta medidos sobre la base de datos original (sin índices adicionales en campos como `codigo`, `escuela`, `semestre` o `nota`):

| Consulta | Antes (s) | Plan (antes) |
|---|---|---|
| **Q1_busqueda_codigo** | 0.001636 s | SCAN TABLE estudiantes |
| **Q2_matriculas_semestre** | 0.049791 s | SCAN TABLE matriculas |
| **Q3_join_escuela_semestre** | 0.043607 s | SCAN TABLE matriculas m <br> SEARCH e USING INTEGER PRIMARY KEY (rowid=?) |
| **Q4_fiis_nota_mayor_igual_14** | 0.013198 s | SCAN TABLE matriculas m <br> SEARCH e USING INTEGER PRIMARY KEY (rowid=?) |

---

## 3. Índice o cambio aplicado
Se aplicaron los siguientes índices para optimizar las rutas de acceso del planificador de consultas de SQLite:

1. `idx_estudiantes_codigo` sobre `estudiantes(codigo)`
2. `idx_estudiantes_escuela` sobre `estudiantes(escuela)`
3. `idx_matriculas_semestre` sobre `matriculas(semestre)`
4. `idx_matriculas_estudiante` sobre `matriculas(estudiante_id)`
5. `idx_matriculas_semestre_estudiante` sobre `matriculas(semestre, estudiante_id)`
6. `idx_matriculas_semestre_nota` sobre `matriculas(semestre, nota)` *(específico para el Ejercicio Q4)*

---

## 4. Tiempo después de optimizar
Medición de los tiempos de ejecución tras la creación de los índices correspondientes:

| Consulta | Después (s) | Mejora (%) | Plan (después) |
|---|---|---|---|
| **Q1_busqueda_codigo** | 0.000213 s | **+86.98%** | SEARCH estudiantes USING INDEX idx_estudiantes_codigo (codigo=?) |
| **Q2_matriculas_semestre** | 0.104709 s | **-110.30%** | SEARCH matriculas USING INDEX idx_matriculas_semestre_estudiante (semestre=?) |
| **Q3_join_escuela_semestre** | 0.041039 s | **+5.89%** | SEARCH m USING INDEX idx_matriculas_semestre_estudiante (semestre=?) <br> SEARCH e USING INTEGER PRIMARY KEY (rowid=?) |
| **Q4_fiis_nota_mayor_igual_14** | 0.020039 s | **-51.83%** | SEARCH m USING INDEX idx_matriculas_semestre_nota (semestre=? AND nota>?) <br> SEARCH e USING INTEGER PRIMARY KEY (rowid=?) |

*Fórmula empleada: `mejora = ((tiempo_antes - tiempo_despues) / tiempo_antes) * 100`*

---

## 5. Interpretación técnica

- **Q1_busqueda_codigo (Mejora significativa: +86.98%)**:
  Al buscar por el código de un estudiante, la base de datos realiza una búsqueda exacta de un único registro. Sin el índice, SQLite tuvo que hacer un escaneo completo (`SCAN TABLE`) de los 5,000 registros de la tabla. Al agregar el índice `idx_estudiantes_codigo`, la complejidad bajó a $O(\log N)$ (búsqueda B-Tree) devolviendo instantáneamente la fila sin recorrer el resto de la tabla. La alta selectividad del campo hace que el índice sea sumamente eficiente.

- **Q2_matriculas_semestre (Degradación del tiempo: -110.30%)**:
  La consulta filtraba por el semestre `'2026-I'`, retornando **26,738 filas** de un total de 80,000 (~33% de la tabla `matriculas`). Cuando el volumen de filas a retornar es tan grande (baja selectividad), utilizar un índice obliga a SQLite a hacer una doble búsqueda por cada fila: primero localiza la clave en el B-Tree del índice y luego hace un acceso aleatorio (random I/O) en las páginas del archivo de base de datos para recuperar la fila real de la tabla. En este escenario, hacer un escaneo secuencial directo de toda la tabla (`SCAN TABLE`) es sustancialmente más rápido debido a la lectura continua en memoria y al aprovechamiento del caché de disco.

- **Q3_join_escuela_semestre (Mejora marginal: +5.89%)**:
  SQLite utilizó el índice compuesto `idx_matriculas_semestre_estudiante` para acotar la búsqueda inicial al semestre `'2026-I'`, y a partir de ahí realizó la unión con la clave primaria de `estudiantes` para validar el filtro `escuela = 'FIIS'`. A pesar del uso de índices, la mejora es modesta debido a que el volumen de datos filtrados sigue siendo alto (6,823 filas resultantes) y el costo computacional de realizar miles de búsquedas JOIN sobre `estudiantes` en caliente reduce el impacto positivo del índice.

- **Q4_fiis_nota_mayor_igual_14 (Degradación del tiempo: -51.83%)**:
  Para el ejercicio aplicado, se creó el índice `idx_matriculas_semestre_nota`. La consulta devolvió **2,046 filas**, pero requirió evaluar más de 8,000 matrículas que cumplían el filtro de semestre y nota antes de cruzarlas con la tabla `estudiantes`. De forma análoga a Q2, la sobrecarga de acceder aleatoriamente al índice compuesto y luego buscar en la tabla `matriculas` para recuperar el ID del estudiante superó el costo de un escaneo secuencial simple sobre la tabla en memoria. Esto ejemplifica que los índices no son mágicos y que el planificador de consultas de la base de datos puede tomar decisiones ineficientes cuando las tablas son pequeñas y caben enteramente en la memoria RAM del sistema.

---

## 6. Evidencias

### Creación de base de datos (`crear_bd.py`) y ejecución de Ejercicio Aplicado (`ejercicio_aplicado.py`)
![Creación de BD y Ejercicio Aplicado](./image%20copy%202.png)

### Tiempos de ejecución iniciales (`medir_consultas.py`) - Antes de optimizar
![Tiempos de ejecución antes de optimizar](./image.png)

### Tiempos de ejecución optimizados (`optimizar_consultas.py`) - Después de optimizar
![Tiempos de ejecución después de optimizar](./image%20copy.png)

