from __future__ import annotations

import statistics
import time
import urllib.request
from dataclasses import dataclass

BASE_URL = "http://localhost:8080/rendimiento/productos"
WARMUP = 5
REPETICIONES = 30


@dataclass
class Resultado:
    nombre: str
    tiempos_ms: list[float]
    errores: int


def percentil_95(valores: list[float]) -> float:
    ordenados = sorted(valores)
    indice = max(0, int(0.95 * len(ordenados)) - 1)
    return ordenados[indice]


def medir(nombre: str) -> Resultado:
    url = f"{BASE_URL}/{nombre}"

    for _ in range(WARMUP):
        with urllib.request.urlopen(url, timeout=5) as respuesta:
            respuesta.read()

    tiempos = []
    errores = 0
    for _ in range(REPETICIONES):
        inicio = time.perf_counter()
        try:
            with urllib.request.urlopen(url, timeout=5) as respuesta:
                respuesta.read()
                if respuesta.status != 200:
                    errores += 1
        except Exception:
            errores += 1
        tiempos.append((time.perf_counter() - inicio) * 1000)

    return Resultado(nombre, tiempos, errores)


def mostrar(r: Resultado) -> None:
    print(f"\n{r.nombre.upper()}")
    print(f"promedio: {statistics.mean(r.tiempos_ms):.2f} ms")
    print(f"mediana: {statistics.median(r.tiempos_ms):.2f} ms")
    print(f"p95: {percentil_95(r.tiempos_ms):.2f} ms")
    print(f"mínimo: {min(r.tiempos_ms):.2f} ms")
    print(f"máximo: {max(r.tiempos_ms):.2f} ms")
    print(f"errores: {r.errores}")


if __name__ == "__main__":
    for version in ("base", "optimizado"):
        mostrar(medir(version))
