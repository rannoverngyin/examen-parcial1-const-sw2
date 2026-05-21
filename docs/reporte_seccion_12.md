# seccion 12

## ✅ Captura de mvn test con BUILD SUCCESS

> Inserta aquí la captura donde se visualiza la ejecución de pruebas exitosas.

![alt text](image.png)

---

## ✅ Captura del archivo ProductoControllerIntegrationTest.java

> Inserta aquí la captura del archivo de pruebas de integración.

![alt text](image-1.png)

---

## ✅ Captura del repositorio con commit realizado

> Inserta aquí la captura del repositorio en GitHub mostrando el commit realizado.

![Repositorio GitHub](repositorio-commit.png)

---

# 🚀 Breve explicación del flujo

## MockMvc → Controller → Service

El flujo de pruebas funciona utilizando MockMvc para simular solicitudes HTTP sin necesidad de iniciar un servidor real.

1. MockMvc envía solicitudes HTTP simuladas hacia los endpoints de la API REST.
2. El Controller recibe la solicitud y procesa las rutas definidas mediante anotaciones como @GetMapping y @PostMapping.
3. El Controller delega la lógica de negocio al Service.
4. El Service realiza operaciones sobre la lista de productos, como agregar, eliminar, verificar existencia y contar elementos.
5. Finalmente, la respuesta retorna desde el Service hacia el Controller y luego hacia MockMvc para validar el resultado esperado.

Este flujo permitió validar correctamente la integración entre las capas del sistema utilizando pruebas automatizadas con Spring Boot y JUnit 5.
