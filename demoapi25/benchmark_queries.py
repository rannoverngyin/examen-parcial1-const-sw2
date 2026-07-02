import time
from tabulate import tabulate
from sqlalchemy import create_engine, text, Column, Integer, String
from sqlalchemy.orm import declarative_base, sessionmaker

# Base para declarar el modelo ORM
Base = declarative_base()

# Modelo ORM que representa la tabla cursos
class Curso(Base):
    __tablename__ = "cursos"

    id = Column(Integer, primary_key=True)
    codigo = Column(String)
    nombre = Column(String)
    ciclo = Column(Integer)
    creditos = Column(Integer)
    docente = Column(String)

# Conexión a la base SQLite creada en el paso 5
engine = create_engine("sqlite:///fiis.db", echo=False)

# Crea sesiones ORM para consultar la base de datos
Session = sessionmaker(bind=engine)

# Función genérica para medir tiempo de ejecución
def medir(nombre, funcion):
    inicio = time.perf_counter()
    resultado = funcion()
    fin = time.perf_counter()

    if hasattr(resultado, "__len__"):
        cantidad = len(resultado)
    else:
        cantidad = resultado

    tiempo_ms = round((fin - inicio) * 1000, 4)

    return [nombre, cantidad, tiempo_ms]

# Consulta ORM: trae todos los cursos del ciclo 7 como objetos Python
def orm_listar_ciclo_7():
    session = Session()
    datos = session.query(Curso).filter(Curso.ciclo == 7).all()
    session.close()
    return datos

# Consulta SQL directa: trae todos los cursos del ciclo 7 como filas
def sql_listar_ciclo_7():
    with engine.connect() as conn:
        return conn.execute(
            text("SELECT * FROM cursos WHERE ciclo = :ciclo"),
            {"ciclo": 7}
        ).fetchall()

# Consulta ORM: trae todos los cursos de un docente y los cuenta en Python
def orm_contar_por_docente():
    session = Session()
    datos = session.query(Curso).filter(Curso.docente == "Mg. Yanac").all()
    total = len(datos)
    session.close()
    return total

# Consulta SQL directa: cuenta directamente en la base de datos
def sql_contar_por_docente():
    with engine.connect() as conn:
        return conn.execute(
            text("SELECT COUNT(*) FROM cursos WHERE docente = :docente"),
            {"docente": "Mg. Yanac"}
        ).scalar()

# Lista de resultados del benchmark
resultados = []

resultados.append(medir("ORM: cursos ciclo 7", orm_listar_ciclo_7))
resultados.append(medir("SQL: cursos ciclo 7", sql_listar_ciclo_7))
resultados.append(medir("ORM: contar docente", orm_contar_por_docente))
resultados.append(medir("SQL: contar docente", sql_contar_por_docente))

# Imprime resultados en formato tabla
print(tabulate(
    resultados,
    headers=["Consulta", "Filas/Resultado", "Tiempo ms"],
    tablefmt="github"
))