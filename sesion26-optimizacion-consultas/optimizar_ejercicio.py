import sqlite3
import time


conn = sqlite3.connect("universidad.db")
cur = conn.cursor()


# Índice específico para la consulta Q4
cur.execute("""
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota_estudiante
ON matriculas(semestre, nota, estudiante_id)
""")

conn.commit()


sql = """
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m
    ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14
"""


inicio = time.perf_counter()

resultados = cur.execute(sql).fetchall()

fin = time.perf_counter()


print(
    f"Q4_FIIS_aprobados_optimizada: "
    f"{len(resultados)} filas | "
    f"{fin - inicio:.6f} segundos"
)


print("PLAN:")

for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
    print(row)


conn.close()