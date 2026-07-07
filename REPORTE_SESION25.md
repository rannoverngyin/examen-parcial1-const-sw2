# Reporte Sesión 25 — ORM vs SQL directo en Java

## 1. Objetivo
Comparar consultas ORM (Spring Data JPA) y SQL directo (JdbcTemplate) en una aplicación Spring Boot con Java 17.

## 2. Consultas evaluadas
- Cursos por ciclo (`findByCiclo` / `listarPorCiclo`)
- Conteo por docente (`countByDocente` / `contarPorDocente`)

## 3. Resultados

| Consulta | Resultado | Tiempo ms | Observación |
|---|---:|---:|---|
| ORM ciclo 7 | | | |
| SQL ciclo 7 | | | |
| ORM docente | | | |
| SQL docente | | | |

## 4. Plan de consulta (EXPLAIN)

```
(pegar aquí la salida de: java -jar target/sesion25-orm-sql-java.jar explain)
```

## 5. Efecto del índice `idx_cursos_docente`

| Consulta | Tiempo antes del índice (ms) | Tiempo después del índice (ms) |
|---|---:|---:|
| ORM docente | | |
| SQL docente | | |

## 6. Interpretación
Responder: ¿cuándo conviene ORM y cuándo SQL directo?

## 7. Evidencias
- Captura de ejecución del benchmark (`benchmark`).
- Captura del plan de consulta (`explain`).
- Commit de Git.
