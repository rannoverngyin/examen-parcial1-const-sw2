import json
import csv
from pathlib import Path
from statistics import mean, stdev

BASE = Path('evidencia/sesion30')
RUNS_ESPERADOS = ['run1', 'run2', 'run3']


def leer_metricas(carpeta, etiqueta):
    registros = []

    for run in RUNS_ESPERADOS:
        archivo = BASE / carpeta / f'{run}.json'

        if not archivo.exists():
            raise FileNotFoundError(
                f'No se encontró el archivo obligatorio: {archivo}'
            )

        with archivo.open(encoding='utf-8') as f:
            data = json.load(f)

        metricas = data['metrics']

        try:
            registros.append({
                'version': etiqueta,
                'run': archivo.stem,
                'p95_ms': metricas['http_req_duration']['p(95)'],
                'p99_ms': metricas['http_req_duration']['p(99)'],
                'rps': metricas['http_reqs']['rate'],
                'error_rate': metricas['http_req_failed']['value'],
            })

        except KeyError as error:
            raise KeyError(
                f'La métrica {error} no existe en {archivo}. '
                'Verifique que el benchmark se haya ejecutado '
                'incluyendo p(95) y p(99).'
            ) from error

    return registros


resultados = []
resultados += leer_metricas(
    'resultados_baseline',
    'baseline'
)
resultados += leer_metricas(
    'resultados_optimizado',
    'optimizado'
)


with (BASE / 'resumen_benchmark.csv').open(
    'w',
    newline='',
    encoding='utf-8'
) as f:

    writer = csv.DictWriter(
        f,
        fieldnames=[
            'version',
            'run',
            'p95_ms',
            'p99_ms',
            'rps',
            'error_rate'
        ]
    )

    writer.writeheader()
    writer.writerows(resultados)


resumen = {}

for version in ['baseline', 'optimizado']:
    subset = [
        registro
        for registro in resultados
        if registro['version'] == version
    ]

    p95 = [registro['p95_ms'] for registro in subset]
    p99 = [registro['p99_ms'] for registro in subset]
    rps = [registro['rps'] for registro in subset]
    errores = [registro['error_rate'] for registro in subset]

    resumen[version] = {
        'p95_promedio': mean(p95),
        'p99_promedio': mean(p99),
        'rps_promedio': mean(rps),
        'error_promedio': mean(errores),
        'p95_desviacion': stdev(p95),
        'p99_desviacion': stdev(p99),
        'rps_desviacion': stdev(rps),
        'error_desviacion': stdev(errores),
    }

    print('\nVersion:', version)

    print(
        'p95 promedio:',
        round(resumen[version]['p95_promedio'], 2),
        'ms'
    )

    print(
        'p95 desviacion:',
        round(resumen[version]['p95_desviacion'], 2),
        'ms'
    )

    print(
        'p99 promedio:',
        round(resumen[version]['p99_promedio'], 2),
        'ms'
    )

    print(
        'p99 desviacion:',
        round(resumen[version]['p99_desviacion'], 2),
        'ms'
    )

    print(
        'RPS promedio:',
        round(resumen[version]['rps_promedio'], 2)
    )

    print(
        'RPS desviacion:',
        round(resumen[version]['rps_desviacion'], 2)
    )

    print(
        'Error rate promedio:',
        round(
            resumen[version]['error_promedio'] * 100,
            2
        ),
        '%'
    )

    print(
        'Error rate desviacion:',
        round(
            resumen[version]['error_desviacion'] * 100,
            2
        ),
        '%'
    )


baseline_p95 = resumen['baseline']['p95_promedio']
opt_p95 = resumen['optimizado']['p95_promedio']

baseline_p99 = resumen['baseline']['p99_promedio']
opt_p99 = resumen['optimizado']['p99_promedio']

baseline_rps = resumen['baseline']['rps_promedio']
opt_rps = resumen['optimizado']['rps_promedio']

baseline_error = resumen['baseline']['error_promedio']
opt_error = resumen['optimizado']['error_promedio']


mejora_p95 = (
    (baseline_p95 - opt_p95)
    / baseline_p95
) * 100


print('\nCalculo de mejora porcentual de p95:')

print(
    '((',
    round(baseline_p95, 2),
    '-',
    round(opt_p95, 2),
    ') /',
    round(baseline_p95, 2),
    ') x 100'
)

print(
    'Mejora porcentual p95:',
    round(mejora_p95, 2),
    '%'
)


if opt_rps > baseline_rps and opt_p99 > baseline_p99:
    print(
        'Decision: analizar la cola larga, porque el RPS '
        'aumento, pero el p99 empeoro.'
    )

elif mejora_p95 >= 20 and opt_error <= baseline_error:
    print(
        'Decision: aceptar la optimizacion. El p95 disminuyo '
        'al menos 20% y el error rate no aumento.'
    )

elif opt_p95 < baseline_p95 and opt_error > baseline_error:
    print(
        'Decision: no aceptar todavia. El p95 disminuyo, '
        'pero el error rate aumento.'
    )

else:
    print(
        'Decision: revisar la hipotesis. No existe una mejora '
        'significativa suficiente.'
    )


print(
    '\nCSV generado:',
    BASE / 'resumen_benchmark.csv'
)