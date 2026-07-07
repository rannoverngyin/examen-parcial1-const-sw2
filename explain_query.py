from sqlalchemy import create_engine, text

engine = create_engine("sqlite:///fiis.db")

with engine.connect() as conn:
    print("=== Plan para: ciclo = 7 ===")
    plan = conn.execute(text("EXPLAIN QUERY PLAN SELECT * FROM cursos WHERE ciclo = 7")).fetchall()
    for fila in plan:
        print(fila)
    
    print("\n=== Plan para: docente = 'Mg. Yanac' ===")
    plan2 = conn.execute(text("EXPLAIN QUERY PLAN SELECT * FROM cursos WHERE docente = 'Mg. Yanac'")).fetchall()
    for fila in plan2:
        print(fila)