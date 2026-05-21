# Reporte de Pruebas - Sesión 11
## Construcción de Software II · UNAS

## Pruebas ejecutadas

| Clase de prueba | Tipo | Descripción |
|---|---|---|
| `ProductoControllerIntegrationTest` | Integración | Valida endpoints REST con SpringBootTest y TestRestTemplate |
| `ProductoServiceConcurrencyTest` | Concurrencia | Simula 20 hilos simultáneos y verifica consistencia del servicio |
| `ProductoApiConcurrencyTest` | Concurrencia API | Simula 10 solicitudes POST concurrentes al endpoint real |

## Resultado esperado

```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Descripción de cada prueba

### ProductoControllerIntegrationTest
- **`listarProductos_debeResponderOk`**: Llama al GET /productos y verifica que el código HTTP sea 2xx y que el cuerpo contenga "Laptop".
- **`agregarProducto_debeResponderMensajeCorrecto`**: Llama al POST /productos?nombre=Teclado y verifica que la respuesta sea "Producto agregado".

### ProductoServiceConcurrencyTest
- **`agregarProductosConcurrentemente_debeMantenerConteoCorrecto`**: Lanza 20 hilos en paralelo (pool de 5) para agregar productos. Usa `CountDownLatch` para esperar que todos terminen y valida que el total sea `inicial + 20`.

### ProductoApiConcurrencyTest
- **`multiplesPostConcurrentes_debenResponderCorrectamente`**: Lanza 10 solicitudes POST concurrentes a `/productos` via HTTP real (RANDOM_PORT) y verifica que todas respondan con código 2xx.

## Tecnologías clave

- **`CopyOnWriteArrayList`**: Colección thread-safe que permite lecturas y escrituras concurrentes sin bloqueos. Reemplaza a `ArrayList` que no es segura para hilos.
- **`CountDownLatch`**: Mecanismo de sincronización que permite esperar a que un número determinado de operaciones concurrentes finalicen.
- **`ExecutorService`**: Pool de hilos que gestiona la ejecución concurrente de tareas.
- **`@SpringBootTest(webEnvironment = RANDOM_PORT)`**: Levanta el contexto real de Spring Boot en un puerto aleatorio para pruebas de integración.

## Conclusión

La API responde correctamente bajo condiciones normales y mantiene consistencia básica ante solicitudes concurrentes gracias al uso de `CopyOnWriteArrayList` en la capa de servicio. Las pruebas de integración confirman la correcta integración entre Controller, Service y el contexto de Spring.
