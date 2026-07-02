import sqlite3
import time

conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

Q4 = """
    SELECT e.codigo, e.nombre, m.nota
    FROM estudiantes e
    JOIN matriculas m ON e.id = m.estudiante_id
    WHERE e.escuela = 'FIIS'
    AND m.semestre = '2026-I'
    AND m.nota >= 14
"""


def medir(etiqueta):
    inicio = time.perf_counter()
    resultados = cur.execute(Q4).fetchall()
    fin = time.perf_counter()
    print(f"{etiqueta}: {len(resultados)} filas | {fin - inicio:.6f} segundos")
    print("PLAN:")
    for row in cur.execute("EXPLAIN QUERY PLAN " + Q4):
        print(row)
    print("-" * 60)


print("=== Q4 ANTES del índice específico (semestre, nota) ===")
medir("Q4_antes")

cur.execute("CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota)")
conn.commit()

print("=== Q4 DESPUÉS del índice específico (semestre, nota) ===")
medir("Q4_despues")

conn.close()
