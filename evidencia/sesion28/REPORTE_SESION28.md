# Reporte técnico - Sesión 28

## 1. Escenario

- **Endpoint:** `GET /rendimiento/productos`
- **Equipo y sistema operativo:** Windows 10/11, Java 17, Spring Boot, Google Chrome y k6.
- **Perfil/configuración:** Pruebas de carga ejecutadas con k6 utilizando diferentes cantidades de usuarios virtuales (VUs).
- **Etapas de carga:** Incremento progresivo de usuarios, carga sostenida y reducción gradual.
- **Umbrales:** Respuesta HTTP 200, sin errores y tiempos de respuesta estables.
- **Commit evaluado:** *(Agregar el hash del commit realizado).*

---

## 2. Resultados

### Prueba de carga - 20 Usuarios Virtuales (VUs)

| Métrica | Resultado |
|:--------|----------:|
| p50 (Mediana) | 28.55 ms |
| p95 | 36.88 ms |
| p99 (Máximo observado) | 147.13 ms |
| RPS | 16.83 req/s |
| Errores | 0 % |
| CPU | *(VisualVM/JFR)* |
| Memoria | *(VisualVM/JFR)* |

---

### Prueba de carga - 50 Usuarios Virtuales (VUs)

| Métrica | Resultado |
|:--------|----------:|
| p50 (Mediana) | 26.20 ms |
| p95 | 36.29 ms |
| p99 (Máximo observado) | 130.74 ms |
| RPS | 24.40 req/s |
| Errores | 0 % |
| CPU | *(VisualVM/JFR)* |
| Memoria | *(VisualVM/JFR)* |

---

### Prueba de carga - 120 ms (Versión Base)

| Métrica | Resultado |
|:--------|----------:|
| p50 (Mediana) | 127.05 ms |
| p95 | 136.61 ms |
| p99 (Máximo observado) | 221.86 ms |
| RPS | 15.36 req/s |
| Errores | 0 % |
| CPU | *(VisualVM/JFR)* |
| Memoria | *(VisualVM/JFR)* |

---

## 3. Hallazgos

- **Síntoma principal:** La latencia aumenta cuando el tiempo de procesamiento del endpoint es mayor, disminuyendo la cantidad de solicitudes atendidas por segundo.
- **Hipótesis de cuello de botella:** El retraso artificial incorporado en la versión base incrementa el tiempo de respuesta y limita el rendimiento bajo carga.
- **Evidencia que la sustenta:** En la prueba con mayor latencia (120 ms) se obtuvo un **p95 de 136.61 ms** y **15.36 solicitudes por segundo**, mientras que las pruebas de 20 y 50 usuarios virtuales mantuvieron un **p95 cercano a 36 ms**, una mayor cantidad de solicitudes por segundo y **0 % de errores**.

---

## 4. Decisión

Los umbrales establecidos se cumplieron en todas las pruebas realizadas. No se registraron errores HTTP y todas las solicitudes respondieron correctamente. Los resultados muestran que el servicio mantiene un comportamiento estable bajo carga; sin embargo, la versión con mayor tiempo de procesamiento presenta una latencia considerablemente superior y un menor rendimiento. Se recomienda utilizar la versión optimizada, ya que ofrece menores tiempos de respuesta y una mayor capacidad de procesamiento de solicitudes.


![alt text](image.png)

![alt text](image-1.png)
120
![alt text](image-3.png)

20
![alt text](image-2.png)


![alt text](image-4.png)

![alt text](image-5.png)
![alt text](image-6.png)