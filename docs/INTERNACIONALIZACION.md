# Internacionalización en Spring Boot

## Objetivo
Implementar soporte multi-idioma (i18n) en una API REST con Spring Boot, separando los mensajes del código fuente en archivos de recursos externos y permitiendo cambiar la traducción dinámicamente.

## Estructura del Proyecto
- `application/MensajeService.java`: Servicio que administra y selecciona el idioma del mensaje.
- `presentation/InternacionalizacionController.java`: Controlador que expone los endpoints REST.
- `resources/messages*.properties`: Archivos de propiedades para los textos en español e inglés.

## Idiomas soportados
- **Español (es)** - Idioma por defecto
- **Inglés (en)**

## Endpoints

### 1. Saludo
- **URL**: `GET /i18n/saludo?lang={es|en}`
- **Descripción**: Retorna un mensaje de bienvenida.
- **Ejemplo**:
  - Petición: `GET /i18n/saludo?lang=en`
  - Respuesta: `Welcome to the FIIS-UNAS system`

### 2. Curso
- **URL**: `GET /i18n/curso?lang={es|en}`
- **Descripción**: Retorna el nombre del curso.
- **Ejemplo**:
  - Petición: `GET /i18n/curso?lang=es`
  - Respuesta: `Construcción de Software II`

### 3. Idioma Activo
- **URL**: `GET /i18n/idioma?lang={es|en}`
- **Descripción**: Informa sobre el idioma activo del sistema.
- **Ejemplo**:
  - Petición: `GET /i18n/idioma?lang=en`
  - Respuesta: `Active language: English`

### 4. Saludo mediante Cabecera (Reto Opcional)
- **URL**: `GET /i18n/saludo-header`
- **Cabecera**: `Accept-Language: {es|en}`
- **Descripción**: Resuelve el idioma basándose en las cabeceras HTTP del cliente.
- **Ejemplo**:
  - Petición: `GET /i18n/saludo-header` con cabecera `Accept-Language: en`
  - Respuesta: `Welcome to the FIIS-UNAS system`

### 5. Evaluación (Ejercicio Aplicado)
- **URL**: `GET /i18n/evaluacion?lang={es|en}`
- **Descripción**: Mensaje de evaluación configurada.
- **Ejemplo**:
  - Petición: `GET /i18n/evaluacion?lang=es`
  - Respuesta: `Evaluación configurada correctamente`

## Pruebas de Integración
Las respuestas han sido validadas a través de pruebas de integración con `MockMvc` localizadas en `InternacionalizacionControllerTest.java`.
