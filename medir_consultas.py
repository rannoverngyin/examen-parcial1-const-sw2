import sqlite3
import time

# Nos conectamos a la base de datos universidad
conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

# Mapeamos las consultas requeridas por la guía, incluyendo el reto de optimización
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
    "Q4_reto_estudiantes_fiis_aprobados": """
        SELECT e.codigo, e.nombre, m.nota
        FROM estudiantes e
        JOIN matriculas m ON e.id = m.estudiante_id
        WHERE e.escuela = 'FIIS'  
          AND m.semestre = '2026-I'  
          AND m.nota >= 14
    """
}

print("=== EJECUCIÓN INICIAL: SIN ÍNDICES (TIEMPOS LENTOS) ===\n")

for nombre, sql in consultas.items():
    # Medimos con precisión de reloj de CPU de alta resolución
    inicio = time.perf_counter()
    resultados = cur.execute(sql).fetchall()
    fin = time.perf_counter()
    
    tiempo = fin - inicio
    print(f"{nombre}: {len(resultados)} filas recuperadas | {tiempo:.6f} segundos")
    
    # Explicamos qué estrategia usó SQLite para resolverlo
    print("PLAN DE CONSULTA:")
    for plan in cur.execute("EXPLAIN QUERY PLAN " + sql):
        print(f"  {plan}")
    print("-" * 70)

conn.close()