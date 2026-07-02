import time
from tabulate import tabulate
from sqlalchemy import create_engine, text, Column, Integer, String
from sqlalchemy.orm import declarative_base, sessionmaker

Base = declarative_base()

class Curso(Base):
    __tablename__ = "cursos"
    id = Column(Integer, primary_key=True)
    codigo = Column(String)
    nombre = Column(String)
    ciclo = Column(Integer)
    creditos = Column(Integer)
    docente = Column(String)

engine = create_engine("sqlite:///fiis.db", echo=False)
Session = sessionmaker(bind=engine)

def medir(nombre, funcion):
    inicio = time.perf_counter()
    resultado = funcion()
    fin = time.perf_counter()
    return [nombre, len(resultado) if hasattr(resultado, "__len__") else resultado, round((fin - inicio) * 1000, 4)]

def orm_listar_ciclo_7():
    session = Session()
    datos = session.query(Curso).filter(Curso.ciclo == 7).all()
    session.close()
    return datos

def sql_listar_ciclo_7():
    with engine.connect() as conn:
        return conn.execute(text("SELECT * FROM cursos WHERE ciclo = :ciclo"), {"ciclo": 7}).fetchall()

def orm_contar_por_docente():
    session = Session()
    datos = session.query(Curso).filter(Curso.docente == "Mg. Yanac").all()
    total = len(datos)
    session.close()
    return total

def sql_contar_por_docente():
    with engine.connect() as conn:
        return conn.execute(
            text("SELECT COUNT(*) FROM cursos WHERE docente = :docente"),
            {"docente": "Mg. Yanac"}
        ).scalar()

resultados = []
resultados.append(medir("ORM: cursos ciclo 7", orm_listar_ciclo_7))
resultados.append(medir("SQL: cursos ciclo 7", sql_listar_ciclo_7))
resultados.append(medir("ORM: contar docente", orm_contar_por_docente))
resultados.append(medir("SQL: contar docente", sql_contar_por_docente))

print(tabulate(resultados, headers=["Consulta", "Filas/Resultado", "Tiempo ms"], tablefmt="github"))