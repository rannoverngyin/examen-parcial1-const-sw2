# Reporte de Práctica - Sesión 12

## Construcción de Software II

## Tema

Pruebas de integración en servicios REST con Spring Boot.

## Producto

Suite de pruebas de integración para endpoints REST usando MockMvc.

---

## 1. Objetivo de la práctica

El objetivo de esta práctica fue validar el funcionamiento integrado de servicios REST en una aplicación Spring Boot, verificando rutas, métodos HTTP, códigos de estado, respuestas JSON y la comunicación entre las capas `presentation` y `application`.

---

## 2. Herramientas utilizadas

- Java 17
- Spring Boot
- Maven
- JUnit 5
- MockMvc
- Visual Studio Code
- Git y GitHub

---

## 3. Estructura del proyecto

La estructura principal utilizada fue la siguiente:

```text
src
├── main
│   └── java
│       └── pe
│           └── unas
│               └── demoapi
│                   ├── application
│                   │   └── ProductoService.java
│                   ├── presentation
│                   │   └── ProductoController.java
│                   └── ExamenParcial1ConstSw2Application.java
│
└── test
    └── java
        └── pe
            └── unas
                └── demoapi
                    └── presentation
                        └── ProductoControllerIntegrationTest.java