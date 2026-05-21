# Cobertura de código – Sesión 14

## Módulo evaluado
CalidadService

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PC-01 | Clasificar cobertura alta | Alta | Retorna "ALTA" |
| PC-02 | Clasificar cobertura media | Alta | Retorna "MEDIA" |
| PC-03 | Clasificar cobertura baja | Media | Retorna "BAJA" |
| PC-04 | Rechazar cobertura negativa | Alta | Lanza IllegalArgumentException |
| PC-05 | Rechazar cobertura mayor a 100 | Alta | Lanza IllegalArgumentException |
| PC-06 | Validar cobertura aceptable en 70 | Media | Retorna true |
| PC-07 | Validar cobertura aceptable en 90 | Media | Retorna true |
| PC-08 | Validar cobertura no aceptable en 40 | Media | Retorna false |


### Ejecutar pruebas y generar cobertura
```bash
./mvnw clean test