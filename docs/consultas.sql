-- ============================================================
-- Consultas del laboratorio - Sesión 26
-- Optimización de consultas con SQLite
-- ============================================================

-- Q1: Búsqueda por código de estudiante (SIN índice)
EXPLAIN QUERY PLAN
SELECT * FROM estudiantes WHERE codigo = '202600120';

-- Q1 con índice idx_estudiantes_codigo
CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo);
EXPLAIN QUERY PLAN
SELECT * FROM estudiantes WHERE codigo = '202600120';

-- Q2: Filtro por semestre (SIN índice)
EXPLAIN QUERY PLAN
SELECT * FROM matriculas WHERE semestre = '2026-I';

-- Q2 con índice compuesto idx_matriculas_semestre_estudiante
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id);
EXPLAIN QUERY PLAN
SELECT * FROM matriculas WHERE semestre = '2026-I';

-- Q3: JOIN estudiantes + matriculas por escuela y semestre
EXPLAIN QUERY PLAN
SELECT e.codigo, e.nombre, m.semestre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I';

-- Q4: JOIN con filtro adicional de nota >= 14
EXPLAIN QUERY PLAN
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14;

-- Índices creados para la optimización
-- Índices simples:
CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo);
CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela);
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre);
CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id);
CREATE INDEX IF NOT EXISTS idx_matriculas_nota ON matriculas(nota);

-- Índice compuesto:
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id);
