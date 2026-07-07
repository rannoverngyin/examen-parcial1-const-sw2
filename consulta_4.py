import sqlite3
import time

conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

print("EJERCICIO APLICADO - CONSULTA 4 (Versión Optimizada)\n")

sql = """
SELECT e.codigo, e.nombre, m.nota
FROM estudiantes e
JOIN matriculas m ON e.id = m.estudiante_id
WHERE e.escuela = 'FIIS'
  AND m.semestre = '2026-I'
  AND m.nota >= 14;
"""

# ====================== ANTES ======================
print("ANTES del nuevo índice:")
inicio = time.perf_counter()
resultados = cur.execute(sql).fetchall()
fin = time.perf_counter()
print(f"   → Filas: {len(resultados)} | Tiempo: {fin - inicio:.6f} segundos")

print("   → Plan antes:")
for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
    print(f"      {row}")

print("-" * 80)

# ====================== ÍNDICE ADECUADO ======================
print("Creando índice optimizado para Consulta 4...")

cur.execute("""
CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_nota 
ON matriculas(semestre, nota)
""")

conn.commit()
print("✓ Índice creado: idx_matriculas_semestre_nota (semestre, nota)\n")

# ====================== DESPUÉS ======================
print("DESPUÉS del nuevo índice:")
inicio = time.perf_counter()
resultados = cur.execute(sql).fetchall()
fin = time.perf_counter()
print(f"   → Filas: {len(resultados)} | Tiempo: {fin - inicio:.6f} segundos")

print("   → Plan después:")
for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
    print(f"      {row}")

conn.close()
print("\nConsulta 4 optimizada completada.")