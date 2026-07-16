import csv
import matplotlib.pyplot as plt

versiones = []
p95 = []

with open('evidencia/sesion30/resumen_benchmark.csv', encoding='utf-8') as f:
    reader = csv.DictReader(f)
    for row in reader:
        versiones.append(row['version'] + '-' + row['run'])
        p95.append(float(row['p95_ms']))

plt.figure(figsize=(8, 4))
plt.bar(versiones, p95, color=['red', 'red', 'red', 'green', 'green', 'green'])
plt.ylabel('p95 ms')
plt.title('Comparación de p95 por ejecución')
plt.xticks(rotation=45)
plt.tight_layout()
plt.savefig('evidencia/sesion30/grafico_p95.png')
print('Gráfico generado: evidencia/sesion30/grafico_p95.png')