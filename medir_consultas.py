import sqlite3
import time

# Establecer conexión con la base de datos local
conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

# Diccionario de sentencias SQL bajo evaluación (Línea base sin índices)
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

print("=== MEDICIÓN INICIAL (SIN OPTIMIZAR) ===")

# Iterar y evaluar el rendimiento de cada consulta
for nombre, sql in consultas.items():
    # Capturar tiempo de CPU inicial de alta precisión
    inicio = time.perf_counter()
    resultados = cur.execute(sql).fetchall()
    fin = time.perf_counter()
    
    # Imprimir métricas de latencia y volumen de datos
    print(f"{nombre}: {len(resultados)} filas | {fin - inicio:.6f} segundos")
    
    # Extraer e inspeccionar el Plan de Ejecución del optimizador de SQLite
    print("PLAN:")
    for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
        print(f"  {row}")
    print("-" * 60)

# Liberar recursos de infraestructura
conn.close()