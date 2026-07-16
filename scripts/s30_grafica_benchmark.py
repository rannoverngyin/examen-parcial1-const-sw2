import csv
from pathlib import Path
import matplotlib.pyplot as plt

BASE_DIR = Path(__file__).resolve().parent.parent
CSV_PATH = BASE_DIR / 'docs' / 'UNIDAD IV' / 'sesion30' / 'resumen_benchmark.csv'
IMG_PATH = BASE_DIR / 'docs' / 'UNIDAD IV' / 'sesion30' / 'grafico_p95.png'

versiones = []
p95 = []

with open(CSV_PATH, encoding='utf-8') as f:
    reader = csv.DictReader(f)
    for row in reader:
        versiones.append(row['version'] + '-' + row['run'])
        p95.append(float(row['p95_ms']))

plt.figure(figsize=(8, 4))
plt.bar(versiones, p95)
plt.ylabel('p95 ms')
plt.title('Comparación de p95 por ejecución')
plt.xticks(rotation=45)
plt.tight_layout()
plt.savefig(IMG_PATH)
print(f'Gráfico generado: {IMG_PATH}')
