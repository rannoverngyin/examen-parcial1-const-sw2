import os
import re
import json
import matplotlib.pyplot as plt
import numpy as np

# Directory paths
root = "C:/Users/ZUZUKA/examen-parcial1-const-sw2"
evidencia_dir = os.path.join(root, "evidencia")
docs_dir = os.path.join(root, "docs/sesion29")

# Set up matplotlib style for sleek dark aesthetics
plt.style.use('dark_background')
plt.rcParams['figure.facecolor'] = '#0f172a'
plt.rcParams['axes.facecolor'] = '#1e293b'
plt.rcParams['grid.color'] = '#334155'
plt.rcParams['axes.edgecolor'] = '#334155'
plt.rcParams['text.color'] = '#e2e8f0'
plt.rcParams['axes.labelcolor'] = '#94a3b8'
plt.rcParams['xtick.color'] = '#94a3b8'
plt.rcParams['ytick.color'] = '#94a3b8'
plt.rcParams['font.sans-serif'] = 'Segoe UI'
plt.rcParams['font.family'] = 'sans-serif'

def parse_nmt(filename):
    filepath = os.path.join(evidencia_dir, filename)
    if not os.path.exists(filepath):
        print(f"Warning: file {filepath} not found.")
        return None
    
    try:
        with open(filepath, 'r', encoding='utf-16') as f:
            content = f.read()
    except Exception:
        with open(filepath, 'r', encoding='utf-8', errors='ignore') as f:
            content = f.read()
        
    metrics = {}
    
    # Parse total committed
    total_match = re.search(r'Total:\s+reserved=\d+KB,\s+committed=(\d+)KB', content)
    if total_match:
        metrics['Total'] = int(total_match.group(1)) / 1024.0 # in MB
    else:
        metrics['Total'] = 0.0
        
    # Parse categories
    categories = {
        'Heap': 'Java Heap',
        'Class': 'Class',
        'Thread': 'Thread',
        'Code': 'Code',
        'GC': 'GC',
        'Metaspace': 'Metaspace'
    }
    
    for label, regex_name in categories.items():
        match = re.search(rf'-\s+{regex_name}\s+\(reserved=\d+KB,\s+committed=(\d+)KB\)', content)
        if match:
            metrics[label] = int(match.group(1)) / 1024.0 # in MB
        else:
            # Fallback try without hyphen prefix
            match = re.search(rf'{regex_name}\s+\(reserved=\d+KB,\s+committed=(\d+)KB\)', content)
            if match:
                metrics[label] = int(match.group(1)) / 1024.0
            else:
                metrics[label] = 0.0
                
    return metrics

def generate_nmt_tendencia_chart(stages_data):
    stages = ['Baseline', 'Steady 10 VUs', 'Peak Load', 'Ramp-Down', 'Post-Test (GC)']
    total_mem = [stages_data[s]['Total'] for s in stages]
    heap_mem = [stages_data[s]['Heap'] for s in stages]
    
    fig, ax = plt.subplots(figsize=(8, 4.5))
    
    # Plot curves with modern glow colors
    ax.plot(stages, total_mem, marker='o', linewidth=2.5, color='#38bdf8', label='Committed Nativo Total')
    ax.plot(stages, heap_mem, marker='s', linewidth=2, linestyle='--', color='#a78bfa', label='Java Heap Committed')
    
    # Fill areas under the curves for premium visual style
    ax.fill_between(stages, total_mem, alpha=0.1, color='#38bdf8')
    ax.fill_between(stages, heap_mem, alpha=0.05, color='#a78bfa')
    
    ax.set_title("Tendencia de Consumo de Memoria JVM (NMT)", fontsize=13, fontweight='bold', color='#38bdf8', pad=15)
    ax.set_ylabel("Memoria Comprometida (MB)", fontsize=10, labelpad=10)
    ax.grid(True, linestyle=':', alpha=0.6)
    ax.legend(loc='center right', framealpha=0.8, facecolor='#1e293b', edgecolor='#334155')
    
    # Annotate points
    for i, txt in enumerate(total_mem):
        ax.annotate(f"{txt:.1f} MB", (stages[i], total_mem[i]), textcoords="offset points", xytext=(0,10), ha='center', fontsize=9, fontweight='bold', color='#38bdf8')
    for i, txt in enumerate(heap_mem):
        ax.annotate(f"{txt:.1f} MB", (stages[i], heap_mem[i]), textcoords="offset points", xytext=(0,-15), ha='center', fontsize=9, color='#c084fc')
        
    plt.tight_layout()
    chart_path = os.path.join(docs_dir, "grafico-nmt-tendencia.png")
    plt.savefig(chart_path, dpi=150, facecolor='#0f172a')
    plt.close()
    print(f"Chart generated: {chart_path}")

