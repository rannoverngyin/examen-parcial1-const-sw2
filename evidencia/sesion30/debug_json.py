import json
from pathlib import Path

BASE = Path('evidencia/sesion30')

def debug_json(archivo):
    """Muestra la estructura del JSON para debug"""
    try:
        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)
        
        print(f"\n📄 {archivo.name}")
        print("-" * 40)
        print(f"Keys principales: {list(data.keys())}")
        
        if 'metrics' in data:
            print(f"Keys en metrics: {list(data['metrics'].keys())}")
            
            if 'http_req_duration' in data['metrics']:
                duration = data['metrics']['http_req_duration']
                print(f"Keys en http_req_duration: {list(duration.keys())}")
                
                if 'values' in duration:
                    print(f"Valores: {duration['values']}")
                elif 'percentiles' in duration:
                    print(f"Percentiles: {duration['percentiles']}")
        
        print("-" * 40)
        
    except Exception as e:
        print(f"❌ Error: {e}")

# Analizar todos los JSON
baseline_files = list((BASE / 'resultados_baseline').glob('*.json'))
optimizado_files = list((BASE / 'resultados_optimizado').glob('*.json'))

print("=" * 60)
print("   DEBUG DE ARCHIVOS JSON")
print("=" * 60)

for archivo in baseline_files:
    debug_json(archivo)

for archivo in optimizado_files:
    debug_json(archivo)