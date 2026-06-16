# Internacionalización

## Objetivo
Permitir que la API responda mensajes en más de un idioma sin modificar el código fuente.

## Idiomas soportados
- Español: `es`
- Inglés: `en`

## Endpoints

| Endpoint | Parámetro | Descripción |
|----------|-----------|-------------|
| `GET /i18n/saludo?lang=es` | `lang=es` | Saludo en español |
| `GET /i18n/saludo?lang=en` | `lang=en` | Saludo en inglés |
| `GET /i18n/curso?lang=es` | `lang=es` | Nombre del curso en español |
| `GET /i18n/curso?lang=en` | `lang=en` | Nombre del curso en inglés |
| `GET /i18n/idioma?lang=es` | `lang=es` | Idioma activo en español |
| `GET /i18n/idioma?lang=en` | `lang=en` | Idioma activo en inglés |
| `GET /i18n/saludo-header` | Header `Accept-Language` | Saludo usando cabecera HTTP |
| `GET /i18n/evaluacion?lang=es` | `lang=es` | Evaluación en español |
| `GET /i18n/evaluacion?lang=en` | `lang=en` | Evaluación en inglés |

## Archivos de mensajes

- `messages.properties` — mensajes por defecto (español)
- `messages_es.properties` — mensajes en español
- `messages_en.properties` — mensajes en inglés

## Configuración

```properties
spring.messages.basename=messages
spring.messages.encoding=UTF-8
```

## Arquitectura

```
MensajeService (application)
  └── obtenerMensaje(clave, idioma)
        └── MessageSource.getMessage(clave, null, locale)

InternacionalizacionController (presentation)
  └── Inyecta MensajeService
  └── Expone endpoints GET /i18n/*
```

## Pruebas

Se ejecutan con MockMvc en modo integración (`@SpringBootTest`).

```bash
./mvnw test
```

Resultado esperado: `BUILD SUCCESS — Tests run: 9, Failures: 0`

## Evidencia
- Capturas de curl para `/i18n/saludo?lang=es` y `?lang=en`
- Captura de `./mvnw test` con BUILD SUCCESS
- Commit en rama `feature/sesion21-internacionalizacion`
