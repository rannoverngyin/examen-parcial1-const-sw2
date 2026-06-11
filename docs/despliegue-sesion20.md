# INFORME DE LABORATORIO: SESIÓN 20 – CONTENEDORES Y DESPLIEGUE

## Resumen de Cumplimiento de Criterios (Rúbrica)

| Criterio Evaluado | [cite_start]Logro Esperado [cite: 104] | Estado | Sustento Técnico Basado en Logs |
| :--- | :--- | :--- | :--- |
| **1. Empaquetado Maven** | [cite_start]El proyecto compila y genera JAR sin errores. [cite: 104] | **LOGRADO** | [cite_start]Ejecución exitosa de `./mvnw clean package -DskipTests` [cite: 29] con resultado final **`BUILD SUCCESS`** en 3.865 segundos. El artefacto `.jar` ejecutable se reemplazó correctamente. |
| **2. Dockerfile** | [cite_start]Imagen construida correctamente con Java 17. [cite: 104] | **LOGRADO** | [cite_start]Comando `docker build -t examen-parcial1-api:1.0 .` [cite: 46] finalizado en 37.1 segundos con 9 pasos ejecutados (`9/9 FINISHED`). [cite_start]Se descargó la imagen base oficial `eclipse-temurin:17-jre`. [cite: 42] |
| **3. Ejecución en Contenedor** | [cite_start]La API responde desde el puerto publicado. [cite: 104] | **LOGRADO** | [cite_start]El contenedor `examen-api` mapeó el puerto `8080:8080` [cite: 67] y el servidor integrado Tomcat se inicializó en el puerto interno `8080 (http)`. |
| **4. Docker Compose** | [cite_start]Define y ejecuta servicios de manera reproducible. [cite: 104] | **LOGRADO** | Orquestación multi-contenedor activa. Red por defecto y volumen `postgres_data` creados. [cite_start]Ambos contenedores (`examen-db` y `examen-api`) se enlazaron con éxito. [cite: 76, 77] |

---

## Detalle Técnico de los Hitos Logrados

### 1. Empaquetado de la Aplicación (Maven)
Se ejecutó la limpieza del entorno y la compilación de los recursos del proyecto. El compilador de Java procesó los archivos fuente del examen parcial dirigiendo el resultado binario hacia la ruta interna de empaquetado:
* **Resultado:** `Building jar: target\examen-parcial1-const-sw2-0.0.1-SNAPSHOT.jar`
* **Verificación de Calidad:** `BUILD SUCCESS`.

### 2. Construcción de la Imagen Docker
[cite_start]A través de las instrucciones configuradas en el archivo `Dockerfile` [cite: 38] (pasos `FROM`, `WORKDIR` y `COPY`) [cite_start][cite: 43], las capas de la aplicación se unieron de manera estructurada:
* [cite_start]**Imagen Base:** `docker.io/library/eclipse-temurin:17-jre`[cite: 42].
* [cite_start]**Asignación del Contexto:** Transferencia de los binarios pesados (`target/*.jar`) hacia el entorno de trabajo virtualizado `/app`[cite: 42].
* [cite_start]**Etiquetado:** La imagen quedó registrada localmente bajo el nombre `examen-parcial1-api:1.0`[cite: 46].

### 3. Orquestación Multi-Servicio (Docker Compose)
[cite_start]Al ejecutar el despliegue con `docker compose up --build`[cite: 69], el motor de Docker levantó la arquitectura de la siguiente forma:

1. [cite_start]**Base de Datos (`examen-db`):** Se descargó la imagen oficial de PostgreSQL 16[cite: 76]. [cite_start]El motor inicializó el clúster de datos bajo el juego de caracteres `UTF8` y creó la base de datos `appdb` de manera aislada[cite: 76]. [cite_start]Quedó lista para aceptar conexiones en el puerto de red `5432`[cite: 77].
2. [cite_start]**Ciclo de Persistencia:** Se montó con éxito el volumen persistente asignado bajo la etiqueta `examen-parcial1-const-sw2_postgres_data` [cite: 77][cite_start], garantizando el resguardo de la información ante reinicios de infraestructura[cite: 86].
3. [cite_start]**Servicio API (`examen-api`):** El contenedor de Spring Boot arrancó leyendo de manera nativa la configuración del perfil activo asignado por entorno: `The following 1 profile is active: "docker"`[cite: 76].

### 4. Inicialización del Entorno de Ejecución (Logs de Spring Boot)
El framework cargó los componentes principales de manera ordenada:
* **Contenedor Embebido:** Inicialización de `TomcatWebServer` en el puerto `8080 (http)`.
* **Enrutador Central:** `Initializing Spring DispatcherServlet 'dispatcherServlet'`.
* **Confirmación de Arranque:** `Started ExamenParcial1ConstSw2Application in 2.191 seconds`.

---

## Evidencias Técnicas Registradas (Logs)

### Compilación y Construcción exitosa con Maven
```text
[INFO] --- jar:3.4.2:jar (default-jar) @ examen-parcial1-const-sw2 ---
[INFO] Building jar: C:\U\construcción_sw\examen\examen-parcial1-const-sw2\target\examen-parcial1-const-sw2-0.0.1-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.865 s
```

## Despliegue Exitoso en Red Orquestada
[+] up 22/22
 ✔ Image postgres:16               Pulled                                                                            53.3s
 ✔ Image examen-parcial1-const-sw2-api            Built                                                                             5.5s
 ✔ Container examen-db                            Created                                                                            0.6s
 ✔ Container examen-api                           Created                                                                            0.1s
Attaching to examen-api, examen-db
examen-api  |  :: Spring Boot ::                (v3.5.14)
examen-api  | 2026-06-11T03:16:24.382Z  INFO 1 --- [o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
examen-api  | 2026-06-11T03:16:24.402Z  INFO 1 --- [p.u.d.ExamenParcial1ConstSw2Application  : Started ExamenParcial1ConstSw2Application in 2.191 seconds