GUÍA DE PRÁCTICA – SESIÓN 13
Mantenimiento del banco de pruebas
Curso	Construcción de Software II
Unidad	Unidad II: Pruebas unitarias
Tema	Mantenimiento del banco de pruebas, automatización y documentación de pruebas
Producto	Banco de pruebas organizado + test de regresión + workflow de GitHub Actions
Evidencia	mvn test exitoso + TEST_BANK.md + commit/push en rama propia
1. Objetivo de la práctica
Mantener y organizar el banco de pruebas de una aplicación Spring Boot, incorporando pruebas unitarias de regresión, documentación mínima de casos de prueba y automatización de ejecución con Maven y GitHub Actions.
•	Identificar qué es un banco de pruebas y por qué debe mantenerse.
•	Organizar pruebas por capa o responsabilidad.
•	Agregar pruebas de regresión ante nuevas reglas del sistema.
•	Documentar pruebas unitarias en un archivo TEST_BANK.md.
•	Automatizar la ejecución de pruebas con un workflow de GitHub Actions.
2. Conceptos breves para iniciar
Concepto	Aplicación en la práctica
Banco de pruebas	Conjunto organizado de pruebas automatizadas que verifican el comportamiento esperado del sistema.
Mantenimiento de pruebas	Actualizar, limpiar y documentar pruebas cuando cambian los requisitos o el código.
Prueba de regresión	Prueba que evita que una funcionalidad antes correcta vuelva a fallar.
Priorización	Ejecutar primero pruebas críticas: reglas de negocio, cálculos, seguridad o flujos más usados.
CI con GitHub Actions	Ejecutar pruebas automáticamente cada vez que se hace push o Pull Request.
3. Requisitos previos
•	Java 17 activo.
•	Maven funcionando: mvn -version o ./mvnw -version.
•	Proyecto Spring Boot usado en sesiones anteriores.
•	Dependencia spring-boot-starter-test disponible en pom.xml.
•	Repositorio Git local y remoto en GitHub.
4. Desarrollo paso a paso
4.1. Ubicarse en la raíz del proyecto
cd ~/Documents/const_sw2/examen-parcial1-const-sw2
ls
Debe aparecer: pom.xml, src/, mvnw, mvnw.cmd, README.md.
4.2. Crear una rama para la práctica
git checkout main
git pull origin main
git checkout -b feature/sesion13-banco-pruebas
4.3. Verificar o crear la clase ProductoService
Archivo: src/main/java/pe/unas/demoapi/application/ProductoService.java
package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    private final List<String> productos = new ArrayList<>();

    public ProductoService() {
        productos.add("Laptop");
        productos.add("Mouse");
    }

    public List<String> listar() {
        return productos;
    }

    public void agregar(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        productos.add(nombre.trim());
    }

    public void eliminar(String nombre) {
        productos.remove(nombre);
    }

    public int total() {
        return productos.size();
    }

    public boolean existe(String nombre) {
        return productos.contains(nombre);
    }
}
4.4. Crear banco de pruebas unitarias
Crear la carpeta si no existe:
mkdir -p src/test/java/pe/unas/demoapi/application
Archivo: src/test/java/pe/unas/demoapi/application/ProductoServiceTest.java
package pe.unas.demoapi.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    private ProductoService service;

    @BeforeEach
    void preparar() {
        service = new ProductoService();
    }

    @Test
    @DisplayName("Debe listar productos iniciales")
    void debeListarProductosIniciales() {
        assertEquals(2, service.total());
        assertTrue(service.existe("Laptop"));
        assertTrue(service.existe("Mouse"));
    }

    @Test
    @DisplayName("Debe agregar un producto válido")
    void debeAgregarProductoValido() {
        service.agregar("Teclado");

        assertEquals(3, service.total());
        assertTrue(service.existe("Teclado"));
    }

    @Test
    @DisplayName("Debe eliminar un producto existente")
    void debeEliminarProductoExistente() {
        service.eliminar("Mouse");

        assertEquals(1, service.total());
        assertFalse(service.existe("Mouse"));
    }

    @Test
    @DisplayName("No debe aceptar producto vacío")
    void noDebeAceptarProductoVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.agregar(""));
        assertThrows(IllegalArgumentException.class, () -> service.agregar("   "));
        assertThrows(IllegalArgumentException.class, () -> service.agregar(null));
    }
}
4.5. Ejecutar el banco de pruebas
./mvnw test
Resultado esperado: BUILD SUCCESS y pruebas sin fallos.
4.6. Documentar el banco de pruebas
Crear el archivo TEST_BANK.md en la raíz del proyecto:
nano TEST_BANK.md
Contenido sugerido:
# Banco de pruebas – Sesión 13

