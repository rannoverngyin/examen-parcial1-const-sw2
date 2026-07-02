from sqlalchemy import create_engine, text
engine = create_engine("sqlite:///fiis.db")
with engine.connect() as conn:
    conn.execute(text("CREATE INDEX IF NOT EXISTS idx_cursos_docente ON cursos(docente)"))
    conn.commit()
print("Indice creado con exito: idx_cursos_docente")