# Reporte Sesión 25 — ORM vs SQL directo en Spring Boot (Clean Architecture)

## 1. Objetivo
Comparar consultas ORM (Spring Data JPA) y SQL directo (JDBC) en un catálogo académico, 
identificando diferencias de rendimiento, mantenibilidad y control sobre las consultas.

## 2. Consultas evaluadas
- Cursos por ciclo (filtrada por ciclo = 7)
- Conteo por docente (Mg. Yanac)

## 3. Resultados
| Consulta | Filas | Tiempo (ms) | Observación |
|---|---:|---:|---|
| ORM: cursos ciclo 7 | 2500 | 786.8249 | Incluye overhead de mapeo objeto-relacional |
| SQL: cursos ciclo 7 | 2500 | 9.1199 | SQL directo, sin overhead de mapeo |
| ORM: contar Mg. Yanac | 1250 | 36.6835 | Incluye overhead de mapeo objeto-relacional |
| SQL: contar Mg. Yanac | 1250 | 3.2541 | SQL directo, sin overhead de mapeo |

## 4. Interpretación
ORM (Spring Data JPA) ofrece mayor productividad y mantenibilidad: 
las consultas se escriben en JPQL o Criteria API, el mapeo objeto-relacional es automático 
y los cambios de esquema requieren menos modificaciones en código. 
SQL directo (JDBC) ofrece mayor control sobre la consulta y, en general, 
mejor rendimiento en operaciones masivas o consultas altamente optimizadas, 
ya que no tiene la sobrecarga del mapeo ORM ni la generación automática de SQL.

### ¿Cuándo conviene ORM?
- Aplicaciones con dominio complejo y muchas relaciones entre entidades.
- Equipos que priorizan mantenibilidad y rapidez de desarrollo.
- Cuando se necesita cambiar de motor de base de datos con facilidad.

### ¿Cuándo conviene SQL directo?
- Consultas con alto volumen de datos donde cada milisegundo cuenta.
- Reportes complejos con joins y agregaciones muy específicas.
- Operaciones batch que requieren control fino del SQL generado.

## 5. Plan de ejecución (EXPLAIN)
```
SELECT
    "PUBLIC"."CURSOS"."ID",
    "PUBLIC"."CURSOS"."CODIGO",
    "PUBLIC"."CURSOS"."NOMBRE",
    "PUBLIC"."CURSOS"."CICLO",
    "PUBLIC"."CURSOS"."CREDITOS",
    "PUBLIC"."CURSOS"."DOCENTE"
FROM "PUBLIC"."CURSOS"
    /* PUBLIC.IDX_CURSOS_CICLO: CICLO = 7 */
    /* scanCount: 2501 */
WHERE "CICLO" = 7
```

## 6. Impacto del índice
El índice en la columna `docente` y `ciclo` permite búsquedas por índice (INDEX SCAN) 
en lugar de escaneo completo de tabla (TABLE SCAN). Esto reduce drásticamente 
el tiempo de consulta filtrada, especialmente al crecer el volumen de datos.

## 7. Reflexión técnica
- **Ventaja ORM en mantenibilidad**: el código queda desacoplado del SQL nativo; 
  los cambios de esquema se gestionan a nivel de entidades.
- **SQL directo más conveniente**: en consultas con alta demanda de rendimiento 
  o cuando se requiere sintaxis específica del motor no soportada por el ORM.
- **Impacto del índice**: reduce el costo de las búsquedas filtradas de O(n) a O(log n).
- **COUNT eficiente**: COUNT en SQL se ejecuta a nivel motor sin transferir filas; 
  contar en Java requiere traer todos los registros a memoria.
- **Riesgo de inyección SQL**: concatenar texto del usuario en SQL directo permite 
  inyección SQL. ORM con parámetros tipados (PreparedStatement / JPQL parametrizado) mitiga este riesgo.

## 8. Evidencias
- Código fuente organizado en Clean Architecture (domain, application, infrastructure, presentation)
- Proyecto Spring Boot con Spring Data JPA y JDBC
- Base de datos H2 embebida generada con 5000 registros
- Este reporte generado automáticamente
