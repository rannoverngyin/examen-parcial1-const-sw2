import sqlite3
import time

# Establecer conexión con la base de datos local
conn = sqlite3.connect("universidad.db")
cur = conn.cursor()

# 1. Definición e inyección de índices estructurados (Simples y Compuestos)
indices = [
    "CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo ON estudiantes(codigo)",
    "CREATE INDEX IF NOT EXISTS idx_estudiantes_escuela ON estudiantes(escuela)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre ON matriculas(semestre)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_estudiante ON matriculas(estudiante_id)",
    "CREATE INDEX IF NOT EXISTS idx_matriculas_semestre_estudiante ON matriculas(semestre, estudiante_id)"
]

print("=== APLICANDO ÍNDICES DE OPTIMIZACIÓN ===")
for idx in indices:
    cur.execute(idx)
conn.commit()
print("Índices creados con éxito.\n")

# 2. Diccionario de sentencias SQL (Incluye el Reto Aplicado de evaluación)
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
    "Q4_reto_estudiantes_fiis_alta_nota": """
        SELECT e.codigo, e.nombre, m.nota
        FROM estudiantes e
        JOIN matriculas m ON e.id = m.estudiante_id
        WHERE e.escuela = 'FIIS'  
          AND m.semestre = '2026-I'  
          AND m.nota >= 14;
    """
}

print("=== MEDICIÓN POST-OPTIMIZACIÓN (CON ÍNDICES) ===")

# Evaluación del impacto de los índices en la latencia y planes de ejecución
for nombre, sql in consultas.items():
    # Medición de tiempo de CPU de alta precisión
    inicio = time.perf_counter()
    resultados = cur.execute(sql).fetchall()
    fin = time.perf_counter()
    
    # Imprimir métricas post-optimización
    print(f"{nombre}: {len(resultados)} filas | {fin - inicio:.6f} segundos")
    
    # Inspección técnica del nuevo Plan de Ejecución (Uso de índices vs Escaneo)
    print("PLAN:")
    for row in cur.execute("EXPLAIN QUERY PLAN " + sql):
        print(f"  {row}")
    print("-" * 60)

# Liberar recursos de infraestructura
conn.close()