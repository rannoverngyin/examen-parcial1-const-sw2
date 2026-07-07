-- Ejercicio aplicado (Sesión 26)
-- Estudiantes de la FIIS con nota >= 14 en el semestre 2026-I

-- Índice adecuado para esta consulta:
-- cubre el filtro por escuela y por nota, combinado con el índice
-- ya existente idx_matriculas_semestre_estudiante para el JOIN + semestre.
CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela);
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota);

-- Consulta Q4: estudiantes de FIIS con nota >= 14 en 2026-I
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14;

-- Para revisar el plan de ejecución de esta consulta en Python/sqlite3:
-- cur.execute("EXPLAIN QUERY PLAN " + sql_q4)
