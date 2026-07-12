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

---

## Sesión 28 – Prueba de Carga con k6

### Configuración del test

- **Script**: `scripts/s28-load.js`
- **Endpoint**: `GET http://localhost:8080/carga/productos`
- **Escenario**: 5 etapas (ramp-up, estable, ramp-up, estable, ramp-down)
- **Max VUs**: 30
- **Duración**: 70 segundos

### Métricas de CPU y Memoria (Spring Boot - Java PID 2720)

| Etapa | VUs | CPU acum (s) | CPU delta | Memoria (MB) |
|-------|-----|-------------|-----------|--------------|
| Baseline (sin carga) | 0 | 21.97 | - | 75.46 |
| INICIO (ramp 0→10) | 0→10 | 21.98 | +0.01 | 75.48 |
| ESTABLE 10 VUs | 10 | 22.19 | +0.22 | 59.02 |
| RAMPEO 10→30 VUs | 10→30 | 22.27 | +0.08 | 59.93 |
| ESTABLE 30 VUs | 30 | 22.98 | +0.71 | 45.30 |
| BAJADA 30→0 VUs | 30→0 | 23.27 | +0.29 | 48.20 |
| FIN | 0 | 23.34 | +0.07 | 48.95 |

### Métricas HTTP (k6)

| Métrica | Valor |
|---------|-------|
| Requests totales | 1,085 |
| Throughput | 15.34 req/s |
| Tasa de error | 0.00% |
| Checks exitosos | 100% (2,170) |
| p95 duración | 122.97 ms |
| p90 duración | 122.67 ms |
| Duración promedio | 121.80 ms |
| Duración max | 125.69 ms |

### Análisis

- **CPU no es cuello de botella**: Solo consumió ~1.37s de CPU en total para 1,085 requests. El procesador apenas se usa.
- **No hay fuga de memoria**: La memoria bajó de 75 MB a ~45-48 MB, indicando que el GC de Java liberó memoria correctamente.
- **p95 estable en ~122 ms** sin importar si hay 10 o 30 VUs, no hay degradación por carga.
- **Tasa de error 0%**: No se alcanzó saturación.
- **Conclusión**: La app responde de forma estable. El backend (latencia de BD o procesamiento) define los ~120 ms, no la carga. No se detecta cuello de botella en CPU ni memoria.
