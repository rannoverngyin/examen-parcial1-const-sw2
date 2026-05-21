# demoapi – Construcción de Software II · Sesión 13

## Descripción
Proyecto Spring Boot con banco de pruebas unitarias para `ProductoService`.

## Requisitos
- Java 17
- Maven 3.9+ (o usar el wrapper incluido: `./mvnw`)

## Ejecutar pruebas
```bash
./mvnw test
```

## Estructura
```
src/
  main/java/pe/unas/demoapi/
    DemoapiApplication.java
    application/ProductoService.java
  test/java/pe/unas/demoapi/
    application/ProductoServiceTest.java
.github/workflows/tests.yml
TEST_BANK.md
pom.xml
```
