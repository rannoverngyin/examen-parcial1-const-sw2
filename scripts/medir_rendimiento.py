from __future__ import annotations

import statistics
import time
import urllib.error
import urllib.request
from dataclasses import dataclass


BASE_URL = "http://localhost:8080/rendimiento/productos"
WARMUP = 5
REPETICIONES = 30
TIMEOUT_SEGUNDOS = 5


@dataclass
class Resultado:
    nombre: str
    tiempos_ms: list[float]
    errores: int


def percentil_95(valores: list[float]) -> float:
    """
    Calcula el percentil 95 de una lista de tiempos.
    """
    if not valores:
        return 0.0

    ordenados = sorted(valores)
    indice = max(0, int(0.95 * len(ordenados)) - 1)

    return ordenados[indice]


def ejecutar_peticion(url: str) -> int:
    """
    Ejecuta una solicitud GET y devuelve el estado HTTP.
    """
    solicitud = urllib.request.Request(
        url,
        method="GET",
        headers={
            "Accept": "application/json",
            "Cache-Control": "no-cache",
        },
    )

    with urllib.request.urlopen(
        solicitud,
        timeout=TIMEOUT_SEGUNDOS,
    ) as respuesta:
        respuesta.read()
        return respuesta.status


def calentar_jvm(url: str) -> None:
    """
    Realiza solicitudes iniciales para calentar la JVM.
    """
    print(f"Realizando {WARMUP} solicitudes de calentamiento...")

    for numero in range(1, WARMUP + 1):
        try:
            estado = ejecutar_peticion(url)

            print(
                f"  calentamiento {numero}/{WARMUP}: "
                f"HTTP {estado}"
            )

        except Exception as error:
            print(
                f"  calentamiento {numero}/{WARMUP}: "
                f"ERROR - {error}"
            )


def medir(nombre: str) -> Resultado:
    """
    Mide el tiempo de respuesta de un endpoint.
    """
    url = f"{BASE_URL}/{nombre}"

    print("\n" + "=" * 55)
    print(f"PREPARANDO VERSIÓN: {nombre.upper()}")
    print("=" * 55)

    calentar_jvm(url)

    tiempos: list[float] = []
    errores = 0

    print(f"\nEjecutando {REPETICIONES} mediciones...")

    for numero in range(1, REPETICIONES + 1):
        inicio = time.perf_counter()

        try:
            estado = ejecutar_peticion(url)

            if estado != 200:
                errores += 1

        except (
            urllib.error.URLError,
            TimeoutError,
            Exception,
        ) as error:
            errores += 1
            print(f"  medición {numero}: ERROR - {error}")

        tiempo_ms = (time.perf_counter() - inicio) * 1000
        tiempos.append(tiempo_ms)

        print(
            f"  medición {numero:02}/{REPETICIONES}: "
            f"{tiempo_ms:.2f} ms"
        )

    return Resultado(
        nombre=nombre,
        tiempos_ms=tiempos,
        errores=errores,
    )


def mostrar(resultado: Resultado) -> None:
    """
    Muestra las estadísticas obtenidas.
    """
    print("\n" + "=" * 55)
    print(f"RESULTADOS: {resultado.nombre.upper()}")
    print("=" * 55)

    print(
        f"promedio : "
        f"{statistics.mean(resultado.tiempos_ms):.2f} ms"
    )

    print(
        f"mediana  : "
        f"{statistics.median(resultado.tiempos_ms):.2f} ms"
    )

    print(
        f"p95      : "
        f"{percentil_95(resultado.tiempos_ms):.2f} ms"
    )

    print(
        f"mínimo   : "
        f"{min(resultado.tiempos_ms):.2f} ms"
    )

    print(
        f"máximo   : "
        f"{max(resultado.tiempos_ms):.2f} ms"
    )

    print(f"errores  : {resultado.errores}")


def mostrar_comparacion(
    resultado_base: Resultado,
    resultado_optimizado: Resultado,
) -> None:
    """
    Compara el p95 de ambas versiones.
    """
    p95_base = percentil_95(resultado_base.tiempos_ms)
    p95_optimizado = percentil_95(
        resultado_optimizado.tiempos_ms
    )

    mejora = 0.0

    if p95_base > 0:
        mejora = (
            (p95_base - p95_optimizado)
            / p95_base
        ) * 100

    criterio_cumplido = (
        p95_optimizado < p95_base
        and resultado_base.errores == 0
        and resultado_optimizado.errores == 0
    )

    print("\n" + "=" * 55)
    print("COMPARACIÓN FINAL")
    print("=" * 55)

    print(f"p95 base       : {p95_base:.2f} ms")
    print(f"p95 optimizado : {p95_optimizado:.2f} ms")
    print(f"mejora         : {mejora:.2f} %")

    print(
        "criterio de éxito: "
        + (
            "CUMPLIDO"
            if criterio_cumplido
            else "NO CUMPLIDO"
        )
    )


def main() -> None:
    resultado_base = medir("base")
    resultado_optimizado = medir("optimizado")

    mostrar(resultado_base)
    mostrar(resultado_optimizado)

    mostrar_comparacion(
        resultado_base,
        resultado_optimizado,
    )


if __name__ == "__main__":
    main()