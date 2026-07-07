# Reporte Sesión 26 -- Optimización de consultas

## 1. Consulta analizada
_Describir la consulta lenta (Q1, Q2, Q3 y la del ejercicio aplicado Q4)._

## 2. Tiempo antes de optimizar
_Registrar tiempo y plan de ejecución. Usar la respuesta de `GET /api/consultas/medir` (ejecutar ANTES de `POST /api/indices/crear`)._

## 3. Índice o cambio aplicado
_Indicar CREATE INDEX o mejora realizada. Ver `IndiceService` / `POST /api/indices/crear`._

## 4. Tiempo después de optimizar
_Registrar tiempo y nuevo plan. Usar `POST /api/consultas/comparar` o volver a llamar `GET /api/consultas/medir` después de crear los índices._

## 5. Interpretación técnica
_Explicar por qué mejoró o no mejoró (selectividad del índice, volumen de filas devueltas, costo del JOIN, etc.)._

## 6. Evidencias
_Capturas o salida de terminal / Postman de cada endpoint invocado._

---

## Tabla comparativa (paso 6 de la guía)

| Consulta | Antes (s) | Después (s) | Mejora (%) | Plan optimizado |
|---|---|---|---|---|
| Q1_busqueda_codigo | | | | |
| Q2_matriculas_semestre | | | | |
| Q3_join_escuela_semestre | | | | |
| Q4_fiis_nota_mayor_igual_14 (ejercicio aplicado) | | | | |

Fórmula de mejora porcentual (ya calculada automáticamente por `ComparacionConsulta.mejoraPorcentaje`):

```
mejora = ((tiempo_antes - tiempo_despues) / tiempo_antes) * 100
```
