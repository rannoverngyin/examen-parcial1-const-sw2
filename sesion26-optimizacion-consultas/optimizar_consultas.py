import sqlite3
import time

conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

indices = [
    "CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo)",
    "CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota_estudiante ON matriculas(semestre, nota, estudiante_id)"

]

for idx in indices:
    cur.execute(idx)
conn.commit()

consultas = {
    "Q1_busqueda_codigo": """
        SELECT * FROM estudiantes WHERE codigo = '202600120'
    """,
    "Q2_matriculas_semestre": """
        SELECT * FROM matriculas WHERE semestre = '2026-I'
    """,
    "Q3_join_escuela_semestre": """
        SELECT e.codigo, e.nombre, m.semestre, m.nota
        FROM estudiantes e
        JOIN matriculas m ON e.id = m.estudiante_id
        WHERE e.escuela = 'FIIS' AND m.semestre = '2026-I'
    """,
    "Q4_ejercicio_aplicado": """
        SELECT e.codigo, e.nombre, m.nota
        FROM estudiantes e
        JOIN matriculas m ON e.id = m.estudiante_id
        WHERE e.escuela = 'FIIS'
          AND m.semestre = '2026-I'
          AND m.nota >= 14
    """

}

for nombre, sql in consultas.items():
    inicio = time.perf_counter()
    resultados = cur.execute(sql).fetchall()
    fin = time.perf_counter()
    print(f"{nombre}: {len(resultados)} filas | {fin - inicio:.6f} segundos")
    print("PLAN:")
    for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
        print(row)
    print("-" * 60)

conn.close()
