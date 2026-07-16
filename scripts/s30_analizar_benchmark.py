import json
import csv
from pathlib import Path
from statistics import mean, stdev

BASE = Path('docs/UNIDAD IV/sesion30')


def leer_metricas(carpeta, etiqueta):
    registros = []
    for archivo in sorted((BASE / carpeta).glob('run*.json')):
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        metricas = data['metrics']
        registros.append({
            'version': etiqueta,
            'run': archivo.stem,
            'p95_ms': metricas['http_req_duration']['p(95)'],
            'p99_ms': metricas['http_req_duration'].get('p(99)', metricas['http_req_duration']['p(95)']),
            'rps': metricas['http_reqs']['rate'],
            'error_rate': metricas['http_req_failed']['value'],
        })
    return registros

resultados = []
resultados += leer_metricas('resultados_baseline', 'baseline')
resultados += leer_metricas('resultados_optimizado', 'optimizado')

with (BASE / 'resumen_benchmark.csv').open('w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['version', 'run', 'p95_ms', 'p99_ms', 'rps', 'error_rate'])
    writer.writeheader()
    writer.writerows(resultados)

for version in ['baseline', 'optimizado']:
    subset = [r for r in resultados if r['version'] == version]
    p95 = [r['p95_ms'] for r in subset]
    rps = [r['rps'] for r in subset]
    errores = [r['error_rate'] for r in subset]
    print('\nVersion:', version)
    print('p95 promedio:', round(mean(p95), 2), 'ms')
    print('p95 desviacion:', round(stdev(p95), 2) if len(p95) > 1 else 0)
    print('RPS promedio:', round(mean(rps), 2))
    print('Error rate promedio:', round(mean(errores) * 100, 2), '%')

baseline_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'baseline'])
opt_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'optimizado'])
mejora_p95 = ((baseline_p95 - opt_p95) / baseline_p95) * 100

print('\nMejora porcentual p95:', round(mejora_p95, 2), '%')

if mejora_p95 >= 20:
    print('Decision: la version optimizada mejora significativamente el p95.')
else:
    print('Decision: la mejora no es suficiente; se requiere mas evidencia o ajustes.')
