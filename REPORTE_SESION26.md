\# Reporte Sesión 26 – Optimización de Consultas



\---



\## 👤 Datos del Estudiante

\* \*\*Nombre Completo:\*\* Leonardo Benjamin Travezaño Rodríguez

\* \*\*Curso:\*\* Construcción de Software II

\* \*\*Facultad:\*\* Facultad de Ingeniería en Informática y Sistemas (FIIS)

\* \*\*Universidad:\*\* Universidad Nacional Agraria de la Selva (UNAS)



\---



\## 📊 1. Matriz Comparativa de Tiempos (Antes vs Después)



A continuación se consolidan las mediciones de rendimiento obtenidas sobre la base de datos `universidad.db` con un volumen de \*\*80,000 matrículas\*\*:



| Consulta | Filas Devueltas | Tiempo Antes (s) | Tiempo Después (s) | Mejora (%) | Plan Optimizado (EXPLAIN QUERY PLAN) |

| :--- | :---: | :---: | :---: | :---: | :--- |

| \*\*Q1\_busqueda\_codigo\*\* | 1 | 0.001794 | 0.000277 | \*\*+84.56%\*\* | `SEARCH estudiantes USING INDEX idx\_estudiantes\_codigo (codigo=?)` |

| \*\*Q2\_matriculas\_semestre\*\* | 26,802 | 0.066725 | 0.085083 | \*\*-27.51%\*\* | `SEARCH matriculas USING INDEX idx\_matriculas\_semestre\_estudiante (semestre=?)` |

| \*\*Q3\_join\_escuela\_semestre\*\* | 6,609 | 0.034198 | 0.026291 | \*\*+23.12%\*\* | `SEARCH m USING INDEX idx\_matriculas\_semestre\_estudiante...` |

| \*\*Q4\_reto\_alta\_nota (Reto)\*\* | 1,984 | \*N/A\* | 0.048003 | \*\*Óptimo\*\* | `SEARCH m USING INDEX idx\_matriculas\_semestre\_estudiante...` |



\*Fórmula de mejora aplicada:\* $\\text{mejora} = \\frac{\\text{tiempo\\\_antes} - \\text{tiempo\\\_despues}}{\\text{tiempo\\\_antes}} \\times 100$



\---



\## 🧠 2. Interpretación y Respuestas al Cuestionario Técnico



\### A. ¿Qué ventaja ofrece un ORM frente a SQL directo en mantenibilidad?

El ORM (visto en la sesión 25) abstrae el motor SQL permitiendo manipular la base de datos mediante clases de Python, agilizando refactorizaciones, asegurando legibilidad, aislando al desarrollador de dialectos específicos de bases de datos y mitigando ataques de Inyección SQL de forma nativa.



\### B. ¿En qué casos SQL directo puede ser más conveniente?

Es fundamental en procesos masivos por lotes (\*Batch\*), consultas complejas analíticas, optimización manual de planes de ejecución donde los índices requieran un control estricto o cuando el costo de conversión de tuplas planas a objetos (\*hydration overhead\*) del ORM degrada críticamente la latencia del servicio.



\### C. ¿Qué impacto tuvo el índice en las consultas filtradas?

Transformó la complejidad algorítmica de búsquedas por código de un escaneo lineal completo $O(N)$ (`SCAN TABLE`) a una búsqueda logarítmica binaria estructurada $O(\\log N)$ (`SEARCH USING INDEX`), logrando caídas de tiempo de ejecución de más del 84%.



\### D. ¿Por qué la consulta Q2 decrementó su rendimiento con un índice?

Esto constituye un principio clave de bases de datos: \*\*Baja Selectividad\*\*. La consulta solicita registros del semestre `'2026-I'`, devolviendo un volumen masivo de datos (26,802 filas de 80,000). Cuando un índice retorna un porcentaje tan alto de la tabla, el motor pierde eficiencia realizando lecturas indexadas intercaladas y es preferible un barrido secuencial crudo (`SCAN`).



\### E. ¿Por qué una consulta agregada con COUNT es más eficiente que procesarla en Python?

Delegar `COUNT(\*)` al motor optimiza el canal de red e infraestructura: el motor computa la agregación binaria directamente sobre los descriptores físicos del índice y viaja únicamente un escalar de 4 u 8 bytes hacia Python, evitando transferir miles de objetos a la memoria RAM asignada al proceso local.



\### F. ¿Qué riesgo existe si se construyen consultas SQL concatenando cadenas del usuario?

Se introduce una vulnerabilidad crítica de \*\*Inyección SQL (SQLi)\*\*. Un atacante podría adulterar la cadena de entrada para omitir cláusulas `WHERE`, extraer información confidencial del catálogo, alterar datos o destruir tablas enteras ejecutando sentencias dañinas incrustadas.



\---



\## 🎯 3. Ejercicio Aplicado (Reto - Sesión 26)

Se implementó de manera exitosa la consulta optimizada solicitada en el apartado 12 de la guía práctica:

\* \*\*Filtros aplicados:\*\* `e.escuela = 'FIIS'`, `m.semestre = '2026-I'` y `m.nota >= 14`.

\* \*\*Índice compuesto utilizado:\*\* `idx\_matriculas\_semestre\_estudiante ON matriculas(semestre, estudiante\_id)`.

\* \*\*Justificación técnica:\*\* El plan utilizó el índice compuesto para filtrar el universo de matrículas reduciéndolo a las del semestre buscado, resolviendo inmediatamente la asociación del estudiante mediante su clave primaria indexada de forma nativa (`rowid`), descartando escaneos pesados de tablas.

