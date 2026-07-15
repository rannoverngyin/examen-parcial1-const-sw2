from __future__ import annotations

import csv
import json
from pathlib import Path
from statistics import mean, stdev
from typing import Any


BASE = Path("evidencia") / "sesion30"

CARPETAS = {
    "baseline": BASE / "resultados_baseline",
    "optimizado": BASE / "resultados_optimizado",
}

ARCHIVO_CSV = BASE / "resumen_benchmark.csv"


def obtener_metricas(data: dict[str, Any]) -> dict[str, Any]:
    """
    Obtiene el objeto metrics del resumen de k6.
    """
    metricas = data.get("metrics")

    if not isinstance(metricas, dict):
        raise ValueError(
            "El archivo JSON no contiene la sección 'metrics'."
        )

    return metricas


def obtener_valores_metrica(
    metricas: dict[str, Any],
    nombre: str,
) -> dict[str, Any]:
    """
    Soporta diferentes formatos de resumen generados por k6:
    - metric.values
    - metric.percentiles
    - valores directamente dentro de metric
    """
    metrica = metricas.get(nombre)

    if not isinstance(metrica, dict):
        raise ValueError(
            f"No se encontró la métrica '{nombre}'."
        )

    valores = metrica.get("values")

    if isinstance(valores, dict):
        return valores

    return metrica


def obtener_percentil(
    valores: dict[str, Any],
    percentil: int,
) -> float:
    """
    Busca un percentil en formatos como:
    p(95), 95, p95 o percentiles['95'].
    """
    posibles_claves = (
        f"p({percentil})",
        str(percentil),
        f"p{percentil}",
    )

    for clave in posibles_claves:
        if clave in valores:
            return float(valores[clave])

    percentiles = valores.get("percentiles")

    if isinstance(percentiles, dict):
        for clave in posibles_claves:
            if clave in percentiles:
                return float(percentiles[clave])

    raise ValueError(
        f"No se encontró el percentil p{percentil}."
    )


def obtener_numero(
    valores: dict[str, Any],
    clave: str,
    valor_defecto: float = 0.0,
) -> float:
    """
    Extrae un valor numérico de una métrica.
    """
    valor = valores.get(clave, valor_defecto)

    try:
        return float(valor)
    except (TypeError, ValueError):
        return valor_defecto

def obtener_rate_checks(
    metricas: dict[str, Any],
) -> float:
    """
    Calcula la tasa de checks exitosos y soporta
    los diferentes formatos generados por k6.
    """
    metrica = metricas.get("checks")

    if not isinstance(metrica, dict):
        return 0.0

    valores = metrica.get("values")

    if isinstance(valores, dict):
        if "rate" in valores:
            return float(valores["rate"])

        passes = float(valores.get("passes", 0))
        fails = float(valores.get("fails", 0))
    else:
        if "rate" in metrica:
            return float(metrica["rate"])

        passes = float(metrica.get("passes", 0))
        fails = float(metrica.get("fails", 0))

    total = passes + fails

    if total == 0:
        return 0.0

    return passes / total

def leer_metricas(
    carpeta: Path,
    version: str,
) -> list[dict[str, Any]]:
    """
    Lee todos los archivos run*.json de una versión.
    """
    archivos = sorted(carpeta.glob("run*.json"))

    if len(archivos) != 3:
        raise RuntimeError(
            f"Se esperaban 3 archivos JSON en {carpeta}, "
            f"pero se encontraron {len(archivos)}."
        )

    registros: list[dict[str, Any]] = []

    for archivo in archivos:
        with archivo.open(
            mode="r",
            encoding="utf-8",
        ) as entrada:
            data = json.load(entrada)

        metricas = obtener_metricas(data)

        duracion = obtener_valores_metrica(
            metricas,
            "http_req_duration",
        )

        solicitudes = obtener_valores_metrica(
            metricas,
            "http_reqs",
        )

        errores = obtener_valores_metrica(
            metricas,
            "http_req_failed",
        )


        registro = {
            "version": version,
            "run": archivo.stem,
            "p95_ms": obtener_percentil(
                duracion,
                95,
            ),
            "p99_ms": obtener_percentil(
                duracion,
                99,
            ),
            "rps": obtener_numero(
                solicitudes,
                "rate",
            ),
            "error_rate": obtener_numero(
                errores,
                "rate",
            ),
            "checks_rate": obtener_rate_checks(
                metricas,
            ),
        }

        registros.append(registro)

    return registros


def promedio(
    registros: list[dict[str, Any]],
    campo: str,
) -> float:
    return mean(
        float(registro[campo])
        for registro in registros
    )


def desviacion(
    registros: list[dict[str, Any]],
    campo: str,
) -> float:
    valores = [
        float(registro[campo])
        for registro in registros
    ]

    if len(valores) < 2:
        return 0.0

    return stdev(valores)


def porcentaje_cambio(
    valor_inicial: float,
    valor_final: float,
) -> float:
    if valor_inicial == 0:
        return 0.0

    return (
        (valor_final - valor_inicial)
        / valor_inicial
    ) * 100


def guardar_csv(
    registros: list[dict[str, Any]],
) -> None:
    campos = [
        "version",
        "run",
        "p95_ms",
        "p99_ms",
        "rps",
        "error_rate",
        "checks_rate",
    ]

    with ARCHIVO_CSV.open(
        mode="w",
        newline="",
        encoding="utf-8",
    ) as salida:
        writer = csv.DictWriter(
            salida,
            fieldnames=campos,
        )

        writer.writeheader()
        writer.writerows(registros)


