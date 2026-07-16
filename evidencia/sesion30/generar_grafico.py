import csv
import matplotlib.pyplot as plt

versiones = []
p95 = []

# Leer datos desde el CSV generado previamente
with open('evidencia/sesion30/resumen_benchmark.csv', encoding='utf-8') as f:
    reader = csv.DictReader(f)
    for row in reader:
        versiones.append(row['version'] + '-' + row['run'])
        p95.append(float(row['p95_ms']))

# Configurar dimensiones y estilo del gráfico
plt.figure(figsize=(8, 4))
plt.bar(versiones, p95, color=['#1f77b4', '#1f77b4', '#1f77b4', '#ff7f0e', '#ff7f0e', '#ff7f0e'])

# Etiquetas y títulos
plt.ylabel('p95 (ms)')
plt.title('Comparación de Latencia p95 por Ejecución')
plt.xticks(rotation=45)
plt.tight_layout()

# Guardar la imagen generada
plt.savefig('evidencia/sesion30/grafico_p95.png')
print('Gráfico generado exitosamente en: evidencia/sesion30/grafico_p95.png')