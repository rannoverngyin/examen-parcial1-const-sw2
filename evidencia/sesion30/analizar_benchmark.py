import json
import csv
from pathlib import Path
from statistics import mean, stdev

BASE = Path('evidencia/sesion30')

def extraer_metrica(metricas, nombre_metrica, clave_valor):
    met = metricas.get(nombre_metrica, {})
    # k6 suele guardar en metrica -> values -> p(95) o p(99)
    valores = met.get('values', {})
    if isinstance(valores, dict):
        if clave_valor in valores:
            return valores[clave_valor]
        # Por si viene en la estructura alternativa 'percentiles'
        perc = met.get('percentiles', {})
        if clave_valor.replace('p(', '').replace(')', '') in perc:
            return perc[clave_valor.replace('p(', '').replace(')', '')]
    return met.get(clave_valor, 0.0)

def leer_metricas(carpeta, etiqueta):
    registros = []
    for archivo in sorted((BASE / carpeta).glob('run*.json')):
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        metricas = data['metrics']
        
        p95 = extraer_metrica(metricas, 'http_req_duration', 'p(95)')
        p99 = extraer_metrica(metricas, 'http_req_duration', 'p(99)')
        rps = extraer_metrica(metricas, 'http_reqs', 'rate')
        err = extraer_metrica(metricas, 'http_req_failed', 'rate')

        registros.append({
            'version': etiqueta,
            'run': archivo.stem,
            'p95_ms': float(p95 or 0.0),
            'p99_ms': float(p99 or 0.0),
            'rps': float(rps or 0.0),
            'error_rate': float(err or 0.0),
        })
    return registros

resultados = []
resultados += leer_metricas('resultados_baseline', 'baseline')
resultados += leer_metricas('resultados_optimizado', 'optimizado')

# Guardar CSV
with (BASE / 'resumen_benchmark.csv').open('w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['version', 'run', 'p95_ms', 'p99_ms', 'rps', 'error_rate'])
    writer.writeheader()
    writer.writerows(resultados)

# Imprimir en consola
print("\n--- RESUMEN DEL BENCHMARK ---")
for version in ['baseline', 'optimizado']:
    subset = [r for r in resultados if r['version'] == version]
    p95 = [r['p95_ms'] for r in subset]
    p99 = [r['p99_ms'] for r in subset]
    rps = [r['rps'] for r in subset]
    errores = [r['error_rate'] for r in subset]
    print(f"\nVersión: {version.upper()}")
    print(f"  p95 promedio: {round(mean(p95), 2)} ms")
    print(f"  p99 promedio: {round(mean(p99), 2)} ms")
    print(f"  RPS promedio: {round(mean(rps), 2)}")
    print(f"  Error rate promedio: {round(mean(errores) * 100, 2)} %")

baseline_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'baseline'])
opt_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'optimizado'])

mejora_p95 = ((baseline_p95 - opt_p95) / baseline_p95) * 100 if baseline_p95 > 0 else 0
print(f"\nMejora porcentual p95: {round(mejora_p95, 2)} %")

if mejora_p95 >= 20:
    print('Decisión: La versión optimizada mejora significativamente el p95.')
else:
    print('Decisión: La mejora no es suficiente; se requiere más evidencia o ajustes.')