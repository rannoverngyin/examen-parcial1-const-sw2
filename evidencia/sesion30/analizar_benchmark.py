#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import json
import csv
from pathlib import Path
from statistics import mean, stdev, median
import sys

BASE = Path('evidencia/sesion30')

def leer_metricas(carpeta, etiqueta):
    """Lee los archivos JSON generados por k6 y extrae métricas"""
    registros = []
    carpeta_path = BASE / carpeta
    
    if not carpeta_path.exists():
        print(f"⚠️ Carpeta no encontrada: {carpeta_path}")
        return registros
    
    archivos = sorted(carpeta_path.glob('run*.json'))
    
    if not archivos:
        print(f"⚠️ No se encontraron archivos JSON en {carpeta_path}")
        return registros
    
    for archivo in archivos:
        try:
            with archivo.open(encoding='utf-8') as f:
                data = json.load(f)
            
            if 'metrics' not in data:
                print(f"⚠️ El archivo {archivo.name} no tiene 'metrics'")
                continue
            
            metricas = data['metrics']
            
            if 'http_req_duration' not in metricas:
                print(f"⚠️ El archivo {archivo.name} no tiene 'http_req_duration'")
                continue
            
            # Extraer métricas directamente de http_req_duration
            duration = metricas['http_req_duration']
            
            # Los valores están directamente en duration
            p95 = duration.get('p(95)', 0)
            p90 = duration.get('p(90)', 0)
            avg = duration.get('avg', 0)
            median = duration.get('med', 0)
            min_val = duration.get('min', 0)
            max_val = duration.get('max', 0)
            
            # Extraer RPS
            if 'http_reqs' in metricas:
                rps = metricas['http_reqs'].get('rate', 0)
            else:
                rps = 0
            
            # Extraer error rate
            if 'http_req_failed' in metricas:
                error_rate = metricas['http_req_failed'].get('rate', 0)
            else:
                error_rate = 0
            
            registros.append({
                'version': etiqueta,
                'run': archivo.stem,
                'p95_ms': p95,
                'p90_ms': p90,
                'avg_ms': avg,
                'median_ms': median,
                'min_ms': min_val,
                'max_ms': max_val,
                'rps': rps,
                'error_rate': error_rate,
            })
            
            print(f"✅ Leído: {archivo.name} - p95: {p95:.2f}ms, RPS: {rps:.2f}")
            
        except Exception as e:
            print(f"❌ Error leyendo {archivo}: {e}")
    
    return registros

def calcular_estadisticas(resultados, version):
    """Calcula estadísticas para una versión específica"""
    subset = [r for r in resultados if r['version'] == version]
    
    if not subset:
        return None
    
    p95 = [r['p95_ms'] for r in subset if r['p95_ms'] > 0]
    rps = [r['rps'] for r in subset if r['rps'] > 0]
    errores = [r['error_rate'] for r in subset]
    
    if not p95:
        return None
    
    return {
        'version': version,
        'count': len(subset),
        'p95_promedio': mean(p95),
        'p95_mediana': median(p95),
        'p95_desviacion': stdev(p95) if len(p95) > 1 else 0,
        'p95_min': min(p95),
        'p95_max': max(p95),
        'rps_promedio': mean(rps) if rps else 0,
        'error_rate_promedio': mean(errores) * 100 if errores else 0,
    }

