import csv
import matplotlib.pyplot as plt

versiones = []
p95 = []

with open('evidencia/sesion30/resumen_benchmark.csv', encoding='utf-8') as f:
    reader = csv.DictReader(f)
    for row in reader:
        versiones.append(row['version'] + '-' + row['run'])
        p95.append(float(row['p95_ms']))

colores = ['#e74c3c' if 'baseline' in v else '#2ecc71' for v in versiones]

plt.figure(figsize=(8, 4))
bars = plt.bar(versiones, p95, color=colores)
plt.ylabel('p95 ms')
plt.title('Comparacion de p95 por ejecucion - Baseline vs Optimizado')
plt.xticks(rotation=45)
plt.tight_layout()

for bar, val in zip(bars, p95):
    plt.text(bar.get_x() + bar.get_width()/2, bar.get_height() + 0.1, f'{val:.2f}', ha='center', va='bottom', fontsize=9)

plt.savefig('evidencia/sesion30/grafico_p95.png', dpi=150)
print('Grafico generado: evidencia/sesion30/grafico_p95.png')
