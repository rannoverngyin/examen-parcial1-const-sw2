#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import csv
import matplotlib.pyplot as plt
import numpy as np
from pathlib import Path

BASE = Path('evidencia/sesion30')
CSV_PATH = BASE / 'resumen_benchmark.csv'
OUTPUT_PATH = BASE / 'grafico_p95_lineas.png'

def generar_grafico_lineas():
    """Genera gráfico de líneas mostrando la tendencia"""
    
    if not CSV_PATH.exists():
        print(f"❌ No se encontró {CSV_PATH}")
        return
    
    # Leer datos
    baseline = []
    optimizado = []
    
    with CSV_PATH.open(encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            if row['version'] == 'baseline':
                baseline.append(float(row['p95_ms']))
            else:
                optimizado.append(float(row['p95_ms']))
    
    runs = ['Run 1', 'Run 2', 'Run 3']
    
    fig, ax = plt.subplots(figsize=(10, 6))
    
    # Líneas
    ax.plot(runs, baseline, 'o-', color='#FF6B6B', linewidth=2, 
            markersize=10, label='Baseline')
    ax.plot(runs, optimizado, 's-', color='#4ECDC4', linewidth=2, 
            markersize=10, label='Optimizado')
    
    # Área sombreada para la mejora
    ax.fill_between(runs, baseline, optimizado, alpha=0.2, color='#4ECDC4')
    
    # Personalizar
    ax.set_xlabel('Ejecución', fontsize=12, fontweight='bold')
    ax.set_ylabel('p95 (ms)', fontsize=12, fontweight='bold')
    ax.set_title('Tendencia de p95 por ejecución', fontsize=14, fontweight='bold')
    ax.legend(fontsize=11)
    ax.grid(True, alpha=0.3)
    
    # Agregar valores
    for i, (b, o) in enumerate(zip(baseline, optimizado)):
        ax.text(i, b + 0.1, f'{b:.2f}ms', ha='center', va='bottom', fontsize=9)
        ax.text(i, o - 0.1, f'{o:.2f}ms', ha='center', va='top', fontsize=9)
    
    # Línea de umbral
    ax.axhline(y=5, color='red', linestyle='--', alpha=0.5, label='Umbral 5ms')
    ax.legend()
    
    # Ajustar límites
    max_val = max(max(baseline), max(optimizado))
    ax.set_ylim(0, max_val * 1.3)
    
    plt.tight_layout()
    plt.savefig(OUTPUT_PATH, dpi=200)
    print(f"✅ Gráfico de líneas guardado: {OUTPUT_PATH}")

if __name__ == "__main__":
    generar_grafico_lineas()