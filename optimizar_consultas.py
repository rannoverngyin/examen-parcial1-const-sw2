import sqlite3
import time

conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

# Crear índices estratégicos
print("=== CREANDO ÍNDICES ===\n")
indices = [
    "CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo)",
    "CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_compuesto ON matriculas(semestre, estudiante_id)"
]

for idx in indices:
    print(f"Ejecutando: {idx}")
    cur.execute(idx)
conn.commit()
print("\nÍndices creados exitosamente\n")

# Medir consultas optimizadas
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
    """
}

print("=== MEDICIÓN CON ÍNDICES ===\n")
for nombre, sql in consultas.items():
    print(f"Ejecutando: {nombre}")
    
    # Obtener plan de ejecución
    print("PLAN DE EJECUCIÓN:")
    for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
        print(f"  {row}")
    
    # Medir tiempo de ejecución
    inicio = time.perf_counter()
    resultados = cur.execute(sql).fetchall()
    fin = time.perf_counter()
    
    print(f"Filas devueltas: {len(resultados)}")
    print(f"Tiempo: {fin - inicio:.6f} segundos")
    print("-" * 70)
    print()

conn.close()