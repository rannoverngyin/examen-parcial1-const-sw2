from pathlib import Path

from sqlalchemy import create_engine, text


db_path = Path(__file__).resolve().parent / "fiis.db"
engine = create_engine(f"sqlite:///{db_path}")

with engine.connect() as conn:
    plan = conn.execute(text("EXPLAIN QUERY PLAN SELECT * FROM cursos WHERE ciclo = 7")).fetchall()
    for fila in plan:
        print(fila)