def main():
    print("=" * 60)
    print("   ANÁLISIS DE BENCHMARK - SESIÓN 30")
    print("=" * 60)
    print()
    
    # Verificar que existen los directorios
    baseline_dir = BASE / 'resultados_baseline'
    optimizado_dir = BASE / 'resultados_optimizado'
    
    if not baseline_dir.exists():
        print(f"❌ Directorio no encontrado: {baseline_dir}")
        print("   Ejecuta primero el benchmark con k6")
        sys.exit(1)
    
    if not optimizado_dir.exists():
        print(f"❌ Directorio no encontrado: {optimizado_dir}")
        print("   Ejecuta primero el benchmark con k6")
        sys.exit(1)
    
    # Leer todas las métricas
    print("📊 Leyendo métricas...")
    print()
    
    resultados = []
    resultados += leer_metricas('resultados_baseline', 'baseline')
    resultados += leer_metricas('resultados_optimizado', 'optimizado')
    
    if not resultados:
        print("❌ No se encontraron resultados válidos.")
        print("   Verifica que los archivos JSON tengan el formato correcto.")
        sys.exit(1)
    
    # Guardar CSV con todos los resultados
    csv_path = BASE / 'resumen_benchmark.csv'
    with csv_path.open('w', newline='', encoding='utf-8') as f:
        fieldnames = ['version', 'run', 'p95_ms', 'p90_ms', 'avg_ms', 'median_ms', 
                     'min_ms', 'max_ms', 'rps', 'error_rate']
        writer = csv.DictWriter(f, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(resultados)
    
    print()
    print(f"✅ Resultados guardados en: {csv_path}")
    print()
    
    # Analizar cada versión
    print("📈 ESTADÍSTICAS POR VERSIÓN:")
    print("-" * 60)
    
    stats_baseline = calcular_estadisticas(resultados, 'baseline')
    stats_optimizado = calcular_estadisticas(resultados, 'optimizado')
    
    if stats_baseline:
        print(f"\n🔵 BASELINE ({stats_baseline['count']} ejecuciones):")
        print(f"  p95 promedio: {stats_baseline['p95_promedio']:.2f} ms")
        print(f"  p95 desviación: {stats_baseline['p95_desviacion']:.2f} ms")
        print(f"  p95 min: {stats_baseline['p95_min']:.2f} ms")
        print(f"  p95 max: {stats_baseline['p95_max']:.2f} ms")
        print(f"  RPS promedio: {stats_baseline['rps_promedio']:.2f} req/s")
        print(f"  Error rate: {stats_baseline['error_rate_promedio']:.2f}%")
    
    if stats_optimizado:
        print(f"\n🟢 OPTIMIZADO ({stats_optimizado['count']} ejecuciones):")
        print(f"  p95 promedio: {stats_optimizado['p95_promedio']:.2f} ms")
        print(f"  p95 desviación: {stats_optimizado['p95_desviacion']:.2f} ms")
        print(f"  p95 min: {stats_optimizado['p95_min']:.2f} ms")
        print(f"  p95 max: {stats_optimizado['p95_max']:.2f} ms")
        print(f"  RPS promedio: {stats_optimizado['rps_promedio']:.2f} req/s")
        print(f"  Error rate: {stats_optimizado['error_rate_promedio']:.2f}%")
    
    # Calcular mejora
    if stats_baseline and stats_optimizado:
        print("\n" + "=" * 60)
        print("📊 COMPARATIVA Y MEJORA:")
        print("-" * 60)
        
        mejora_p95 = ((stats_baseline['p95_promedio'] - stats_optimizado['p95_promedio']) / stats_baseline['p95_promedio']) * 100
        mejora_rps = ((stats_optimizado['rps_promedio'] - stats_baseline['rps_promedio']) / stats_baseline['rps_promedio']) * 100 if stats_baseline['rps_promedio'] > 0 else 0
        cambio_errores = stats_optimizado['error_rate_promedio'] - stats_baseline['error_rate_promedio']
        
        print(f"📉 Mejora p95: {mejora_p95:.2f}%")
        print(f"📈 Mejora RPS: {mejora_rps:.2f}%")
        print(f"⚠️  Cambio en errores: {cambio_errores:+.2f}%")
        
        # Matriz de decisión
        print("\n" + "=" * 60)
        print("🎯 DECISIÓN TÉCNICA:")
        print("-" * 60)
        
        # Verificar si hay datos reales
        if stats_baseline['p95_promedio'] == 0 or stats_optimizado['p95_promedio'] == 0:
            print("⚠️  ADVERTENCIA: Los valores de p95 son 0. Verifica que el benchmark se ejecutó correctamente.")
            print("   Puede que los archivos JSON no contengan datos de rendimiento reales.")
        
        elif mejora_p95 >= 20 and stats_optimizado['error_rate_promedio'] < 1:
            print("✅ RECOMENDACIÓN: Aceptar optimización")
            print("   La mejora en p95 es significativa (≥20%) y los errores se mantienen bajos")
        elif mejora_p95 >= 20 and stats_optimizado['error_rate_promedio'] >= 1:
            print("⚠️  RECOMENDACIÓN: No aceptar todavía")
            print("   La mejora en p95 viene acompañada de alta tasa de errores")
        elif stats_optimizado['rps_promedio'] > stats_baseline['rps_promedio'] and mejora_p95 > 0:
            print("✅ RECOMENDACIÓN: Aceptar optimización")
            print("   Mejora tanto en latencia como en throughput")
        elif mejora_p95 < 10 and mejora_p95 > 0:
            print("⚠️  RECOMENDACIÓN: Revisar hipótesis")
            print("   La mejora no es significativa, el cambio no atacó el cuello de botella")
        elif mejora_p95 <= 0:
            print("❌ RECOMENDACIÓN: Rechazar optimización")
            print("   La versión optimizada es más lenta que la baseline")
        else:
            print("📊 RECOMENDACIÓN: Repetir benchmark")
            print("   Los resultados no son concluyentes, se necesitan más datos")
    
    print("\n" + "=" * 60)
    print("✅ Análisis completado")
    print(f"📁 Archivo CSV: {csv_path}")
    print("\n📋 Contenido del CSV:")
    with csv_path.open('r', encoding='utf-8') as f:
        print(f.read())

if __name__ == "__main__":
    main()