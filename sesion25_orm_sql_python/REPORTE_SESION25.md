# Reporte Sesión 25
- ORM vs SQL directo en Python

## 1. Objetivo
Comparar consultas realizadas con ORM y con SQL directo sobre SQLite para identificar cuándo conviene priorizar productividad, mantenibilidad o rendimiento.

## 2. Consultas evaluadas
- Cursos por ciclo
- Conteo por docente

## 3. Resultados

### Benchmark inicial

| Consulta | Resultado | Tiempo ms | Observación |
|---|---:|---:|---|
| ORM ciclo 7 | 2500 | 335.5060 | Carga objetos ORM y mapeo de resultados |
| SQL ciclo 7 | 2500 | 51.3623 | Consulta directa más eficiente para filtrado simple |
| ORM docente | 1250 | 308.9390 | Trae registros completos y luego cuenta en Python |
| SQL docente | 1250 | 2.6730 | COUNT en la base de datos evita traer filas innecesarias |

### Benchmark con índice sobre docente

| Consulta | Resultado | Tiempo ms | Observación |
|---|---:|---:|---|
| ORM ciclo 7 | 2500 | 259.9280 | Sigue siendo más costoso que SQL directo |
| SQL ciclo 7 | 2500 | 36.4417 | Sigue siendo más rápido para filtrado |
| ORM docente | 1250 | 115.9940 | Mejora por el uso de índices y menor costo de acceso |
| SQL docente | 1250 | 12.0510 | El índice ayuda, aunque el resultado en este entorno es muy cercano |

## 4. Interpretación
ORM conviene cuando se prioriza legibilidad, mantenibilidad y rapidez de desarrollo. SQL directo es más conveniente cuando se necesita máximo control, consultas muy específicas o mejores tiempos en operaciones de filtrado y agregación. En este laboratorio, SQL directo mostró mejor rendimiento en la mayoría de los casos, mientras que ORM resultó más pesado por la conversión a objetos. El índice sobre docente ayudó a mejorar el rendimiento de las consultas filtradas, aunque su impacto fue más evidente en el camino ORM que en el COUNT directo.

## 5. Evidencias
- Script de creación de base de datos: setup_db.py
- Script de benchmark: benchmark_queries.py
- Script de explicación del plan: explain_query.py
- Script para crear índice: crear_indice.py
- Ejecución del benchmark registrada en benchmark_output.txt
- Plan de consulta registrado en explain_output.txt

## 6. Respuestas a la reflexión técnica
1. ORM ofrece mayor mantenibilidad porque encapsula la lógica de acceso a datos y reduce el SQL embebido.
2. SQL directo es más conveniente para consultas complejas, reportes o escenarios donde se requiere rendimiento fino y control del plan.
3. El índice mejoró el acceso a filas filtradas, aunque el impacto depende del tamaño de los datos y del tipo de consulta.
4. COUNT es más eficiente porque la base de datos realiza la agregación en el motor, evitando traer miles de filas a Python.
5. Concatenar texto del usuario en SQL expone al sistema a inyecciones SQL, lo que puede comprometer la seguridad y la integridad de los datos.
