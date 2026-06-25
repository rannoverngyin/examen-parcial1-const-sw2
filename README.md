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



git checkout -b feature/sesion21-internacionalizacion-UPIACHIHUA

git Branch


git add .
git commit -m "Implementa internacionalizacion"