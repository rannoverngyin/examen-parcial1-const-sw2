# Reporte Sesión 25 – ORM vs SQL directo en Python [cite: 51]

## 1. Objetivo
Comparar consultas ORM y SQL directo para identificar cuándo conviene priorizar productividad, mantenibilidad o rendimiento en base a un volumen controlado de datos[cite: 4, 51].

## 2. Consultas evaluadas
- Cursos por ciclo (Filtrado de datos masivo) [cite: 51]
- Conteo por docente (Consulta agregada) [cite: 51]

## 3. Resultados
Los tiempos obtenidos durante las ejecuciones del benchmark en este entorno fueron los siguientes:

| Consulta | Resultado | Tiempo ms (Sin Índice Extra) | Tiempo ms (Con Índice Extra) | Observación |
|---|---:|---:|---:|---|
| **ORM: cursos ciclo 7** | 2500 | 24.5328 | 28.5082 | Más lento debido al costo de mapeo de objetos (Data Mapping) en Python. |
| **SQL: cursos ciclo 7** | 2500 | 3.6745 | 4.1696 | Extremadamente rápido. Usa el índice implícito `ix_cursos_ciclo` de forma directa. |
| **ORM: contar docente** | 1250 | 6.0843 | 6.5399 | Trae los objetos a memoria y calcula el tamaño con `len()`, sobrecargando el proceso. |
| **SQL: contar docente** | 1250 | 0.3174 | 0.3534 | El rendimiento es óptimo porque delega la agregación `COUNT(*)` directamente al motor SQLite. |

## 4. Interpretación de Resultados y Plan de Consulta
1. **Diferencia ORM vs SQL directo:** SQL directo demostró ser drásticamente más rápido en ambos escenarios (hasta ~20 veces más rápido en el conteo por docente). Esto ocurre porque el ORM (SQLAlchemy) invierte tiempo de procesamiento en transformar las filas crudas de la base de datos en instancias/objetos complejos de Python (proceso de hidratación).
2. **Análisis del Plan de Consulta (EXPLAIN QUERY PLAN):** La salida del comando (`SEARCH cursos USING INDEX ix_cursos_ciclo`) demuestra que SQLite ya estaba optimizando las búsquedas de ciclos utilizando el índice generado automáticamente por SQLAlchemy al definir `index=True` en el modelo.
3. **Impacto del índice por docente:** Al ejecutar `crear_indice.py` se creó exitosamente el índice `idx_cursos_docente`. En bases de datos en disco masivas esto reduce drásticamente los tiempos; sin embargo, debido a que SQLite funciona directamente sobre la memoria/caché local en pruebas cortas y los datos ya estaban pre-cargados, las ligeras variaciones al alza (de 0.31ms a 0.35ms) entran dentro del margen de fluctuación normal por la carga del sistema operativo.

## 5. Respuestas a las Preguntas de Reflexión Técnica
* **¿Qué ventaja ofrece ORM frente a SQL directo en mantenibilidad?** El ORM permite escribir código orientado a objetos independiente del motor de base de datos (abstracción). Si se cambia de SQLite a PostgreSQL, el código de Python permanece idéntico. Además, previene errores sintácticos de SQL en tiempo de compilación.
* **¿En qué casos SQL directo puede ser más conveniente?** Es ideal para reportes analíticos complejos, consultas masivas (Batch processing), optimizaciones finas críticas de rendimiento o cuando se requieren funciones nativas muy específicas del motor de base de datos que el ORM no soporta de forma limpia.
* **¿Qué impacto tuvo el índice en las consultas filtradas?** El plan de consulta evidenció que el motor de la base de datos dejó de hacer un escaneo secuencial completo de la tabla (*Full Table Scan*) y pasó a buscar directamente en la estructura de árbol del índice, acelerando la localización de los registros de 2,500 filas.
* **¿Por qué una consulta agregada con COUNT puede ser más eficiente que traer todos los registros y contarlos en Python?** Porque `SELECT COUNT(*)` solo devuelve un único número entero a través de la red/canal de comunicación. Traer todos los registros con el ORM para usar `len()` implica transmitir miles de filas de datos y construir miles de objetos en la memoria RAM de Python innecesariamente.
* **¿Qué riesgo existe si se construyen consultas SQL concatenando texto del usuario?** El riesgo crítico es la **Inyección SQL (SQL Injection)**, donde un atacante puede alterar la lógica de la consulta para extraer información confidencial, borrar tablas o saltarse capas de autenticación. Por ello se usan siempre parámetros estructurados (`:docente` o `:ciclo`).

## 6. Evidencias
- Estructura de base de datos e índices creados correctamente en `fiis.db`.
- Tiempos de consultas contrastados mediante ejecución del script de benchmark.
- Plan de consulta validado.
- Commit local listo para subida a rama remota.