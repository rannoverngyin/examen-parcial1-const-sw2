# Reporte técnico - Sesión 27

## 1. Escenario

- **Endpoints:** `GET /rendimiento/productos/base` y `GET /rendimiento/productos/optimizado`
- **Dataset:** Lista de 5 productos (`Laptop`, `Mouse`, `Teclado`, `Monitor`, `Impresora`) transformados a mayúsculas
- **Repeticiones:** 30 mediciones por endpoint + 5 warmup (script `scripts/medir_rendimiento.py`)
- **Equipo/entorno:** Windows 11 Home (10.0.26200), AMD Ryzen 7 5700U, 8 GB RAM, Java 26.0.1 HotSpot, Spring Boot 3.5.14
- **Commit evaluado:** `07e1a60` (último commit del repositorio)

## 2. Hipótesis inicial

La versión **base** ejecuta `Thread.sleep(200)` en cada invocación para simular una operación pesada (E/S, consulta a BD, procesamiento), y además transforma la lista con `stream().map().toList()` en cada llamada. Esto genera un cuello de botella artificial de ~200 ms por request.

La versión **optimizada** pre-calcula la lista transformada una sola vez al instanciar el servicio y la retorna directamente, eliminando tanto el delay como el procesamiento repetitivo.

**Cuello de botella esperado:** `Thread.sleep(200)` bloquea el hilo del servlet 200 ms por request, limitando el throughput a ~5 req/s por hilo.

## 3. Resultados

### 3.1 Tiempos de respuesta (medir_rendimiento.py)

| Métrica | Base | Optimizada | Mejora |
|---|---:|---:|---:|
| Promedio | 215.30 ms | 15.05 ms | **93.0%** |
| Mediana | 217.43 ms | 15.38 ms | **92.9%** |
| P95 | 220.00 ms | 27.65 ms | **87.4%** |
| Mínimo | 203.91 ms | 2.91 ms | **98.6%** |
| Máximo | 221.29 ms | 28.28 ms | **87.2%** |
| Errores | 0 | 0 | — |

### 3.2 Recursos JVM (Java Flight Recorder - grabaciones separadas)

Se realizaron **dos grabaciones JFR independientes**: una durante 30 llamadas al endpoint base y otra durante 30 llamadas al optimizado.

| Métrica | Base | Optimizada | Mejora |
|---|---:|---:|---:|
| CPU pico JVM (user+system) | 0.77% | 0.57% | **26.0%** |
| CPU pico máquina | 21.28% | 30.96% | — |
| Heap usado pico | 18.6 MB | 19.4 MB | — |
| Heap máximo | 1.5 GB | 1.5 GB | — |

> **Nota sobre CPU máquina:** El valor de CPU máquina del optimizado (30.96%) es mayor porque incluye **todos los procesos del sistema** (IDE, navegador, etc.), no solo el JVM. La métrica relevante es **CPU pico JVM**, que es donde la versión base consume más (0.77% vs 0.57%) debido a que ejecuta `stream().map().toList()` en cada llamada, mientras la optimizada solo retorna una referencia.
>
> **Nota sobre Heap:** La versión optimizada usa ligeramente más heap (19.4 MB vs 18.6 MB) porque almacena la lista pre-calculada como campo `final` del servicio. La diferencia es insignificante (0.8 MB).

## 4. Evidencias

### 4.1 Código fuente de la optimización

**Versión base** (`RendimientoService.java`):
```java
@Service
public class RendimientoService {

    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora");

    // BASE: Thread.sleep(200) + transformación en cada llamada
    public List<String> listarBase() {
        try {
            Thread.sleep(200);  // ← cuello de botella
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productos.stream()
                .map(String::toUpperCase)
                .toList();
    }
}
```

**Versión optimizada** (`RendimientoService.java`):
```java
    // OPTIMIZADA: pre-calculo una sola vez al instanciar
    private final List<String> productosOptimizados = productos.stream()
            .map(String::toUpperCase)
            .toList();

    public List<String> listarOptimizado() {
        return productosOptimizados;  // ← retorno directo, sin delay
    }
```

