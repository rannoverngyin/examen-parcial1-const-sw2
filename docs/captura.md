=====================================================================
            PRUEBAS MANUALES - SESIÓN 21: INTERNACIONALIZACIÓN
=====================================================================

--- 1. ENDPOINTS BASE (QUERY PARAMS) ---

# Saludo en Español
curl.exe 'http://localhost:8080/i18n/saludo?lang=es'

# Saludo en Inglés
curl.exe 'http://localhost:8080/i18n/saludo?lang=en'

# Curso en Español
curl.exe 'http://localhost:8080/i18n/curso?lang=es'

# Curso en Inglés
curl.exe 'http://localhost:8080/i18n/curso?lang=en'

# Idioma en Español
curl.exe 'http://localhost:8080/i18n/idioma?lang=es'

# Idioma en Inglés
curl.exe 'http://localhost:8080/i18n/idioma?lang=en'


--- 2. EJERCICIO APLICADO (EVALUACIÓN) ---

# Evaluación en Español
curl.exe 'http://localhost:8080/i18n/evaluacion?lang=es'

# Evaluación en Inglés
curl.exe 'http://localhost:8080/i18n/evaluacion?lang=en'


--- 3. RETO OPCIONAL (CABECERA ACCEPT-LANGUAGE) ---

# Cabecera en Español
curl.exe -H "Accept-Language: es" 'http://localhost:8080/i18n/saludo-header'

# Cabecera en Inglés
curl.exe -H "Accept-Language: en" 'http://localhost:8080/i18n/saludo-header'


--- 4. PRUEBAS AUTOMATIZADAS ---

# Ejecución de pruebas con MockMvc en Windows PowerShell
.\mvnw.cmd test