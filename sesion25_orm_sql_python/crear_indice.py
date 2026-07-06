from pathlib import Path

from sqlalchemy import create_engine, text


db_path = Path(__file__).resolve().parent / "fiis.db"
engine = create_engine(f"sqlite:///{db_path}")

with engine.connect() as conn:
    conn.execute(text("CREATE INDEX IF NOT EXISTS idx_cursos_docente ON cursos(docente)"))
    conn.commit()

print("Índice creado: idx_cursos_docente")
