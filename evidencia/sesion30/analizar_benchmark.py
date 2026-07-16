import json
import csv
from pathlib import Path
from statistics import mean, stdev

BASE = Path('evidencia/sesion30')


def obtener(metrica, *claves):
    fuente = metrica.get('values', metrica)
    for clave in claves:
        if clave in fuente:
            return fuente[clave]
    return None


def calcular_error_rate(http_failed):
    passes = http_failed.get('passes', 0)
    fails = http_failed.get('fails', 0)
    total = passes + fails
    if total == 0:
        return 0
    return fails / total


def leer_metricas(carpeta, etiqueta):
    registros = []
    for archivo in sorted((BASE / carpeta).glob('run*.json')):
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        metricas = data['metrics']
        http_dur = metricas['http_req_duration']
        http_reqs = metricas['http_reqs']
        http_failed = metricas['http_req_failed']

        registros.append({
            'version': etiqueta,
            'run': archivo.stem,
            'p95_ms': obtener(http_dur, 'p(95)'),
            'p99_ms': obtener(http_dur, 'p(99)'),
            'rps': obtener(http_reqs, 'rate', 'value'),
            'error_rate': calcular_error_rate(http_failed),
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
    print('Version:', version)
    print('p95 promedio:', round(mean(p95), 2), 'ms')
    print('p95 desviacion:', round(stdev(p95), 2) if len(p95) > 1 else 0)
    print('RPS promedio:', round(mean(rps), 2))
    print('Error rate promedio:', round(mean(errores) * 100, 2), '%')

baseline_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'baseline'])
opt_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'optimizado'])
mejora_p95 = ((baseline_p95 - opt_p95) / baseline_p95) * 100

print('Mejora porcentual p95:', round(mejora_p95, 2), '%')

if mejora_p95 >= 20:
    print('Decision: la version optimizada mejora significativamente el p95.')
else:
    print('Decision: la mejora no es suficiente; se requiere mas evidencia o ajustes.')