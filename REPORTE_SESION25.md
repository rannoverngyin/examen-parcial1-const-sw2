# Reporte Técnico - Sesión 25: Benchmark ORM vs SQL Directo en Python

---

## 👤 Identificación del Estudiante
* **Nombre Completo:** Leonardo Benjamin Travezaño Rodríguez
* **Curso:** Construcción de Software II
* **Facultad:** Facultad de Ingeniería en Informática y Sistemas (FIIS)
* **Universidad:** Universidad Nacional Agraria de la Selva (UNAS)

---

## 📊 1. Tabla de Tiempos Obtenidos (Benchmark)

A continuación se detallan las métricas reales tomadas en el entorno local con un volumen de **5,000 registros** indexados en la base de datos SQLite (`fiis.db`):

| Consulta | Tipo de Acceso | Filas / Resultado | Tiempo Promedio (ms) |
| :--- | :--- | :---: | :---: |
| **Listar Cursos Ciclo 7** | ORM (SQLAlchemy) | 2500 | 19.5136 ms |
| **Listar Cursos Ciclo 7** | SQL Textual Directo | 2500 | 4.2851 ms |
| **Contar por Docente** | ORM (Carga + len) | 1250 | 7.4139 ms |
| **Contar por Docente** | SQL Directo (COUNT) | 1250 | 0.4267 ms |

---

## 🧠 2. Análisis e Interpretación Técnica

### A. ¿Por qué el SQL directo es significativamente más rápido que el ORM para listar registros?
El SQL directo interactúa con el driver nativo de la base de datos (`sqlite3`) de manera cruda, retornando tuplas de datos planos de forma inmediata. Por el contrario, el ORM añade una capa de abstracción llamada **mapeo u hidratación de objetos**. Por cada una de las 2,500 filas recuperadas, SQLAlchemy debe instanciar un objeto de la clase `Curso`, mapear sus atributos, inicializar los estados del ciclo de vida de la sesión de base de datos y rastrear cambios. Este overhead de CPU impacta de forma drástica en el tiempo de procesamiento.

### B. Análisis del conteo por docente (Análisis del COUNT en BD vs en Python)
La diferencia en este escenario es masiva (~7.41 ms en ORM frente a ~0.42 ms en SQL). 
* Con **SQL Directo**, la operación se delega por completo al motor de la base de datos utilizando `SELECT COUNT(*)`. El motor calcula la métrica internamente en sus estructuras de bajo nivel y transfiere por red un solo número entero.
* Con el **ORM**, se extrajeron los 1,250 objetos completos de la base de datos, se transfirieron por completo hacia la memoria RAM asignada al proceso de Python y se calculó la longitud usando la función nativa `len(datos)`. Traer datos pesados que no se van a utilizar únicamente para contarlos es un antipatrón severo de rendimiento.

---

## 🔍 3. Análisis de Optimización (Plan de Ejecución)

Al realizar el análisis del plan de consulta mediante el comando `EXPLAIN QUERY PLAN`, se obtuvo la siguiente traza:
`SEARCH cursos USING INDEX idx_cursos_docente (docente=?)`

**Interpretación:** El motor de base de datos no realizó un escaneo completo de la tabla (*SCAN TABLE*), el cual tiene una complejidad algorítmica de $O(N)$. En su lugar, utilizó estructuras de árbol de búsqueda de índices optimizados ($O(\log N)$) reduciendo los tiempos de respuesta para las agregaciones y búsquedas por parámetros de texto plano.

---

## 🏁 4. Conclusiones y Criterio de Selección de Arquitectura

1. **Cuándo usar un ORM:** Se debe priorizar en flujos transaccionales ordinarios de negocio (operaciones CRUD), donde la mantenibilidad, legibilidad del código, prevención innata de inyecciones SQL y la velocidad de desarrollo sean el factor clave.
2. **Cuándo migrar a SQL Directo:** Se debe emplear obligatoriamente en procesos Batch, reportes complejos con múltiples uniones (JOINs), subconsultas masivas, endpoints críticos de alta concurrencia o agregaciones matemáticas pesadas, donde los milisegundos de latencia dicten la experiencia de usuario y la eficiencia de costos en servidores cloud.
