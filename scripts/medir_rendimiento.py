from __future__ import annotations

import statistics
import time
import urllib.request
from dataclasses import dataclass


BASE_URL = "http://localhost:8080/rendimiento/productos"
WARMUP = 5
REPETICIONES = 30

# Para el reto aplicado:
# primero 10, después se cambiará a 1000 y finalmente a 10000.
CANTIDAD = 10000


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
    url = f"{BASE_URL}/{nombre}?cantidad={CANTIDAD}"

    # Calentamiento inicial de la JVM.
    for _ in range(WARMUP):
        with urllib.request.urlopen(url, timeout=10) as respuesta:
            respuesta.read()

    tiempos = []
    errores = 0

    # Mediciones registradas.
    for _ in range(REPETICIONES):
        inicio = time.perf_counter()

        try:
            with urllib.request.urlopen(url, timeout=10) as respuesta:
                respuesta.read()

                if respuesta.status != 200:
                    errores += 1

        except Exception:
            errores += 1

        tiempo_ms = (time.perf_counter() - inicio) * 1000
        tiempos.append(tiempo_ms)

    return Resultado(nombre, tiempos, errores)


def mostrar(resultado: Resultado) -> None:
    print(f"\n{resultado.nombre.upper()} - CANTIDAD={CANTIDAD}")
    print(f"promedio: {statistics.mean(resultado.tiempos_ms):.2f} ms")
    print(f"mediana: {statistics.median(resultado.tiempos_ms):.2f} ms")
    print(f"p95: {percentil_95(resultado.tiempos_ms):.2f} ms")
    print(f"mínimo: {min(resultado.tiempos_ms):.2f} ms")
    print(f"máximo: {max(resultado.tiempos_ms):.2f} ms")
    print(f"errores: {resultado.errores}")


for version in ("base", "optimizado"):
    mostrar(medir(version))
