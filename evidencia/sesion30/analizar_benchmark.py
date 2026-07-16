import json
import csv
from pathlib import Path
from statistics import mean, stdev

BASE = Path('evidencia/sesion30')

def leer_metricas(carpeta, etiqueta):
    registros = []
    archivos = sorted((BASE / carpeta).glob('run*.json'))
    
    for archivo in archivos:
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        
        metricas = data.get('metrics', {})
        
        duration = metricas.get('http_req_duration', {})
        
        p95 = duration.get('p(95)', 0)
        avg = duration.get('avg', 0)
        
        rps = metricas.get('http_reqs', {}).get('rate', 0)
        error_rate = metricas.get('http_req_failed', {}).get('value', 0)
        
        registros.append({
            'version': etiqueta,
            'run': archivo.stem,   
            'p95_ms': p95,
            'avg_ms': avg,
            'rps': rps,
            'error_rate': error_rate,
        })
    return registros

# Procesar los resultados
resultados = []
resultados += leer_metricas('resultados_baseline', 'baseline')
resultados += leer_metricas('resultados_optimizado', 'optimizado')

if not resultados:
    print("❌ Error: No se encontraron datos en los JSON. Verifica las rutas.")
    exit(1)

# Guardar en un CSV unificado
with (BASE / 'resumen_benchmark.csv').open('w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['version', 'run', 'p95_ms', 'avg_ms', 'rps', 'error_rate'])
    writer.writeheader()
    writer.writerows(resultados)

# Calcular promedios
for version in ['baseline', 'optimizado']:
    subset = [r for r in resultados if r['version'] == version]
    if subset:
        p95 = [r['p95_ms'] for r in subset]
        rps = [r['rps'] for r in subset]
        errores = [r['error_rate'] for r in subset]
        
        print('---')
        print('Version:', version)
        print('p95 promedio:', round(mean(p95), 2), 'ms')
        print('p95 desviacion:', round(stdev(p95), 2) if len(p95) > 1 else 0)
        print('RPS promedio:', round(mean(rps), 2))
        print('Error rate promedio:', round(mean(errores) * 100, 2), '%')

print('---')
baseline_p95_list = [r['p95_ms'] for r in resultados if r['version'] == 'baseline']
opt_p95_list = [r['p95_ms'] for r in resultados if r['version'] == 'optimizado']

if baseline_p95_list and opt_p95_list:
    baseline_p95 = mean(baseline_p95_list)
    opt_p95 = mean(opt_p95_list)
    
    mejora_p95 = ((baseline_p95 - opt_p95) / baseline_p95) * 100
    print('Mejora porcentual p95:', round(mejora_p95, 2), '%')

    if mejora_p95 >= 20:
        print('Decision: la version optimizada mejora significativamente el p95.')
    else:
        print('Decision: la mejora no es suficiente; se requiere mas evidencia o ajustes.')