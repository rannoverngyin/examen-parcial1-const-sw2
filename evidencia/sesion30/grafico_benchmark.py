import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path

BASE = Path("evidencia/sesion30")

# Leer el resumen generado anteriormente
df = pd.read_csv(BASE / "resumen_benchmark.csv")

# Promedios por versión
promedios = df.groupby("version")[["p95_ms", "rps", "error_rate"]].mean()

# Crear gráfico
ax = promedios.plot(kind="bar", figsize=(8,5))

plt.title("Comparación de métricas: Baseline vs Optimizado")
plt.ylabel("Valor")
plt.xlabel("Versión")
plt.xticks(rotation=0)
plt.grid(axis="y", linestyle="--", alpha=0.5)

# Guardar imagen
plt.tight_layout()
plt.savefig(BASE / "comparacion_benchmark.png")

# Mostrar gráfico
plt.show()