-- ============================================================
-- SESIÓN 26 - OPTIMIZACIÓN DE CONSULTAS
-- Archivo: consultas.sql
-- Descripción: Contiene todas las consultas SQL utilizadas
-- en el laboratorio de optimización
-- ============================================================

-- ============================================================
-- 1. CONSULTAS ORIGINALES (SIN OPTIMIZAR)
-- ============================================================

-- Q1: Búsqueda de estudiante por código (búsqueda exacta)
-- Problema: SCAN TABLE estudiantes (escaneo completo)
SELECT * FROM estudiantes WHERE codigo = '202600120';

-- Q2: Matrículas por semestre específico
-- Problema: SCAN TABLE matriculas (escaneo completo)
SELECT * FROM matriculas WHERE semestre = '2026-I';

-- Q3: JOIN para obtener estudiantes FIIS en un semestre
-- Problema: SCAN TABLE matriculas + JOIN costoso
SELECT e.codigo, e.nombre, m.semestre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I';

-- ============================================================
-- 2. CONSULTAS OPTIMIZADAS (CON ÍNDICES)
-- ============================================================

-- Q1 Optimizada: Usa idx_estudiantes_codigo
-- Plan: SEARCH estudiantes USING INDEX idx_estudiantes_codigo
SELECT * FROM estudiantes WHERE codigo = '202600120';

-- Q2 Optimizada: Usa idx_matriculas_compuesto
-- Plan: SEARCH matriculas USING INDEX idx_matriculas_compuesto
SELECT * FROM matriculas WHERE semestre = '2026-I';

-- Q3 Optimizada: Usa índices en ambas tablas
-- Plan: SEARCH m USING INDEX idx_matriculas_compuesto
--       SEARCH e USING INTEGER PRIMARY KEY
SELECT e.codigo, e.nombre, m.semestre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I';

-- ============================================================
-- 3. EJERCICIO ADICIONAL
-- ============================================================

-- Q4: Estudiantes FIIS con nota >= 14 en semestre 2026-I
-- Optimización sugerida: índice compuesto en (semestre, nota)
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14;

-- ============================================================
-- 4. ÍNDICES CREADOS
-- ============================================================

-- Índice para búsqueda por código de estudiante
CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo);

-- Índice para filtrar por escuela
CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela);

-- Índice simple para semestre
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre);

-- Índice para JOIN por estudiante_id
CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id);

-- Índice compuesto para semestre y estudiante (optimiza JOINs)
CREATE INDEX IF NOT EXISTS idx_matriculas_compuesto ON matriculas(semestre, estudiante_id);

-- Índice para nota (ejercicio adicional)
CREATE INDEX IF NOT EXISTS idx_matriculas_nota ON matriculas(nota);

-- Índice compuesto para semestre y nota (ejercicio adicional)
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota);

-- ============================================================
-- 5. CONSULTAS DE VERIFICACIÓN
-- ============================================================

-- Verificar índices creados
SELECT name, sql 
FROM sqlite_master 
WHERE type='index' 
ORDER BY name;

-- Estadísticas de las tablas
SELECT 'estudiantes' as tabla, COUNT(*) as total FROM estudiantes
UNION ALL
SELECT 'cursos', COUNT(*) FROM cursos
UNION ALL
SELECT 'matriculas', COUNT(*) FROM matriculas;

-- ============================================================
-- 6. ANÁLISIS DE PLANES DE EJECUCIÓN
-- ============================================================

-- Ver plan de ejecución de cada consulta
EXPLAIN QUERY PLAN 
SELECT * FROM estudiantes WHERE codigo = '202600120';

EXPLAIN QUERY PLAN 
SELECT * FROM matriculas WHERE semestre = '2026-I';

EXPLAIN QUERY PLAN 
SELECT e.codigo, e.nombre, m.semestre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I';

EXPLAIN QUERY PLAN 
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14;

-- ============================================================
-- 7. CONSULTAS PARA LIMPIEZA (OPCIONAL)
-- ============================================================

-- Eliminar todos los índices (para reiniciar pruebas)
-- DROP INDEX IF EXISTS idx_estudiantes_codigo;
-- DROP INDEX IF EXISTS idx_estudiantes_escuela;
-- DROP INDEX IF EXISTS idx_matriculas_semestre;
-- DROP INDEX IF EXISTS idx_matriculas_estudiante;
-- DROP INDEX IF EXISTS idx_matriculas_compuesto;
-- DROP INDEX IF EXISTS idx_matriculas_nota;
-- DROP INDEX IF EXISTS idx_matriculas_semestre_nota;