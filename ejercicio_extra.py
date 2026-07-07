import sqlite3
import time

conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

# Medir sin índice específico para nota
print("=== CONSULTA ADICIONAL SIN ÍNDICE ESPECÍFICO ===\n")
sql_extra = """
    SELECT e.codigo, e.nombre, m.nota
    FROM estudiantes e
    JOIN matriculas m ON e.id = m.estudiante_id
    WHERE e.escuela = 'FIIS'
      AND m.semestre = '2026-I'
      AND m.nota >= 14
"""

# Medir sin índice para nota
print("PLAN DE EJECUCIÓN (sin índice para nota):")
for row in cur.execute("EXPLAIN QUERY PLAN " + sql_extra):
    print(f"  {row}")

inicio = time.perf_counter()
resultados = cur.execute(sql_extra).fetchall()
fin = time.perf_counter()
print(f"Filas: {len(resultados)}")
print(f"Tiempo sin índice específico: {fin - inicio:.6f} segundos\n")

# Crear índice para nota
print("=== CREANDO ÍNDICE ADICIONAL ===\n")
cur.execute("CREATE INDEX IF NOT EXISTS idx_matriculas_nota ON matriculas(nota)")
cur.execute("CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota ON matriculas(semestre, nota)")
conn.commit()
print("Índices creados\n")

# Medir con índice
print("=== CONSULTA ADICIONAL CON ÍNDICE ESPECÍFICO ===\n")
print("PLAN DE EJECUCIÓN (con índice para nota):")
for row in cur.execute("EXPLAIN QUERY PLAN " + sql_extra):
    print(f"  {row}")

inicio = time.perf_counter()
resultados = cur.execute(sql_extra).fetchall()
fin = time.perf_counter()
print(f"Filas: {len(resultados)}")
print(f"Tiempo con índice específico: {fin - inicio:.6f} segundos")

conn.close()