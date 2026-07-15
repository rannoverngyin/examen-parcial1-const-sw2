from __future__ import annotations

import csv
from pathlib import Path

import matplotlib.pyplot as plt


BASE = Path("evidencia") / "sesion30"
ARCHIVO_CSV = BASE / "resumen_benchmark.csv"
ARCHIVO_GRAFICO = BASE / "grafico_p95.png"


def main() -> None:
    if not ARCHIVO_CSV.exists():
        raise FileNotFoundError(
            f"No se encontró el archivo: {ARCHIVO_CSV}"
        )

    etiquetas: list[str] = []
    valores_p95: list[float] = []

    with ARCHIVO_CSV.open(
        mode="r",
        encoding="utf-8",
    ) as archivo:
        lector = csv.DictReader(archivo)

        for fila in lector:
            etiqueta = (
                f"{fila['version']}\n{fila['run']}"
            )

            etiquetas.append(etiqueta)
            valores_p95.append(
                float(fila["p95_ms"])
            )

    plt.figure(figsize=(9, 5))
    plt.bar(etiquetas, valores_p95)

    plt.title(
        "Comparación del p95 por ejecución"
    )
    plt.xlabel("Versión y ejecución")
    plt.ylabel("p95 en milisegundos")

    plt.grid(
        axis="y",
        linestyle="--",
        alpha=0.4,
    )

    for indice, valor in enumerate(valores_p95):
        plt.text(
            indice,
            valor + 0.04,
            f"{valor:.2f}",
            ha="center",
            va="bottom",
        )

    plt.tight_layout()
    plt.savefig(
        ARCHIVO_GRAFICO,
        dpi=200,
    )

    print(
        "Gráfico generado correctamente en:"
    )
    print(ARCHIVO_GRAFICO)


if __name__ == "__main__":
    main()