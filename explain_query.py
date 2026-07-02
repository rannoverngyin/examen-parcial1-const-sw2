from sqlalchemy import create_engine, text
engine = create_engine("sqlite:///fiis.db")
with engine.connect() as conn:
    plan = conn.execute(text("EXPLAIN QUERY PLAN SELECT * FROM cursos WHERE docente = 'Mg. Yanac'")).fetchall()
    for fila in plan:
        print(fila)