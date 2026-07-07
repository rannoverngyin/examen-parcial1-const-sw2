# Reporte Sesión 25 – ORM vs SQL directo en Python

## 1. Objetivo

Comparar el rendimiento de consultas implementadas mediante SQLAlchemy ORM y SQL directo sobre una base de datos SQLite, analizando sus tiempos de ejecución y el impacto del uso de índices.

---

## 2. Consultas evaluadas

* Consulta de cursos pertenecientes al ciclo 7.
* Consulta de conteo de cursos asignados al docente **Mg. Yanac**.

---

## 3. Resultados

| Consulta            | Resultado | Tiempo (ms) | Observación                                                                                                                       |
| ------------------- | --------: | ----------: | --------------------------------------------------------------------------------------------------------------------------------- |
| ORM: cursos ciclo 7 |      2500 |     20.5454 | El ORM recupera los registros y los convierte en objetos de Python, lo que incrementa el tiempo de ejecución.                     |
| SQL: cursos ciclo 7 |      2500 |      3.2276 | SQL directo ejecuta la consulta sin crear objetos, obteniendo un mejor rendimiento.                                               |
| ORM: contar docente |      1250 |      6.6893 | El ORM obtiene los registros y luego realiza el conteo mediante Python (`len()`), aumentando el tiempo de procesamiento.          |
| SQL: contar docente |      1250 |      0.3964 | La base de datos realiza el conteo mediante `COUNT(*)`, evitando transferir todos los registros y logrando un tiempo mucho menor. |

---

## 4. Interpretación

El uso de **SQLAlchemy ORM** es recomendable cuando se busca desarrollar aplicaciones con código más organizado, mantenible y orientado a objetos. El ORM simplifica las operaciones sobre la base de datos, reduce la cantidad de código SQL manual y facilita la portabilidad entre distintos motores de bases de datos.

Por otro lado, **SQL directo** resulta más conveniente cuando se requiere obtener el máximo rendimiento o implementar consultas específicas y optimizadas. En las pruebas realizadas, SQL directo obtuvo mejores tiempos porque ejecutó las consultas directamente en el motor de SQLite, evitando la sobrecarga de crear objetos del ORM. Esta diferencia fue especialmente notable en la consulta de conteo, donde `COUNT(*)` permitió que el cálculo se realizara completamente dentro de la base de datos sin recuperar todos los registros.

En conclusión, **ORM** ofrece ventajas en productividad y mantenibilidad, mientras que **SQL directo** proporciona un mayor control y un mejor rendimiento en consultas críticas o de gran volumen de datos.
