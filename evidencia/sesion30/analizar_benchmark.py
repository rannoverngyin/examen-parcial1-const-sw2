import json
import csv
from pathlib import Path
from statistics import mean, stdev

# BASE apunta automáticamente al directorio donde se aloja este script
BASE = Path(__file__).resolve().parent

def leer_metricas(carpeta, etiqueta):
    registros = []
    # Buscamos la carpeta de forma relativa al script
    ruta_carpeta = BASE / carpeta
    for archivo in sorted(ruta_carpeta.glob('run*.json')):
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        metricas = data['metrics']
        
        duracion = metricas.get('http_req_duration', {})
        reqs = metricas.get('http_reqs', {})
        failed = metricas.get('http_req_failed', {})
        
        p95 = duracion.get('p(95)', duracion.get('p95', duracion.get('avg', 0)))
        p99 = duracion.get('p(99)', duracion.get('p99', duracion.get('max', 0)))
        
        registros.append({
            'version': etiqueta,
            'run': archivo.stem,
            'p95_ms': p95,
            'p99_ms': p99,
            'rps': reqs.get('rate', 0),
            'error_rate': failed.get('rate', 0),
        })
    return registros

# Leer y consolidar resultados
resultados = []
resultados += leer_metricas('resultados_baseline', 'baseline')
resultados += leer_metricas('resultados_optimizado', 'optimizado')

# Guardar resumen en CSV
with (BASE / 'resumen_benchmark.csv').open('w', newline='', encoding='utf-8') as f:
    writer = csv.DictWriter(f, fieldnames=['version', 'run', 'p95_ms', 'p99_ms', 'rps', 'error_rate'])
    writer.writeheader()
    writer.writerows(resultados)

# Calcular y mostrar métricas de resumen en consola
for version in ['baseline', 'optimizado']:
    subset = [r for r in resultados if r['version'] == version]
    p95 = [r['p95_ms'] for r in subset]
    rps = [r['rps'] for r in subset]
    errores = [r['error_rate'] for r in subset]
    print(f'\nVersion: {version}')
    print(f'p95 promedio: {round(mean(p95), 2)} ms')
    print(f'p95 desviacion: {round(stdev(p95), 2) if len(p95) > 1 else 0}')
    print(f'RPS promedio: {round(mean(rps), 2)}')
    print(f'Error rate promedio: {round(mean(errores) * 100, 2)} %')

# Evaluar mejora porcentual
baseline_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'baseline'])
opt_p95 = mean([r['p95_ms'] for r in resultados if r['version'] == 'optimizado'])
mejora_p95 = ((baseline_p95 - opt_p95) / baseline_p95) * 100

print(f'\nMejora porcentual p95: {round(mejora_p95, 2)} %')

if mejora_p95 >= 20:
    print('Decision: la version optimizada mejora significativamente el p95.')
else:
    print('Decision: la mejora no es suficiente; se requiere mas evidencia o ajustes.')

# Generación del gráfico comparativo (Reto opcional)
try:
    import matplotlib.pyplot as plt
    
    versiones = [f"{r['version']}-{r['run']}" for r in resultados]
    p95_valores = [r['p95_ms'] for r in resultados]
    
    plt.figure(figsize=(8, 4))
    colores = ['#e74c3c' if 'baseline' in v else '#2ecc71' for v in versiones]
    
    plt.bar(versiones, p95_valores, color=colores)
    plt.ylabel('p95 ms')
    plt.title('Comparación de p95 por ejecución')
    plt.xticks(rotation=45)
    plt.tight_layout()
    
    grafico_path = BASE / 'grafico_p95.png'
    plt.savefig(grafico_path)
    print(f'\nGráfico generado exitosamente en: {grafico_path}')
except ImportError:
    print('\n[Nota] Instala matplotlib ("pip install matplotlib") si deseas generar el gráfico comparativo automáticamente.')