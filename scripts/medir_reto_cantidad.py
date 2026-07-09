from __future__ import annotations

import statistics
import time
import urllib.request
from dataclasses import dataclass

BASE_URL = "http://localhost:8080/rendimiento/productos"
WARMUP = 3
REPETICIONES = 15
CANTIDADES = (10, 1_000, 10_000)


@dataclass
class Resultado:
    nombre: str
    cantidad: int
    tiempos_ms: list[float]
    errores: int


def percentil_95(valores: list[float]) -> float:
    ordenados = sorted(valores)
    indice = max(0, int(0.95 * len(ordenados)) - 1)
    return ordenados[indice]


def medir(nombre: str, cantidad: int) -> Resultado:
    url = f"{BASE_URL}/{nombre}?cantidad={cantidad}"

    for _ in range(WARMUP):
        with urllib.request.urlopen(url, timeout=10) as respuesta:
            respuesta.read()

    tiempos = []
    errores = 0
    for _ in range(REPETICIONES):
        inicio = time.perf_counter()
        try:
            with urllib.request.urlopen(url, timeout=10) as respuesta:
                respuesta.read()
                if respuesta.status != 200:
                    errores += 1
        except Exception:
            errores += 1
        tiempos.append((time.perf_counter() - inicio) * 1000)

    return Resultado(nombre, cantidad, tiempos, errores)


def mostrar(r: Resultado) -> None:
    print(f"\n{r.nombre.upper()} - cantidad={r.cantidad}")
    print(f"promedio: {statistics.mean(r.tiempos_ms):.2f} ms")
    print(f"mediana: {statistics.median(r.tiempos_ms):.2f} ms")
    print(f"p95: {percentil_95(r.tiempos_ms):.2f} ms")
    print(f"errores: {r.errores}")


if __name__ == "__main__":
    for cantidad in CANTIDADES:
        for version in ("base", "optimizado"):
            mostrar(medir(version, cantidad))
