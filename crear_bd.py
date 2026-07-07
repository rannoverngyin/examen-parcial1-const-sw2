import sqlite3
import random
from pathlib import Path

DB = Path("universidad.db")

# Eliminar base de datos existente si existe
if DB.exists():
    DB.unlink()

# Conectar a la base de datos
conn = sqlite3.connect(DB)
cur = conn.cursor()

# Crear tablas
cur.execute("""
CREATE TABLE estudiantes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo TEXT NOT NULL,
    nombre TEXT NOT NULL,
    escuela TEXT NOT NULL
)
""")

cur.execute("""
CREATE TABLE cursos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    ciclo INTEGER NOT NULL
)
""")

cur.execute("""
CREATE TABLE matriculas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    estudiante_id INTEGER NOT NULL,
    curso_id INTEGER NOT NULL,
    semestre TEXT NOT NULL,
    nota REAL NOT NULL,
    FOREIGN KEY(estudiante_id) REFERENCES estudiantes(id),
    FOREIGN KEY(curso_id) REFERENCES cursos(id)
)
""")

print("Tablas creadas correctamente")

# Datos de prueba
escuelas = ["FIIS", "Agronomia", "Zootecnia", "Ambiental"]
cursos = [
    ("Construccion de Software II", 7),
    ("Arquitectura de Software", 6),
    ("Base de Datos", 4),
    ("Inteligencia Artificial", 8)
]

# Insertar 5000 estudiantes
print("Insertando 5000 estudiantes...")
for i in range(1, 5001):
    cur.execute(
        "INSERT INTO estudiantes(codigo, nombre, escuela) VALUES (?,?,?)",
        (f"2026{i:05d}", f"Estudiante {i}", random.choice(escuelas))
    )

# Insertar 4 cursos
print("Insertando cursos...")
for nombre, ciclo in cursos:
    cur.execute("INSERT INTO cursos(nombre, ciclo) VALUES (?,?)", (nombre, ciclo))

# Insertar 80,000 matrículas
print("Insertando 80,000 matrículas...")
semestres = ["2025-I", "2025-II", "2026-I"]
for _ in range(80000):
    cur.execute(
        "INSERT INTO matriculas(estudiante_id, curso_id, semestre, nota) VALUES (?,?,?,?)",
        (random.randint(1, 5000), random.randint(1, 4), random.choice(semestres), round(random.uniform(0, 20), 2))
    )

conn.commit()
conn.close()

print(f"Base de datos {DB} creada correctamente")
print(f"Estudiantes: 5000, Cursos: 4, Matrículas: 80000")