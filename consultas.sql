-- ============================================================================
-- CONSULTAS DEL LABORATORIO - SESIÓN 26: OPTIMIZACIÓN DE CONSULTAS
-- ============================================================================

-- Q1: Búsqueda exacta por código de estudiante
SELECT * 
FROM estudiantes 
WHERE codigo = '202600120';

-- Q2: Filtrado por semestre en tabla transaccional de matrículas
SELECT * 
FROM matriculas 
WHERE semestre = '2026-I';

-- Q3: Consulta con JOIN entre tabla de dimensiones (estudiantes) y hechos (matriculas)
SELECT e.codigo, e.nombre, m.semestre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS' 
  AND m.semestre = '2026-I';

-- Q4 (Ejercicio Aplicado): Estudiantes FIIS con nota >= 14 en el semestre 2026-I
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14;
