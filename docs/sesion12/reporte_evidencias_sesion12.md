# Reporte de Evidencias - Sesión 12

## Objetivo
Documentar las evidencias solicitadas para la sesión 12 de la práctica, dejando espacio para adjuntar las capturas correspondientes.

---

## 1. Captura de `mvn test` con `BUILD SUCCESS`

### Evidencia
Insertar aquí la captura de la terminal donde se observe la ejecución completa de las pruebas con resultado final `BUILD SUCCESS`.

### Archivo sugerido de imagen
[sesion12-mvn-test-build-success.png](sesion12-mvn-test-build-success.png)

### Comando ejecutado
```bash
./mvnw test
```

---

## 2. Captura de `ProductoControllerIntegrationTest.java`

### Evidencia
Insertar aquí la captura del archivo `ProductoControllerIntegrationTest.java` abierto en el editor.

### Archivo sugerido de imagen
[sesion12-producto-controller-integration-test.png](sesion12-producto-controller-integration-test.png)

### Ubicación del archivo
[src/test/java/pe/unas/demoapi/test/ProductoControllerIntegrationTest.java](../../src/test/java/pe/unas/demoapi/test/ProductoControllerIntegrationTest.java)

---

## 3. Captura del repositorio con commit realizado

### Evidencia
Insertar aquí la captura donde se vea el repositorio con el commit ya realizado y la rama activa correspondiente.

### Archivo sugerido de imagen
[sesion12-repo-con-commit.png](sesion12-repo-con-commit.png)

### Comandos de referencia
```bash
git status
git log --oneline -1
```

---

## 4. Breve explicación del flujo: MockMvc → Controller → Service

El flujo de prueba comienza en `MockMvc`, que simula una petición HTTP sin levantar el navegador real. `MockMvc` envía la solicitud al `Controller`, y el `Controller` procesa la entrada, valida los datos y delega la lógica de negocio al `Service`.

Después, el `Service` ejecuta la operación solicitada y devuelve el resultado al `Controller`. Finalmente, el `Controller` construye la respuesta HTTP que `MockMvc` verifica en la prueba, permitiendo validar el comportamiento completo de la capa web de forma controlada.

---

## Notas

- Este documento queda listo para que se agreguen las capturas solicitadas en la carpeta de la sesión 12.
- Si después quieres, también puedo dejarlo con formato más parecido al de la sesión 11.