## Módulo evaluado
ProductoService

## Casos de prueba

| ID | Caso de prueba | Prioridad | Resultado esperado |
|----|----------------|-----------|--------------------|
| PU-01 | Listar productos iniciales | Alta | Retorna Laptop y Mouse |
| PU-02 | Agregar producto válido | Alta | Incrementa total y producto existe |
| PU-03 | Eliminar producto existente | Media | Reduce total y producto ya no existe |
| PU-04 | Rechazar producto vacío | Alta | Lanza IllegalArgumentException |

## Comando de ejecución
./mvnw test

## Evidencia
Captura de BUILD SUCCESS y commit en GitHub.

4.7. Automatizar pruebas con GitHub Actions
Crear carpeta y archivo del workflow:
mkdir -p .github/workflows
nano .github/workflows/tests.yml
Pegar el siguiente contenido:
name: Ejecutar pruebas unitarias

on:
  push:
    branches: [ main, "feature/**" ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      - name: Descargar código
        uses: actions/checkout@v4

      - name: Configurar Java 17
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'

      - name: Dar permisos a Maven Wrapper
        run: chmod +x mvnw

      - name: Ejecutar pruebas
        run: ./mvnw test

4.8. Registrar evidencia con Git
git status
git add .
git commit -m "Mantiene banco de pruebas de ProductoService"
git push origin feature/sesion13-banco-pruebas
Luego crear Pull Request en GitHub hacia main y verificar que el workflow se ejecute correctamente.
5. Ejercicio aplicado
Agregar una nueva regla de negocio: no se debe agregar un producto duplicado.
1.	Primero crear una prueba que falle: debe lanzar IllegalArgumentException cuando se agregue "Laptop" nuevamente.
2.	Luego modificar ProductoService.agregar() para cumplir la regla.
3.	Ejecutar ./mvnw test.
4.	Actualizar TEST_BANK.md con el nuevo caso PU-05.
5.	Registrar commit con el mensaje: Agrega prueba de producto duplicado.
Código orientativo de la prueba nueva
@Test
@DisplayName("No debe aceptar producto duplicado")
void noDebeAceptarProductoDuplicado() {
    assertThrows(IllegalArgumentException.class, () -> service.agregar("Laptop"));
}
Código orientativo de la regla en el service
public void agregar(String nombre) {
    if (nombre == null || nombre.isBlank()) {
        throw new IllegalArgumentException("El nombre del producto es obligatorio");
    }

    String nombreLimpio = nombre.trim();

    if (productos.contains(nombreLimpio)) {
        throw new IllegalArgumentException("El producto ya existe");
    }

    productos.add(nombreLimpio);
}
6. Evidencias de entrega
•	Captura de consola con ./mvnw test y BUILD SUCCESS.
•	Archivo ProductoServiceTest.java implementado.
•	Archivo TEST_BANK.md actualizado.
•	Workflow .github/workflows/tests.yml creado.
•	Commit y push en rama feature.
•	Pull Request creado en GitHub.
7. Errores frecuentes y solución
Error	Solución
No encuentra JUnit	Verificar que pom.xml incluya spring-boot-starter-test con scope test.
Tests fallan por datos compartidos	Usar @BeforeEach para crear una instancia limpia por prueba.
GitHub Actions falla por permisos	Agregar el paso chmod +x mvnw antes de ejecutar ./mvnw test.
Prueba depende del orden	Cada prueba debe ser independiente y no asumir ejecución previa.
No aparece el workflow en GitHub	Verificar ruta exacta: .github/workflows/tests.yml y hacer push.
8. Rúbrica de evaluación
Criterio	Logro esperado	Puntaje
Organización del banco de pruebas	Pruebas ubicadas y nombradas correctamente	4
Pruebas unitarias	Casos relevantes ejecutan correctamente	5
Documentación	TEST_BANK.md contiene casos, prioridad y resultado esperado	4
Automatización CI	Workflow de GitHub Actions ejecuta mvn test	4
Git y evidencia	Commit, push y Pull Request realizados correctamente	3
9. Cierre docente
Mensaje clave para explicar al estudiante:
“Un banco de pruebas no solo se crea: se mantiene, se documenta y se automatiza para proteger el software de regresiones.”
