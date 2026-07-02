from sqlalchemy import create_engine, text

# Conexión a la base SQLite de la práctica
engine = create_engine("sqlite:///fiis.db")

# Crea un índice sobre la columna docente si todavía no existe
with engine.connect() as conn:
    conn.execute(
        text("CREATE INDEX IF NOT EXISTS idx_cursos_docente ON cursos(docente)")
    )
    conn.commit()

print("Índice creado: idx_cursos_docente")