#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import csv
import matplotlib.pyplot as plt
from pathlib import Path

BASE = Path('evidencia/sesion30')
CSV_PATH = BASE / 'resumen_benchmark.csv'
OUTPUT_PATH = BASE / 'grafico_p95.png'

def generar_grafico():
    """Genera un gráfico de barras comparando p95 por ejecución"""
    
    if not CSV_PATH.exists():
        print(f"❌ No se encontró {CSV_PATH}")
        print("   Ejecuta primero analizar_benchmark.py")
        return
    
    versiones = []
    p95_values = []
    colores = []
    
    with CSV_PATH.open(encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            label = f"{row['version']}\n{row['run']}"
            versiones.append(label)
            p95_values.append(float(row['p95_ms']))
            colores.append('blue' if row['version'] == 'baseline' else 'green')
    
    # Crear gráfico
    plt.figure(figsize=(10, 6))
    bars = plt.bar(versiones, p95_values, color=colores, alpha=0.7)
    
    # Personalizar
    plt.ylabel('p95 (ms)', fontsize=12)
    plt.title('Comparación de p95 por ejecución', fontsize=14, fontweight='bold')
    plt.xticks(rotation=45, ha='right')
    plt.grid(axis='y', alpha=0.3)
    
    # Agregar valores en las barras
    for bar, value in zip(bars, p95_values):
        plt.text(bar.get_x() + bar.get_width()/2, bar.get_height() + 5,
                f'{value:.1f}ms', ha='center', va='bottom', fontsize=9)
    
    # Línea de umbral
    plt.axhline(y=500, color='red', linestyle='--', alpha=0.5, label='Umbral p95 < 500ms')
    plt.legend()
    
    plt.tight_layout()
    plt.savefig(OUTPUT_PATH, dpi=150)
    print(f"✅ Gráfico guardado: {OUTPUT_PATH}")
    print("   Abre el archivo para ver la comparación visual")

if __name__ == "__main__":
    generar_grafico()