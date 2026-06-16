# Incremento: Internacionalización (i18n) en API REST

[cite_start]Este componente implementa el soporte multi-idioma para la gestión de mensajes de la API de la FIIS-UNAS, desacoplando los textos del código fuente mediante el uso de `MessageSource`[cite: 3, 5, 6, 17, 18].

---

## Objetivo
[cite_start]Separar los mensajes del código fuente utilizando archivos de propiedades independientes (`messages.properties`) y permitir cambiar el idioma de las respuestas de los endpoints dinámicamente mediante parámetros en la URL[cite: 6, 7, 8].

## Idiomas Soportados
[cite_start]La API detecta el parámetro `lang` y resuelve las cadenas basándose en los siguientes locales[cite: 43, 44, 51]:
* [cite_start]**Español (`es`)**: Idioma activo y por defecto del sistema[cite: 29, 31, 51].
* [cite_start]**Inglés (`en`)**: Traducciones mapeadas para el contexto internacional[cite: 33, 43].

---

## Endpoints Disponibles

[cite_start]Los siguientes endpoints responden dinámicamente según el idioma solicitado[cite: 51, 52, 53]:

| Método | Endpoint | Parámetro | Descripción |
| :--- | :--- | :--- | :--- |
| **GET** | `/i18n/saludo` | `lang=es` / `lang=en` | [cite_start]Mensaje de bienvenida al sistema[cite: 51]. |
| **GET** | `/i18n/curso` | `lang=es` / `lang=en` | [cite_start]Nombre de la asignatura actual[cite: 52]. |
| **GET** | `/i18n/idioma` | `lang=es` / `lang=en` | [cite_start]Confirmación del locale activo[cite: 53]. |

### Ejemplos de uso con `curl`:
```bash
# Consulta en Español (Por defecto)
curl "http://localhost:8080/i18n/saludo?lang=es"
# Respuesta: Bienvenido al sistema FIIS-UNAS

# Consulta en Inglés
curl "http://localhost:8080/i18n/saludo?lang=en"
# Respuesta: Welcome to the FIIS-UNAS system