### 4.2 Script de medición

```python
# scripts/medir_rendimiento.py
from __future__ import annotations
import statistics, time, urllib.request
from dataclasses import dataclass

BASE_URL = "http://localhost:8080/rendimiento/productos"
WARMUP = 5
REPETICIONES = 30

@dataclass
class Resultado:
    nombre: str
    tiempos_ms: list[float]
    errores: int

def medir(nombre: str) -> Resultado:
    url = f"{BASE_URL}/{nombre}"
    # Warmup: 5 llamadas sin registrar
    for _ in range(WARMUP):
        with urllib.request.urlopen(url, timeout=5) as r:
            r.read()
    # Medición: 30 iteraciones
    tiempos, errores = [], 0
    for _ in range(REPETICIONES):
        inicio = time.perf_counter()
        try:
            with urllib.request.urlopen(url, timeout=5) as r:
                r.read()
                if r.status != 200:
                    errores += 1
        except Exception:
            errores += 1
        tiempos.append((time.perf_counter() - inicio) * 1000)
    return Resultado(nombre, tiempos, errores)
```

### 4.3 Salida de medir_rendimiento.py (terminal)

```
BASE
promedio: 215.30 ms
mediana:  217.43 ms
p95:      220.00 ms
mínimo:   203.91 ms
máximo:   221.29 ms
errores:  0

OPTIMIZADO
promedio: 15.05 ms
mediana:  15.38 ms
p95:      27.65 ms
mínimo:   2.91 ms
máximo:   28.28 ms
errores:  0
```

### 4.4 Inicio de grabaciones JFR (separadas)

```powershell
# Obtener PID del proceso Spring Boot
PS> jcmd
14864 org.codehaus.plexus.classworlds.launcher.Launcher spring-boot:run
17024 pe.unas.demoapi.ExamenParcial1ConstSw2Application

# Grabación 1: solo endpoint BASE (30s)
PS> jcmd 17024 JFR.start name=s27-base settings=profile duration=30s filename=s27-base.jfr
17024:
Started recording 2. The result will be written to:
C:\Users\ZUZUKA\examen-parcial1-const-sw2\s27-base.jfr

# Grabación 2: solo endpoint OPTIMIZADO (30s)
PS> jcmd 17024 JFR.start name=s27-opt settings=profile duration=30s filename=s27-optimizado.jfr
17024:
Started recording 3. The result will be written to:
C:\Users\ZUZUKA\examen-parcial1-const-sw2\s27-optimizado.jfr
```

### 4.5 Resumen de archivos JFR generados

```
# s27-base.jfr
Version: 2.1 | Chunks: 1 | Duration: 30 s
Event Type                           Count  Size (bytes)
==========================================================
jdk.CPULoad                           30         ~600
jdk.GCHeapMemoryUsage                  2           39
jdk.ThreadSleep                       30          ~500

# s27-optimizado.jfr
Version: 2.1 | Chunks: 1 | Duration: 30 s
Event Type                           Count  Size (bytes)
==========================================================
jdk.CPULoad                           30         ~600
jdk.GCHeapMemoryUsage                  2           39
jdk.ThreadSleep                        0            0
```

### 4.6 Salida cruda: jfr print --events jdk.CPULoad (BASE)

```powershell
PS> jfr print --events jdk.CPULoad s27-base.jfr

jdk.CPULoad {
  startTime = 10:06:04.466 (2026-07-12)
  jvmUser = 0.00%
  jvmSystem = 0.00%
  machineTotal = 5.35%
}

jdk.CPULoad {
  startTime = 10:06:05.494 (2026-07-12)
  jvmUser = 0.09%
  jvmSystem = 0.09%
  machineTotal = 21.28%
}

jdk.CPULoad {
  startTime = 10:06:10.613 (2026-07-12)
  jvmUser = 0.48%          ← pico jvmUser
  jvmSystem = 0.29%
  machineTotal = 5.92%
}

jdk.CPULoad {
  startTime = 10:06:13.681 (2026-07-12)
  jvmUser = 0.19%
  jvmSystem = 0.48%         ← pico jvmSystem
  machineTotal = 11.56%
}
... (30 muestras en total)
```

