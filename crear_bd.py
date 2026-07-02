import sqlite3
import random
from pathlib import Path

# Configuración y limpieza de la base de datos local
DB = Path("universidad.db")
if DB.exists():
    DB.unlink()

conn = sqlite3.connect(DB)
cur = conn.cursor()

# Creación del esquema relacional (Tablas base y transaccional)
cur.execute("""CREATE TABLE estudiantes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo TEXT NOT NULL,
    nombre TEXT NOT NULL,
    escuela TEXT NOT NULL
)""")

cur.execute("""CREATE TABLE cursos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    ciclo INTEGER NOT NULL
)""")

# Tabla intermedia con restricciones de llave foránea (Foreign Keys)
cur.execute("""CREATE TABLE matriculas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    estudiante_id INTEGER NOT NULL,
    curso_id INTEGER NOT NULL,
    semestre TEXT NOT NULL,
    nota REAL NOT NULL,
    FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY(curso_id) REFERENCES cursos(id)
)""")

# Semillas de datos para la generación aleatoria
escuelas = ["FIIS", "Agronomia", "Zootecnia", "Ambiental"]
cursos = [
    ("Construccion de Software II", 7),
    ("Arquitectura de Software", 6),
    ("Base de Datos", 4),
    ("Inteligencia Artificial", 8)
]

# Inserción masiva de 5,000 registros de estudiantes
for i in range(1, 5001):
    cur.execute(
        "INSERT INTO estudiantes(codigo,nombre,escuela) VALUES (?,?,?)",
        (f"2026{i:05d}", f"Estudiante {i}", random.choice(escuelas))
    )

# Poblar catálogo de cursos
for nombre, ciclo in cursos:
    cur.execute("INSERT INTO cursos(nombre,ciclo) VALUES (?,?)", (nombre, ciclo))

semestres = ["2025-I", "2025-II", "2026-I"]

# Generación masiva de 80,000 registros en la tabla transaccional (Matrículas)
for _ in range(80000):
    cur.execute(
        "INSERT INTO matriculas(estudiante_id, curso_id, semestre, nota) VALUES (?,?,?,?)",
        (random.randint(1, 5000), random.randint(1, 4), random.choice(semestres), round(random.uniform(0, 20), 2))
    )

# Confirmación de transacciones y cierre de conexión
conn.commit()
conn.close()
print("Base de datos universidad.db creada correctamente con 80,000 matriculas.")