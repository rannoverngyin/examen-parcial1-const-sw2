from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.orm import declarative_base, sessionmaker

Base = declarative_base()

class Curso(Base):
    __tablename__ = "cursos"
    id = Column(Integer, primary_key=True)
    codigo = Column(String, index=True)
    nombre = Column(String)
    ciclo = Column(Integer, index=True)
    creditos = Column(Integer)
    docente = Column(String, index=True)

engine = create_engine("sqlite:///fiis.db", echo=False)
Base.metadata.drop_all(engine)
Base.metadata.create_all(engine)

Session = sessionmaker(bind=engine)
session = Session()

cursos_base = [
    ("IS040701", "Arquitectura de Software", 7, 4, "Dr. Garcia"),
    ("IS040703", "Construccion de Software II", 7, 5, "Mg. Yanac"),
    ("IS040602", "Analitica de Datos", 6, 4, "Dra. Rios"),
    ("IS040801", "Calidad de Software", 8, 4, "Mg. Torres"),
]

for i in range(5000):
    codigo, nombre, ciclo, creditos, docente = cursos_base[i % len(cursos_base)]
    session.add(Curso(
        codigo=f"{codigo}-{i}",
        nombre=nombre,
        ciclo=ciclo,
        creditos=creditos,
        docente=docente
    ))

session.commit()
session.close()
print("Base de datos creada: fiis.db con 5000 cursos")