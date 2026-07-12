import time
import statistics
import urllib.request
from dataclasses import dataclass

BASE_URL = "http://localhost:8080/rendimiento/productos"
WARMUP = 5
REPETICIONES = 30
CANTIDADES = [10, 1000, 10000]

@dataclass
class Resultado:
    version: str
    cantidad: int
    tiempos_ms: list[float]
    errores: int


def percentil_95(valores: list[float]) -> float:
    ordenados = sorted(valores)
    if not ordenados:
        return 0.0
    indice = max(0, int(0.95 * len(ordenados)) - 1)
    return ordenados[indice]


def medir(version: str, cantidad: int) -> Resultado:
    url = f"{BASE_URL}/{version}?cantidad={cantidad}"

    # Warmup
    for _ in range(WARMUP):
        try:
            with urllib.request.urlopen(url, timeout=15) as respuesta:
                respuesta.read()
        except Exception:
            pass

    tiempos = []
    errores = 0
    for _ in range(REPETICIONES):
        inicio = time.perf_counter()
        try:
            with urllib.request.urlopen(url, timeout=15) as respuesta:
                respuesta.read()
                if respuesta.status != 200:
                    errores += 1
        except Exception:
            errores += 1
        tiempos.append((time.perf_counter() - inicio) * 1000)

    return Resultado(version, cantidad, tiempos, errores)


def mostrar(r: Resultado) -> None:
    print(f"\nVERSION: {r.version.upper()} - CANTIDAD: {r.cantidad}")
    print(f"promedio: {statistics.mean(r.tiempos_ms):.2f} ms")
    print(f"mediana:  {statistics.median(r.tiempos_ms):.2f} ms")
    print(f"p95:      {percentil_95(r.tiempos_ms):.2f} ms")
    print(f"mínimo:   {min(r.tiempos_ms):.2f} ms")
    print(f"máximo:   {max(r.tiempos_ms):.2f} ms")
    print(f"errores:  {r.errores}")


print("Iniciando medición del Reto...")
for cant in CANTIDADES:
    for version in ("base", "optimizado"):
        mostrar(medir(version, cant))
