from sqlalchemy import create_engine, text

# Conexión a la base de datos SQLite creada en el paso 5
engine = create_engine("sqlite:///fiis.db")

# Analiza cómo SQLite ejecuta la consulta filtrada por ciclo
with engine.connect() as conn:
    plan = conn.execute(
        text("EXPLAIN QUERY PLAN SELECT * FROM cursos WHERE ciclo = 7")
    ).fetchall()

    for fila in plan:
        print(fila)