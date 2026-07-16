#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import csv
import matplotlib.pyplot as plt
import numpy as np
from pathlib import Path

BASE = Path('evidencia/sesion30')
CSV_PATH = BASE / 'resumen_benchmark.csv'
OUTPUT_PATH = BASE / 'grafico_p95_log.png'

def generar_grafico_log():
    """Genera gráfico con escala logarítmica para mejor visualización"""
    
    if not CSV_PATH.exists():
        print(f"❌ No se encontró {CSV_PATH}")
        return
    
    # Leer datos
    datos = []
    with CSV_PATH.open(encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            datos.append({
                'version': row['version'],
                'run': row['run'],
                'p95': float(row['p95_ms'])
            })
    
    # Preparar datos
    runs = ['Run 1', 'Run 2', 'Run 3']
    baseline = [d['p95'] for d in datos if d['version'] == 'baseline']
    optimizado = [d['p95'] for d in datos if d['version'] == 'optimizado']
    
    fig, ax = plt.subplots(figsize=(10, 6))
    
    # Barras
    x = np.arange(len(runs))
    width = 0.35
    
    bars1 = ax.bar(x - width/2, baseline, width, 
                    label='Baseline', color='#FF6B6B', alpha=0.8)
    bars2 = ax.bar(x + width/2, optimizado, width, 
                    label='Optimizado', color='#4ECDC4', alpha=0.8)
    
    # Escala logarítmica para mejor visualización
    ax.set_yscale('log')
    
    # Personalizar
    ax.set_xlabel('Ejecución', fontsize=12, fontweight='bold')
    ax.set_ylabel('p95 (ms) - Escala Logarítmica', fontsize=12, fontweight='bold')
    ax.set_title('Comparación de p95 (Escala Logarítmica)', fontsize=14, fontweight='bold')
    ax.set_xticks(x)
    ax.set_xticklabels(runs)
    ax.legend(fontsize=11)
    ax.grid(True, alpha=0.3, which='both')
    
    # Agregar valores
    for bar, value in zip(bars1, baseline):
        ax.text(bar.get_x() + bar.get_width()/2, value * 1.1,
                f'{value:.2f}ms', ha='center', va='bottom', fontsize=9)
    
    for bar, value in zip(bars2, optimizado):
        ax.text(bar.get_x() + bar.get_width()/2, value * 1.1,
                f'{value:.2f}ms', ha='center', va='bottom', fontsize=9)
    
    # Línea de referencia
    ax.axhline(y=5, color='red', linestyle='--', alpha=0.5, label='Referencia 5ms')
    ax.legend()
    
    plt.tight_layout()
    plt.savefig(OUTPUT_PATH, dpi=200)
    print(f"✅ Gráfico logarítmico guardado: {OUTPUT_PATH}")

if __name__ == "__main__":
    generar_grafico_log()