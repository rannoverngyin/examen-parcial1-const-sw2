# Examen Parcial 1 – Construcción de Software II

## Universidad Nacional Agraria de la Selva
## Facultad de Ingeniería en Informática y Sistemas

---

## Objetivo

Implementar un microservicio aplicando:

- Java 17
- Spring Boot
- Clean Architecture
- Git + GitHub

---

## Instrucciones para el estudiante

### 1. Clonar el repositorio

```bash
git clone git@github.com:rannoverngyin/examen-parcial1-const-sw2.git


### 2. Ingresar al proyecto

```bash
cd examen-parcial1-const-sw2
```

### 3. Crear su rama

Formato obligatorio:

```bash
git checkout -b feature/apellido_nombre
```

Ejemplo:

```bash
git checkout -b feature/yanac_rannoverng
```

### 4. Resolver el ejercicio asignado

Implementar:

- Service
- Controller
- Endpoint REST

Estructura:

```text
domain/
application/
presentation/
```

### 5. Ejecutar

```bash
./mvnw spring-boot:run
```

### 6. Validar

```bash
curl http://localhost:8080/endpoint
```

### 7. Commit

```bash
git add .
git commit -m "Implementa API ejercicio"
```

### 8. Push

```bash
git push origin feature/apellido_nombre
```

### 9. Crear Pull Request

En GitHub → Compare & Pull Request

---

## Criterios de evaluación

| Criterio | Puntaje |
|----------|---------|
| Branch creada | 4 |
| Código funcional | 4 |
| Commit correcto | 4 |
| Push correcto | 4 |
| Pull Request | 4 |
| **Total** | **20** |

---

## Tiempo del examen
10 minutos

## Evidencia de pruebas de integración REST

Se ejecutaron pruebas de integración para servicios REST usando Spring Boot, MockMvc y Maven.

### Explicación del flujo MockMvc → Controller → Service

MockMvc simula una petición HTTP dentro del entorno de pruebas de Spring Boot, sin necesidad de levantar manualmente un servidor externo. La prueba envía solicitudes como GET, POST o DELETE hacia los endpoints REST definidos en el controlador.

Luego, el Controller recibe la petición, procesa los parámetros enviados y llama a los métodos correspondientes del Service. En este caso, ProductoController delega la lógica a ProductoService.

ProductoService ejecuta la lógica de aplicación, como listar productos, agregar un producto, eliminarlo, contar el total o verificar su existencia. Finalmente, la respuesta vuelve al Controller y la prueba valida el código de estado, el contenido textual o la respuesta JSON.

De esta manera, se comprueba que la capa Presentation y la capa Application trabajan correctamente de forma integrada.

