-- Q1: Búsqueda exacta de estudiante por su código
SELECT * FROM estudiantes WHERE codigo = '202600120';

-- Q2: Matrículas del semestre actual
SELECT * FROM matriculas WHERE semestre = '2026-I';

-- Q3: Uniones complejas (JOIN) con filtrado múltiple
SELECT e.codigo, e.nombre, m.semestre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I';

-- Q4 (RETO APLICADO): Estudiantes de FIIS aprobados en 2026-I con nota >= 14
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'  
  AND m.semestre = '2026-I'  
  AND m.nota >= 14;