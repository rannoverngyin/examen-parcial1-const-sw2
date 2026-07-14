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


def medir(nombre: str, reps: int = REPETICIONES) -> Resultado:
    url = f"{BASE_URL}/{nombre}"

    for _ in range(WARMUP):
        try:
            with urllib.request.urlopen(url, timeout=10) as respuesta:
                respuesta.read()
        except Exception:
            pass

    tiempos = []
    errores = 0
    for _ in range(reps):
        inicio = time.perf_counter()
        try:
            with urllib.request.urlopen(url, timeout=10) as respuesta:
                respuesta.read()
                if respuesta.status != 200:
                    errores += 1
        except Exception:
            errores += 1
        tiempos.append((time.perf_counter() - inicio) * 1000)

    return Resultado(nombre, tiempos, errores)


def mostrar(r: Resultado) -> None:
    print(f"\n=========================================")
    print(f"ENDPOINT: {r.nombre.upper()}")
    print(f"=========================================")
    print(f"Promedio: {statistics.mean(r.tiempos_ms):.2f} ms")
    print(f"Mediana:  {statistics.median(r.tiempos_ms):.2f} ms")
    print(f"p95:      {percentil_95(r.tiempos_ms):.2f} ms")
    print(f"Mínimo:   {min(r.tiempos_ms):.2f} ms")
    print(f"Máximo:   {max(r.tiempos_ms):.2f} ms")
    print(f"Errores:  {r.errores}")


if __name__ == "__main__":
    print("### 1. MEDICIÓN DE ENDPOINTS ESTÁNDAR (Sesión 27) ###")
    for version in ("base", "optimizado"):
        mostrar(medir(version))

    print("\n\n### 2. MEDICIÓN RETO APLICADO (Evaluación de escala N=10, 1000, 10000) ###")
    escalas = [10, 1000, 10000]
    for n in escalas:
        print(f"\n--- Escala N = {n} ---")
        mostrar(medir(f"base?cantidad={n}", reps=15))
        mostrar(medir(f"optimizado?cantidad={n}", reps=15))
