import sqlite3
import time

conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

sql_q4 = """
    SELECT e.codigo, e.nombre, m.nota
    FROM estudiantes e
    JOIN matriculas m ON e.id = m.estudiante_id
    WHERE e.escuela = 'FIIS'
      AND m.semestre = '2026-I'
      AND m.nota >= 14
"""


def medir_y_mostrar(etiqueta):
    inicio = time.perf_counter()
    resultados = cur.execute(sql_q4).fetchall()
    fin = time.perf_counter()
    tiempo = fin - inicio
    print(f"{etiqueta}: {len(resultados)} filas | {tiempo:.6f} segundos")
    print("PLAN:")
    for row in cur.execute("EXPLAIN QUERY PLAN " + sql_q4):
        print(row)
    print("-" * 60)
    return tiempo


# 1) Medición ANTES de crear el índice específico para esta consulta
tiempo_antes = medir_y_mostrar("Q4_fiis_nota_mayor_igual_14 (ANTES)")

# 2) Crear el índice adecuado para esta consulta
cur.execute("CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota)")
conn.commit()

# 3) Medición DESPUÉS de crear el índice
tiempo_despues = medir_y_mostrar("Q4_fiis_nota_mayor_igual_14 (DESPUES)")

# 4) Calcular mejora porcentual
if tiempo_antes > 0:
    mejora = ((tiempo_antes - tiempo_despues) / tiempo_antes) * 100
    print(f"Mejora: {mejora:.2f}%")

conn.close()
