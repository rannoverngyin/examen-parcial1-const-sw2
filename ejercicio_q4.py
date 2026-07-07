import sqlite3
import time

DB = "universidad.db"

SQL_Q4 = """
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14
"""

INDICES_Q4 = [
    "CREATE INDEX IF NOT EXISTS idx_q4_matriculas_semestre_nota_estudiante ON matriculas(semestre, nota, estudiante_id)",
    "CREATE INDEX IF NOT EXISTS idx_q4_estudiantes_escuela_id ON estudiantes(escuela, id)"
]

DROP_Q4 = [
    "DROP INDEX IF EXISTS idx_q4_matriculas_semestre_nota_estudiante",
    "DROP INDEX IF EXISTS idx_q4_estudiantes_escuela_id"
]

def medir(cur, etiqueta):
    print(f"\n{etiqueta}")

    inicio = time.perf_counter()
    resultados = cur.execute(SQL_Q4).fetchall()
    fin = time.perf_counter()

    tiempo = fin - inicio

    print(f"Q4_fiis_nota_14_2026I: {len(resultados)} filas | {tiempo:.6f} segundos")
    print("PLAN:")

    for row in cur.execute("EXPLAIN QUERY PLAN " + SQL_Q4):
        print(row)

    print("-" * 60)
    return tiempo


conn = sqlite3.connect(DB)
cur = conn.cursor()

# Se eliminan solo los índices específicos de la Q4 para medir el antes
for idx in DROP_Q4:
    cur.execute(idx)

conn.commit()

antes = medir(cur, "ANTES del índice específico para Q4")

# Se crean índices adecuados para la consulta Q4
for idx in INDICES_Q4:
    cur.execute(idx)

conn.commit()

despues = medir(cur, "DESPUÉS del índice específico para Q4")

if antes > 0:
    mejora = ((antes - despues) / antes) * 100
else:
    mejora = 0

print(f"\nMejora Q4: {mejora:.2f}%")

conn.close()