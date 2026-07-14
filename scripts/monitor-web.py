import http.server
import json
import os
import time
import threading
import urllib.request
from datetime import datetime
from pathlib import Path

PORT = 9090
K6_URL = "http://localhost:8080"
SPRING_URL = "http://localhost:8080"
METRICS_FILE = Path(__file__).parent / "metrics-live.json"

metrics_history = []
lock = threading.Lock()
k6_status = "unknown"
k6_output = ""


def find_java_pid():
    try:
        import subprocess
        result = subprocess.run(
            ["powershell", "-Command",
             "(Get-NetTCPConnection -LocalPort 8080 -State Listen -ErrorAction SilentlyContinue).OwningProcess"],
            capture_output=True, text=True, timeout=5
        )
        pid = result.stdout.strip()
        if not pid:
            result = subprocess.run(
                ["powershell", "-Command",
                 "(Get-Process java -ErrorAction SilentlyContinue | Where-Object {$_.WorkingSet64 -gt 30MB}).Id | Select-Object -First 1"],
                capture_output=True, text=True, timeout=5
            )
            pid = result.stdout.strip()
        return int(pid) if pid else None
    except Exception:
        return None


def get_java_metrics(pid):
    try:
        import subprocess
        cmd = (
            f"$p = Get-Process -Id {pid} -ErrorAction SilentlyContinue; "
            f"if ($p) {{ "
            f"  $memMB = [math]::Round($p.WorkingSet64/1MB, 2); "
            f"  $cpuSec = [math]::Round($p.CPU, 2); "
            f"  Write-Output \"$memMB|$cpuSec|$($p.Threads.Count)\""
            f"}} else {{ Write-Output 'dead|0|0' }}"
        )
        result = subprocess.run(
            ["powershell", "-Command", cmd],
            capture_output=True, text=True, timeout=5
        )
        parts = result.stdout.strip().split("|")
        return {
            "memMB": float(parts[0]) if parts[0] != "dead" else 0,
            "cpuSec": float(parts[1]) if len(parts) > 1 else 0,
            "threads": int(parts[2]) if len(parts) > 2 else 0,
        }
    except Exception:
        return {"memMB": 0, "cpuSec": 0, "threads": 0}


def check_endpoint(url, timeout=3):
    try:
        start = time.time()
        req = urllib.request.Request(url, method="GET")
        with urllib.request.urlopen(req, timeout=timeout) as resp:
            latency = (time.time() - start) * 1000
            return {"status": resp.status, "latencyMs": round(latency, 2)}
    except Exception as e:
        return {"status": 0, "latencyMs": 0, "error": str(e)[:80]}


def collect_metrics():
    global k6_status, k6_output
    pid = find_java_pid()
    start_time = time.time()
    prev_cpu = 0
    prev_time = start_time
    
    import os
    num_cores = os.cpu_count() or 1

    while True:
        now = time.time()
        time_delta = now - prev_time
        if time_delta <= 0:
            time_delta = 2.0
        prev_time = now
        elapsed = round(now - start_time, 1)

        java = get_java_metrics(pid) if pid else {"memMB": 0, "cpuSec": 0, "threads": 0}
        cpu_delta = round(java["cpuSec"] - prev_cpu, 2)
        if cpu_delta < 0:
            cpu_delta = 0.0
        prev_cpu = java["cpuSec"]
        
        # Calculate CPU usage percentage (relative to 1 core)
        if elapsed < 1.5:
            cpu_percent = 0.0
        else:
            cpu_percent = round((cpu_delta / time_delta) * 100, 2)

        api = check_endpoint(f"{SPRING_URL}/carga/productos/total")

        # Detect k6 state
        if elapsed > 5 and api["status"] == 200:
            k6_status = "running"
        elif elapsed < 5:
            k6_status = "starting"
        else:
            k6_status = "unknown"

        point = {
            "timestamp": datetime.now().strftime("%H:%M:%S"),
            "elapsed": elapsed,
            "memMB": java["memMB"],
            "cpuSec": java["cpuSec"],
            "cpuDelta": cpu_delta,
            "cpuPercent": cpu_percent,
            "threads": java["threads"],
            "apiStatus": api["status"],
            "apiLatencyMs": api["latencyMs"],
        }

        with lock:
            metrics_history.append(point)
            if len(metrics_history) > 600:
                metrics_history.pop(0)
            METRICS_FILE.write_text(json.dumps(metrics_history, indent=2))

        time.sleep(2)


