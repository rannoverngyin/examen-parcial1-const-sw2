from pathlib import Path

from sqlalchemy import Column, Integer, String, create_engine
from sqlalchemy.orm import declarative_base, sessionmaker

Base = declarative_base()


class Curso(Base):
    __tablename__ = "cursos"

    id = Column(Integer, primary_key=True)
    codigo = Column(String, index=True, nullable=False)
    nombre = Column(String, nullable=False)
    ciclo = Column(Integer, index=True, nullable=False)
    creditos = Column(Integer, nullable=False)
    docente = Column(String, index=True, nullable=False)


db_path = Path(__file__).resolve().parent / "fiis.db"
engine = create_engine(f"sqlite:///{db_path}", echo=False)

Base.metadata.drop_all(engine)
Base.metadata.create_all(engine)

Session = sessionmaker(bind=engine)
session = Session()

cursos_base = [
    ("IS040701", "Arquitectura de Software", 7, 4, "Dr. García"),
    ("IS040703", "Construcción de Software II", 7, 5, "Mg. Yanac"),
    ("IS040602", "Analítica de Datos", 6, 4, "Dra. Ríos"),
    ("IS040801", "Calidad de Software", 8, 4, "Mg. Torres"),
]

for i in range(5000):
    codigo, nombre, ciclo, creditos, docente = cursos_base[i % len(cursos_base)]
    session.add(
        Curso(
            codigo=f"{codigo}-{i}",
            nombre=nombre,
            ciclo=ciclo,
            creditos=creditos,
            docente=docente,
        )
    )

session.commit()
session.close()

print(f"Base de datos creada: {db_path} con 5000 cursos")
