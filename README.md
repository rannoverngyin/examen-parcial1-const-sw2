# examen-parcial1-const-sw2

**Construccion de Software II - Sesion 20: Contenedores y Despliegue**  
Universidad Nacional Agraria de la Selva (UNAS) - FIIS

---

## Tecnologias

- Java 17
- Spring Boot 3.2.5
- Maven
- Docker / Docker Compose

## Endpoints disponibles

| Metodo | Ruta             | Descripcion                  |
|--------|------------------|------------------------------|
| GET    | /productos       | Listar todos los productos   |
| GET    | /productos/{id}  | Obtener producto por ID      |
| POST   | /productos       | Crear nuevo producto         |
| PUT    | /productos/{id}  | Actualizar producto          |
| DELETE | /productos/{id}  | Eliminar producto            |
| GET    | /config/info     | Informacion de configuracion |
| GET    | /actuator/health | Estado de la aplicacion      |

---

## Pasos de la practica (Sesion 20)

### 1. Verificar prerrequisitos

```bash
java -version
mvn -version
docker --version
docker compose version
git --version
```

### 2. Ejecutar pruebas

```bash
./mvnw test
```

### 3. Generar el JAR

```bash
./mvnw clean package -DskipTests
ls target/*.jar
```

### 4. Construir la imagen Docker

```bash
docker build -t examen-parcial1-api:1.0 .
docker images | grep examen-parcial1-api
```

### 5. Ejecutar en contenedor (primer plano)

```bash
docker run --name examen-api -p 8080:8080 examen-parcial1-api:1.0
```

### 6. Validar endpoints

```bash
curl http://localhost:8080/productos
curl http://localhost:8080/config/info
curl http://localhost:8080/actuator/health
```

### 7. Ejecutar en segundo plano (detached)

```bash
docker rm examen-api
docker run -d --name examen-api -p 8080:8080 examen-parcial1-api:1.0
docker ps
docker logs examen-api
```

### 8. Docker Compose (API sola)

```bash
docker compose up --build
# En otra terminal:
curl http://localhost:8080/productos
# Para detener:
docker compose down
```

### 9. Docker Compose con PostgreSQL (seccion 12)

```bash
docker compose -f docker-compose-postgres.yml up --build
docker ps
# Verificar persistencia:
docker exec -it examen-db psql -U appuser -d appdb
CREATE TABLE prueba(id SERIAL PRIMARY KEY, nombre TEXT);
INSERT INTO prueba(nombre) VALUES ('dato persistente');
SELECT * FROM prueba;
\q
# Bajar y volver a levantar sin eliminar volumen:
docker compose -f docker-compose-postgres.yml down
docker compose -f docker-compose-postgres.yml up -d
docker exec -it examen-db psql -U appuser -d appdb -c "SELECT * FROM prueba;"
```

### 10. Registrar con Git

```bash
git status
git add Dockerfile .dockerignore docker-compose.yml docker-compose-postgres.yml
git commit -m "Dockeriza aplicacion Spring Boot"
git push origin feature/apellido_nombre
```

---

## Limpiar contenedores

```bash
docker stop examen-api
docker rm examen-api
```

## Errores frecuentes

| Error | Solucion |
|-------|----------|
| `docker: permission denied` | `sudo usermod -aG docker $USER` y reiniciar sesion |
| `COPY target/*.jar` no encuentra archivo | Ejecutar primero `./mvnw clean package -DskipTests` |
| `Port 8080 already allocated` | Usar `-p 8081:8080` |
| Contenedor se detiene | Revisar `docker logs examen-api` |
| `curl` devuelve 404 | Probar un endpoint real del proyecto |