def generate_nmt_categories_chart(stages_data):
    categories = ['Class', 'Thread', 'Code', 'GC', 'Metaspace']
    baseline = [stages_data['Baseline'][c] for c in categories]
    peak = [stages_data['Peak Load'][c] for c in categories]
    post_test = [stages_data['Post-Test (GC)'][c] for c in categories]
    
    x = np.arange(len(categories))
    width = 0.25
    
    fig, ax = plt.subplots(figsize=(8, 4.5))
    
    # Render grouped bar charts
    rects1 = ax.bar(x - width, baseline, width, label='Baseline', color='#64748b')
    rects2 = ax.bar(x, peak, width, label='Peak Load (25 VUs)', color='#f43f5e')
    rects3 = ax.bar(x + width, post_test, width, label='Post-Test (GC)', color='#34d399')
    
    ax.set_title("Comparación de Áreas de Memoria Nativa JVM", fontsize=13, fontweight='bold', color='#38bdf8', pad=15)
    ax.set_ylabel("Memoria Comprometida (MB)", fontsize=10, labelpad=10)
    ax.set_xticks(x)
    ax.set_xticklabels(categories)
    ax.grid(True, axis='y', linestyle=':', alpha=0.6)
    ax.legend(framealpha=0.8, facecolor='#1e293b', edgecolor='#334155')
    
    # Annotate heights on the peak bars
    for rect in rects2:
        height = rect.get_height()
        ax.annotate(f"{height:.1f}",
                    xy=(rect.get_x() + rect.get_width() / 2, height),
                    xytext=(0, 3),  # 3 points vertical offset
                    textcoords="offset points",
                    ha='center', va='bottom', fontsize=8, color='#fca5a5')
        
    plt.tight_layout()
    chart_path = os.path.join(docs_dir, "grafico-nmt-categorias.png")
    plt.savefig(chart_path, dpi=150, facecolor='#0f172a')
    plt.close()
    print(f"Chart generated: {chart_path}")

def generate_k6_latencies_chart():
    json_path = os.path.join(evidencia_dir, "resumen-sesion29.json")
    if not os.path.exists(json_path):
        print(f"Error: {json_path} not found.")
        return
        
    with open(json_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
        
    endpoints = ['listado', 'total', 'registro', 'reporte']
    clean_names = {
        'listado': 'GET /productos\n(Listar)',
        'total': 'GET /total\n(Total)',
        'registro': 'POST /productos\n(Registro)',
        'reporte': 'GET /reporte\n(Reporte)'
    }
    
    avg_l = []
    med_l = []
    p95_l = []
    
    for ep in endpoints:
        metric_key = f"http_req_duration{{endpoint:{ep}}}"
        values = data['metrics'].get(metric_key, {}).get('values', {})
        avg_l.append(values.get('avg', 0))
        med_l.append(values.get('med', 0))
        p95_l.append(values.get('p(95)', 0))
        
    x = np.arange(len(endpoints))
    width = 0.25
    
    fig, ax = plt.subplots(figsize=(8, 4.5))
    
    rects1 = ax.bar(x - width, med_l, width, label='Mediana', color='#10b981')
    rects2 = ax.bar(x, avg_l, width, label='Promedio', color='#3b82f6')
    rects3 = ax.bar(x + width, p95_l, width, label='Percentil 95', color='#f59e0b')
    
    ax.set_title("Latencias por Endpoint en k6 (ms)", fontsize=13, fontweight='bold', color='#38bdf8', pad=15)
    ax.set_ylabel("Tiempo de Respuesta (ms)", fontsize=10, labelpad=10)
    ax.set_xticks(x)
    ax.set_xticklabels([clean_names[ep] for ep in endpoints])
    ax.grid(True, axis='y', linestyle=':', alpha=0.6)
    ax.legend(framealpha=0.8, facecolor='#1e293b', edgecolor='#334155')
    
    # Annotate heights
    for rect in rects3:
        height = rect.get_height()
        ax.annotate(f"{height:.1f}ms",
                    xy=(rect.get_x() + rect.get_width() / 2, height),
                    xytext=(0, 3),
                    textcoords="offset points",
                    ha='center', va='bottom', fontsize=8, fontweight='bold', color='#fcd34d')
                    
    plt.tight_layout()
    chart_path = os.path.join(docs_dir, "grafico-k6-latencias.png")
    plt.savefig(chart_path, dpi=150, facecolor='#0f172a')
    plt.close()
    print(f"Chart generated: {chart_path}")

def main():
    stages_data = {}
    
    # Parse NMT files mapping them to stages
    mapping = {
        'Baseline': 'nmt-baseline.txt',
        'Steady 10 VUs': 'nmt-steady-10vus.txt',
        'Peak Load': 'nmt-peak-load.txt',
        'Ramp-Down': 'nmt-ramp-down.txt',
        'Post-Test (GC)': 'nmt-post-test.txt'
    }
    
    for stage_name, file_name in mapping.items():
        metrics = parse_nmt(file_name)
        if metrics:
            stages_data[stage_name] = metrics
        else:
            stages_data[stage_name] = {
                'Total': 0.0, 'Heap': 0.0, 'Class': 0.0, 
                'Thread': 0.0, 'Code': 0.0, 'GC': 0.0, 'Metaspace': 0.0
            }
            
    # Generate charts
    generate_nmt_tendencia_chart(stages_data)
    generate_nmt_categories_chart(stages_data)
    generate_k6_latencies_chart()
    
if __name__ == "__main__":
    main()
