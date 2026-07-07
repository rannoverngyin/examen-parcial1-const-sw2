# Sesión 26 — Optimización de consultas (Java 17 / Spring Boot)

Equivalente en Java 17 + Spring Boot 3.2.5 de la guía práctica original en Python/SQLite.
Usa H2 (archivo local `universidad.mv.db`) en lugar de SQLite, y `EXPLAIN` de H2
en lugar de `EXPLAIN QUERY PLAN` de SQLite — el concepto y el flujo de trabajo son los mismos.

## Requisitos

- Java 17
- Maven 3.8+ (o usar el wrapper `mvnw` si lo agregas con `mvn -N io.takari:maven:wrapper`)

## Cómo ejecutar

```bash
cd sesion26-optimizacion-consultas
mvn spring-boot:run
```

La aplicación levanta en `http://localhost:8080`.

## Flujo equivalente a los pasos de la guía

| Paso de la guía | Endpoint |
|---|---|
| Paso 2 — Crear BD con datos de prueba (`crear_bd.py`) | `POST /api/db/crear` |
| Paso 3 — Medir consultas sin optimizar (`medir_consultas.py`) | `GET /api/consultas/medir` |
| Paso 5 — Crear índices (`optimizar_consultas.py`) | `POST /api/indices/crear` |
| Paso 5 — Medir consultas después de índices | `GET /api/consultas/medir` (llamar de nuevo) |
| Paso 6 — Comparar antes/después en un solo ciclo | `POST /api/consultas/comparar` |
| — | `GET /api/indices` (lista índices creados) |
| Paso 12 — Ejercicio aplicado (FIIS, nota ≥ 14, con su propio índice) | `POST /api/consultas/ejercicio-aplicado` |

### Orden recomendado para generar evidencias

```bash
# 1. Crear base de datos y datos de prueba
curl -X POST http://localhost:8080/api/db/crear

# 2. Medir consultas SIN índices (guardar esta salida como "antes")
curl http://localhost:8080/api/consultas/medir

# 3. Comparar automáticamente: mide antes, crea índices, mide después
curl -X POST http://localhost:8080/api/consultas/comparar

# 4. Ejercicio aplicado (Q4): mide antes, crea su índice, mide después
curl -X POST http://localhost:8080/api/consultas/ejercicio-aplicado

# 5. Ver índices existentes
curl http://localhost:8080/api/indices
```

También puedes inspeccionar la base de datos manualmente en la consola web de H2:
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./universidad;AUTO_SERVER=TRUE`, usuario `sa`, sin contraseña).

## Estructura del proyecto

```
sesion26-optimizacion-consultas/
├── pom.xml
├── README.md
├── REPORTE_SESION26.md
└── src/main/
    ├── java/pe/unas/demoapi/
    │   ├── DemoApiApplication.java
    │   ├── controller/OptimizacionController.java
    │   ├── service/SeederService.java        (equivalente a crear_bd.py)
    │   ├── service/ConsultaService.java       (equivalente a medir_consultas.py)
    │   ├── service/IndiceService.java         (equivalente a optimizar_consultas.py)
    │   └── dto/ResultadoConsulta.java, ComparacionConsulta.java
    └── resources/application.properties
```

## Registro con Git (paso 14 de la guía)

```bash
git checkout -b feature/sesion26-optimizacion
git add .
git commit -m "Optimiza consultas y documenta resultados"
git push origin feature/sesion26-optimizacion
```

## Buenas prácticas aplicadas (paso 7)

- No se usa `SELECT *` salvo en Q1/Q2 (idénticas a la guía original); Q3 y Q4 seleccionan solo las columnas necesarias.
- Índices creados sobre columnas usadas en `WHERE` y `JOIN`: `codigo`, `escuela`, `semestre`, `estudiante_id`, y el compuesto `(semestre, estudiante_id)`.
- El índice del ejercicio aplicado, `idx_matriculas_semestre_nota (semestre, nota)`, se crea por separado para no mezclarse con la medición de Q1–Q3.
- Cada medición se hace con `System.nanoTime()` antes y después de crear los índices, igual que la guía original.