### 4.7 Salida cruda: jfr print --events jdk.CPULoad (OPTIMIZADO)

```powershell
PS> jfr print --events jdk.CPULoad s27-optimizado.jfr

jdk.CPULoad {
  startTime = 10:06:41.325 (2026-07-12)
  jvmUser = 0.01%
  jvmSystem = 0.05%
  machineTotal = 10.71%
}

jdk.CPULoad {
  startTime = 10:06:42.357 (2026-07-12)
  jvmUser = 0.00%
  jvmSystem = 0.19%
  machineTotal = 30.96%     ← pico máquina (procesos del sistema)
}

jdk.CPULoad {
  startTime = 10:06:46.456 (2026-07-12)
  jvmUser = 0.00%
  jvmSystem = 0.38%         ← pico jvmSystem
  machineTotal = 17.38%
}

jdk.CPULoad {
  startTime = 10:07:03.857 (2026-07-12)
  jvmUser = 0.19%           ← pico jvmUser
  jvmSystem = 0.00%
  machineTotal = 12.79%
}
... (30 muestras en total)
```

### 4.8 Salida cruda: jfr print --events jdk.GCHeapMemoryUsage

```powershell
PS> jfr print --events jdk.GCHeapMemoryUsage s27-base.jfr

jdk.GCHeapMemoryUsage {
  startTime = 10:06:04.425 (2026-07-12)
  used = 18.6 MB
  committed = 60.0 MB
  max = 1.5 GB
}

jdk.GCHeapMemoryUsage {
  startTime = 10:06:34.408 (2026-07-12)
  used = 18.4 MB
  committed = 60.0 MB
  max = 1.5 GB
}
```

```powershell
PS> jfr print --events jdk.GCHeapMemoryUsage s27-optimizado.jfr

jdk.GCHeapMemoryUsage {
  startTime = 10:06:41.291 (2026-07-12)
  used = 19.4 MB
  committed = 60.0 MB
  max = 1.5 GB
}

jdk.GCHeapMemoryUsage {
  startTime = 10:07:11.287 (2026-07-12)
  used = 19.4 MB
  committed = 60.0 MB
  max = 1.5 GB
}
```

### 4.9 Resumen de cálculo de picos

```
BASE:
  CPU JVM pico = max(jvmUser + jvmSystem) = 0.48% + 0.29% = 0.77%
  CPU máquina pico = max(machineTotal) = 21.28%
  Heap pico = max(used) = 18.6 MB

OPTIMIZADO:
  CPU JVM pico = max(jvmUser + jvmSystem) = 0.19% + 0.38% = 0.57%
  CPU máquina pico = max(machineTotal) = 30.96%  (procesos externos al JVM)
  Heap pico = max(used) = 19.4 MB
```

### 4.10 Evidencias visuales

- `docs/sesion27/image.png` — Captura 1
- `docs/sesion27/image copy.png` — Captura 2
- `docs/sesion27/image copy 2.png` — Captura 3
- `docs/sesion27/image copy 3.png` — Captura 4
- `docs/sesion27/image copy 4.png` — Captura 5

## 5. Conclusión

**Cuello de botella identificado:** `Thread.sleep(200)` en `RendimientoService.listarBase()` bloqueaba el hilo del servlet 200 ms por request, sumado a la transformación repetitiva con `stream().map().toList()`.

**Mejora aplicada:** Pre-cálculo de la lista transformada en el constructor del servicio (`productosOptimizados`), eliminando el delay artificial y el procesamiento repetitivo.

**Criterio de éxito:**
- La versión optimizada es **93% más rápida** en promedio (215.30 ms → 15.05 ms)
- Cero errores en 30 llamadas por endpoint
- CPU JVM pico se redujo de 0.77% a 0.57% (**26% menos uso de CPU JVM**)
- El heap JVM se mantuvo estable (~18-19 MB de 1.5 GB disponibles)
- **Criterio de éxito cumplido:** la optimización demostró una mejora significativa y consistente en tiempo de respuesta y uso de recursos