DASHBOARD_HTML = r"""<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Monitor Sesion 29 - k6 + Spring Boot</title>
<style>
  * { box-sizing: border-box; margin: 0; padding: 0; }
  body { font-family: 'Segoe UI', Arial, sans-serif; background: #0f172a; color: #e2e8f0; }
  .header { background: linear-gradient(135deg, #1e293b 0%, #0f172a 100%); padding: 20px 30px; border-bottom: 1px solid #334155; display: flex; justify-content: space-between; align-items: center; }
  .header h1 { font-size: 1.4em; color: #38bdf8; }
  .header .status { padding: 6px 16px; border-radius: 20px; font-weight: bold; font-size: 0.85em; }
  .status-running { background: #065f46; color: #34d399; }
  .status-starting { background: #713f12; color: #fbbf24; }
  .status-unknown { background: #7f1d1d; color: #fca5a5; }
  .grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; padding: 20px 30px; }
  .card { background: #1e293b; border-radius: 12px; padding: 18px; border: 1px solid #334155; }
  .card .label { font-size: 0.75em; color: #94a3b8; text-transform: uppercase; letter-spacing: 1px; }
  .card .value { font-size: 2em; font-weight: bold; margin-top: 4px; }
  .card .unit { font-size: 0.5em; color: #64748b; }
  .charts { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; padding: 0 30px 20px; }
  .chart-box { background: #1e293b; border-radius: 12px; padding: 18px; border: 1px solid #334155; }
  .chart-box h3 { font-size: 0.9em; color: #94a3b8; margin-bottom: 12px; }
  canvas { width: 100% !important; height: 200px !important; }
  .log-box { background: #1e293b; border-radius: 12px; padding: 18px; border: 1px solid #334155; margin: 0 30px 30px; }
  .log-box h3 { font-size: 0.9em; color: #94a3b8; margin-bottom: 10px; }
  .log { font-family: 'Cascadia Code', 'Consolas', monospace; font-size: 0.8em; max-height: 200px; overflow-y: auto; background: #0f172a; padding: 12px; border-radius: 8px; line-height: 1.6; }
  .log .ts { color: #64748b; }
  .log .ok { color: #34d399; }
  .log .warn { color: #fbbf24; }
  .log .err { color: #f87171; }
</style>
</head>
<body>

<div class="header">
  <h1>Monitor de Rendimiento - Sesion 29</h1>
  <span id="statusBadge" class="status status-unknown">INICIANDO</span>
</div>

<div class="grid">
  <div class="card">
    <div class="label">Memoria Java</div>
    <div class="value" id="memVal">--<span class="unit"> MB</span></div>
  </div>
  <div class="card">
    <div class="label">CPU (%)</div>
    <div class="value" id="cpuVal">--<span class="unit"> %</span></div>
  </div>
  <div class="card">
    <div class="label">Threads Java</div>
    <div class="value" id="threadVal">--</div>
  </div>
  <div class="card">
    <div class="label">API Latencia</div>
    <div class="value" id="apiVal">--<span class="unit"> ms</span></div>
  </div>
</div>

<div class="charts">
  <div class="chart-box">
    <h3>Memoria (MB) vs Tiempo</h3>
    <canvas id="memChart"></canvas>
  </div>
  <div class="chart-box">
    <h3>CPU (%) vs Tiempo</h3>
    <canvas id="cpuChart"></canvas>
  </div>
</div>

<div class="log-box">
  <h3>Registro de Metricas</h3>
  <div class="log" id="logDiv"></div>
</div>

<script>
const MAX_POINTS = 120;
let memData = [], cpuData = [], labels = [];

function drawChart(canvasId, data, color, label) {
  const canvas = document.getElementById(canvasId);
  const ctx = canvas.getContext('2d');
  const W = canvas.parentElement.clientWidth - 36;
  const H = 200;
  canvas.width = W; canvas.height = H;
  ctx.clearRect(0, 0, W, H);

  if (data.length < 2) return;
  const max = Math.max(...data, 1) * 1.1;
  const step = W / (MAX_POINTS - 1);

  ctx.strokeStyle = '#334155'; ctx.lineWidth = 0.5;
  for (let i = 0; i < 5; i++) {
    const y = H - (H * (i / 4));
    ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(W, y); ctx.stroke();
    ctx.fillStyle = '#64748b'; ctx.font = '10px sans-serif';
    ctx.fillText((max * i / 4).toFixed(1), 2, y - 3);
  }

  const offset = MAX_POINTS - data.length;
  ctx.beginPath();
  ctx.strokeStyle = color; ctx.lineWidth = 2;
  ctx.lineJoin = 'round';
  data.forEach((v, i) => {
    const x = (i + offset) * step;
    const y = H - (v / max) * H;
    i === 0 ? ctx.moveTo(x, y) : ctx.lineTo(x, y);
  });
  ctx.stroke();

  const grad = ctx.createLinearGradient(0, 0, 0, H);
  grad.addColorStop(0, color + '40');
  grad.addColorStop(1, color + '00');
  ctx.lineTo((data.length - 1 + offset) * step, H);
  ctx.lineTo(offset * step, H);
  ctx.closePath();
  ctx.fillStyle = grad;
  ctx.fill();
}

function update() {
  fetch('/metrics').then(r => r.json()).then(data => {
    if (!data.length) return;
    const latest = data[data.length - 1];

    document.getElementById('memVal').innerHTML = latest.memMB.toFixed(1) + '<span class="unit"> MB</span>';
    document.getElementById('cpuVal').innerHTML = latest.cpuPercent.toFixed(2) + '<span class="unit"> %</span>';
    document.getElementById('threadVal').textContent = latest.threads;
    document.getElementById('apiVal').innerHTML = latest.apiLatencyMs.toFixed(1) + '<span class="unit"> ms</span>';

    const badge = document.getElementById('statusBadge');
    if (latest.apiStatus === 200) { badge.textContent = 'API OK'; badge.className = 'status status-running'; }
    else { badge.textContent = 'API DOWN'; badge.className = 'status status-unknown'; }

    memData = data.slice(-MAX_POINTS).map(d => d.memMB);
    cpuData = data.slice(-MAX_POINTS).map(d => d.cpuPercent);
    labels = data.slice(-MAX_POINTS).map(d => d.timestamp);

    drawChart('memChart', memData, '#38bdf8', 'Memoria');
    drawChart('cpuChart', cpuData, '#a78bfa', 'CPU');

    const log = document.getElementById('logDiv');
    const last15 = data.slice(-15).reverse();
    log.innerHTML = last15.map(d => {
      const cls = d.apiStatus === 200 ? 'ok' : 'err';
      return `<div><span class="ts">${d.timestamp}</span> | <span class="${cls}">API ${d.apiStatus}</span> | Mem: ${d.memMB}MB | CPU: ${d.cpuPercent}% | Threads: ${d.threads} | Lat: ${d.apiLatencyMs}ms</div>`;
    }).join('');
  }).catch(() => {});
}

update();
setInterval(update, 2000);
</script>
</body>
</html>"""


class Handler(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path == "/" or self.path == "/index.html":
            self.send_response(200)
            self.send_header("Content-Type", "text/html; charset=utf-8")
            self.end_headers()
            self.wfile.write(DASHBOARD_HTML.encode("utf-8"))
        elif self.path == "/metrics":
            self.send_response(200)
            self.send_header("Content-Type", "application/json")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
            with lock:
                self.wfile.write(json.dumps(metrics_history).encode("utf-8"))
        else:
            self.send_response(404)
            self.end_headers()

    def log_message(self, fmt, *args):
        pass


if __name__ == "__main__":
    collector = threading.Thread(target=collect_metrics, daemon=True)
    collector.start()

    server = http.server.HTTPServer(("0.0.0.0", PORT), Handler)
    print(f"Dashboard disponible en: http://localhost:{PORT}")
    print("Presiona Ctrl+C para detener")
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("\nDetenido.")
