@"
# Internacionalización

## Objetivo

Permitir que la API REST responda mensajes en más de un idioma usando internacionalización con Spring Boot.

## Idiomas soportados

- Español: es
- Inglés: en

## Implementación

Se implementó internacionalización usando MessageSource de Spring Boot.

Los mensajes fueron separados del código fuente y almacenados en archivos externos dentro de src/main/resources.

## Archivos creados

- messages.properties
- messages_es.properties
- messages_en.properties

## Endpoints implementados

| Método | Endpoint | Descripción |
|---|---|---|
| GET | /i18n/saludo?lang=es | Devuelve saludo en español |
| GET | /i18n/saludo?lang=en | Devuelve saludo en inglés |
| GET | /i18n/evaluacion?lang=es | Devuelve mensaje de evaluación en español |
| GET | /i18n/evaluacion?lang=en | Devuelve mensaje de evaluación en inglés |

## Pruebas

Se implementaron pruebas de integración con MockMvc.

Resultado obtenido:

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

## Evidencias

Se validaron los endpoints usando navegador y curl.

## Conclusión

La internacionalización permite que la API sea adaptable a diferentes idiomas sin modificar directamente el código de los controladores.
"@ | Set-Content -Encoding UTF8 docs\INTERNACIONALIZACION.md