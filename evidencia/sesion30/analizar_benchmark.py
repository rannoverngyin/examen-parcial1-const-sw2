import json
import csv
from pathlib import Path
from statistics import mean, stdev

BASE = Path('evidencia/sesion30')

def leer_metricas(carpeta, etiqueta):
    registros = []
    for archivo in sorted((BASE / carpeta).glob('run*.json')):
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        metricas = data['metrics']
        dur = metricas['http_req_duration']
        reqs = metricas['http_reqs']
        failed = metricas['http_req_failed']
        
        p95 = dur.get('p(95)', dur.get('percentiles', {}).get('95', 0))
        p99 = dur.get('p(99)', dur.get('percentiles', {}).get('99', 0))
        rps = reqs.get('rate', reqs.get('values', {}).get('rate', 0))
        err = failed.get('rate', failed.get('values', {}).get('rate', 0))
        
        registros.append({
            'version': etiqueta,
            'run': archivo.stem,
            'p95_ms': p95,
            'p99_ms': p99,
            'rps': rps,
            'error_rate': err,
        })
    return registros

resultados = []
resultados += leer_metricas('resultados_baseline', 'baseline')
resultados += leer_metricas('resultados_optimizado', 'optimizado')

if not resultados:
    print("No se encontraron archivos run*.json en resultados_baseline/ o resultados_optimizado/.")
    exit(0)

with (BASE / 'resumen_benchmark.csv').open('w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['version', 'run', 'p95_ms', 'p99_ms', 'rps', 'error_rate'])
    writer.writeheader()
    writer.writerows(resultados)

for version in ['baseline', 'optimizado']:
    subset = [r for r in resultados if r['version'] == version]
    if not subset:
        continue
    p95 = [r['p95_ms'] for r in subset]
    rps = [r['rps'] for r in subset]
    errores = [r['error_rate'] for r in subset]
    print('\nVersion:', version)
    print('p95 promedio:', round(mean(p95), 2), 'ms')
    print('p95 desviacion:', round(stdev(p95), 2) if len(p95) > 1 else 0)
    print('RPS promedio:', round(mean(rps), 2))
    print('Error rate promedio:', round(mean(errores) * 100, 2), '%')

baseline_subset = [r['p95_ms'] for r in resultados if r['version'] == 'baseline']
opt_subset = [r['p95_ms'] for r in resultados if r['version'] == 'optimizado']

if baseline_subset and opt_subset:
    baseline_p95 = mean(baseline_subset)
    opt_p95 = mean(opt_subset)
    mejora_p95 = ((baseline_p95 - opt_p95) / baseline_p95) * 100

    print('\nMejora porcentual p95:', round(mejora_p95, 2), '%')

    if mejora_p95 >= 20:
        print('Decision: la version optimizada mejora significativamente el p95.')
    else:
        print('Decision: la mejora no es suficiente; se requiere mas evidencia o ajustes.')

# Sección 13 (Reto opcional: gráfico comparativo)
try:
    import matplotlib.pyplot as plt
    versiones = []
    p95_vals = []
    with (BASE / 'resumen_benchmark.csv').open(encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            versiones.append(row['version'] + '-' + row['run'])
            p95_vals.append(float(row['p95_ms']))

    if versiones:
        plt.figure(figsize=(8, 4))
        plt.bar(versiones, p95_vals, color=['#e74c3c' if 'baseline' in v else '#2ecc71' for v in versiones])
        plt.ylabel('p95 ms')
        plt.title('Comparación de p95 por ejecución')
        plt.xticks(rotation=45)
        plt.tight_layout()
        plt.savefig(BASE / 'grafico_p95.png')
        print('\nGráfico generado: evidencia/sesion30/grafico_p95.png')
except ImportError:
    print('\nNota: matplotlib no está instalado, se omitió la generación del gráfico.')
