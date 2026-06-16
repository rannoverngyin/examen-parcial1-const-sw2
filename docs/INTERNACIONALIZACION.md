# Internacionalización (Sesión 21)

## Objetivo
Permitir que la API responda mensajes en más de un idioma sin quemar código duro en el controlador, externalizando los textos.

## Idiomas soportados
- Español: `es`
- Inglés: `en`

## Evidencias de Pruebas Automatizadas

**E1: Ejecución de Pruebas (MockMvc)**
Pruebas de integración verificando los idiomas.
![Evidencia E1 - mvnw test](./e1_mvnw_test.png)

---

## Evidencias de Endpoints (cURL)

**E2: Saludo en Español**
- GET `/i18n/saludo?lang=es`
![Evidencia E2 - Saludo Español](./e2_saludo_es.png)

**E3: Saludo en Inglés**
- GET `/i18n/saludo?lang=en`
![Evidencia E3 - Saludo Inglés](./e3_saludo_en.png)

**E4: Consulta de Curso**
- GET `/i18n/curso?lang=en` y `lang=es`
![Evidencia E4 - Curso](./e4_curso.png)

**E5: Consulta de Idioma**
- GET `/i18n/idioma?lang=en`
![Evidencia E5 - Idioma](./e5_idioma.png)

---

## Evidencias Adicionales (Retos)

**E6: Reto Opcional (Cabecera Accept-Language)**
- GET `/i18n/saludo-header` con header `Accept-Language: en`
![Evidencia E6 - Reto Headers](./e6_reto_headers.png)

**E7: Ejercicio Aplicado (Evaluación)**
- GET `/i18n/evaluacion?lang=es` y `lang=en`
![Evidencia E7 - Evaluacion](./e7_evaluacion.png)

---

## Evidencia de Repositorio

**E8: Commit en la rama feature**
![Evidencia E8 - Git Push](./e8_git_push.png)