def mostrar_detalle(
    registros: list[dict[str, Any]],
) -> None:
    print("\nRESULTADOS POR EJECUCIÓN")
    print("-" * 95)

    print(
        f"{'Versión':<12}"
        f"{'Run':<8}"
        f"{'p95 ms':>12}"
        f"{'p99 ms':>12}"
        f"{'RPS':>12}"
        f"{'Errores %':>14}"
        f"{'Checks %':>14}"
    )

    print("-" * 95)

    for registro in registros:
        print(
            f"{registro['version']:<12}"
            f"{registro['run']:<8}"
            f"{registro['p95_ms']:>12.2f}"
            f"{registro['p99_ms']:>12.2f}"
            f"{registro['rps']:>12.2f}"
            f"{registro['error_rate'] * 100:>14.2f}"
            f"{registro['checks_rate'] * 100:>14.2f}"
        )


def mostrar_resumen(
    registros: list[dict[str, Any]],
) -> dict[str, dict[str, float]]:
    resumen: dict[str, dict[str, float]] = {}

    print("\nPROMEDIOS Y VARIABILIDAD")
    print("=" * 60)

    for version in CARPETAS:
        subset = [
            registro
            for registro in registros
            if registro["version"] == version
        ]

        resumen[version] = {
            "p95": promedio(subset, "p95_ms"),
            "p95_desviacion": desviacion(
                subset,
                "p95_ms",
            ),
            "p99": promedio(subset, "p99_ms"),
            "p99_desviacion": desviacion(
                subset,
                "p99_ms",
            ),
            "rps": promedio(subset, "rps"),
            "rps_desviacion": desviacion(
                subset,
                "rps",
            ),
            "errores": promedio(
                subset,
                "error_rate",
            ),
            "checks": promedio(
                subset,
                "checks_rate",
            ),
        }

        valores = resumen[version]

        print(f"\nVersión: {version}")
        print(
            f"p95 promedio:       "
            f"{valores['p95']:.2f} ms"
        )
        print(
            f"p95 desviación:     "
            f"{valores['p95_desviacion']:.2f} ms"
        )
        print(
            f"p99 promedio:       "
            f"{valores['p99']:.2f} ms"
        )
        print(
            f"p99 desviación:     "
            f"{valores['p99_desviacion']:.2f} ms"
        )
        print(
            f"RPS promedio:       "
            f"{valores['rps']:.2f}"
        )
        print(
            f"RPS desviación:     "
            f"{valores['rps_desviacion']:.2f}"
        )
        print(
            f"Error promedio:     "
            f"{valores['errores'] * 100:.2f} %"
        )
        print(
            f"Checks exitosos:    "
            f"{valores['checks'] * 100:.2f} %"
        )

    return resumen


def mostrar_decision(
    resumen: dict[str, dict[str, float]],
) -> None:
    baseline = resumen["baseline"]
    optimizado = resumen["optimizado"]

    mejora_p95 = (
        (
            baseline["p95"]
            - optimizado["p95"]
        )
        / baseline["p95"]
    ) * 100

    mejora_p99 = (
        (
            baseline["p99"]
            - optimizado["p99"]
        )
        / baseline["p99"]
    ) * 100

    cambio_rps = porcentaje_cambio(
        baseline["rps"],
        optimizado["rps"],
    )

    errores_aumentaron = (
        optimizado["errores"]
        > baseline["errores"]
    )

    variacion_aceptable = (
        optimizado["p95_desviacion"]
        <= optimizado["p95"] * 0.20
    )

    print("\nCOMPARACIÓN FINAL")
    print("=" * 60)
    print(f"Mejora del p95: {mejora_p95:.2f} %")
    print(f"Mejora del p99: {mejora_p99:.2f} %")
    print(f"Cambio en RPS:  {cambio_rps:.2f} %")

    print("\nDECISIÓN TÉCNICA")
    print("=" * 60)

    if (
        mejora_p95 >= 20
        and not errores_aumentaron
        and variacion_aceptable
    ):
        print(
            "RECOMENDACIÓN: aceptar la versión optimizada."
        )
        print(
            "La reducción del p95 es igual o superior "
            "al 20 %, los errores no aumentaron y las "
            "ejecuciones presentan estabilidad."
        )

    elif errores_aumentaron:
        print(
            "RECOMENDACIÓN: no aceptar todavía la "
            "versión optimizada."
        )
        print(
            "La latencia puede haber mejorado, pero la "
            "tasa de errores aumentó."
        )

    elif not variacion_aceptable:
        print(
            "RECOMENDACIÓN: repetir el benchmark."
        )
        print(
            "Los resultados presentan demasiada "
            "variabilidad entre ejecuciones."
        )

    else:
        print(
            "RECOMENDACIÓN: revisar la hipótesis de "
            "optimización."
        )
        print(
            "La mejora del p95 es inferior al 20 %."
        )


def main() -> None:
    resultados: list[dict[str, Any]] = []

    for version, carpeta in CARPETAS.items():
        resultados.extend(
            leer_metricas(
                carpeta=carpeta,
                version=version,
            )
        )

    guardar_csv(resultados)
    mostrar_detalle(resultados)

    resumen = mostrar_resumen(resultados)
    mostrar_decision(resumen)

    print(
        "\nCSV generado correctamente en:"
        f"\n{ARCHIVO_CSV}"
    )


if __name__ == "__main__":
    try:
        main()
    except (
        FileNotFoundError,
        ValueError,
        RuntimeError,
        json.JSONDecodeError,
    ) as error:
        print(f"\nERROR: {error}")
        raise SystemExit(1)