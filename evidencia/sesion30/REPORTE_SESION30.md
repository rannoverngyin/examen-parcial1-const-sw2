# Reporte Sesión 30: Benchmarking y análisis de resultados

## 1. Objetivo

Comparar el rendimiento de los endpoints:

```text
GET /benchmark/baseline
GET /benchmark/optimizado
```

bajo condiciones equivalentes de carga, con el fin de determinar si la versión optimizada presenta una mejora real, estable y suficiente para recomendar su adopción.

El análisis considera p95, p99, solicitudes por segundo, tasa de errores, checks exitosos, promedio entre ejecuciones, desviación estándar, mejora porcentual y estabilidad.

---

## 2. Ambiente de prueba

| Elemento | Valor |
|---|---|
| Proyecto | `examen-parcial1-const-sw2` |
| Rama | `feature/sesion30-benchmarking-hidalgo-dairon` |
| Sistema operativo | Windows 11 |
| Java | 24.0.2 |
| Maven | 3.9.15 |
| Python | 3.14.6 |
| k6 | 2.1.0 |
| Framework | Spring Boot |
| URL base | `http://localhost:8080` |
| Fecha de ejecución | 13 de julio de 2026 |

---

## 3. Diseño del benchmark

Las dos versiones fueron evaluadas con las mismas condiciones:

| Propiedad | Valor |
|---|---|
| Usuarios virtuales máximos | 20 VUs |
| Ramp-up | 15 segundos |
| Carga estable | 60 segundos |
| Ramp-down | 15 segundos |
| Duración por ejecución | 90 segundos |
| Repeticiones por versión | 3 |
| Pausa entre iteraciones | 1 segundo |
| Threshold de errores | Menor al 5 % |
| Threshold de p95 | Menor a 1000 ms |
| Threshold de checks | Mayor al 99 % |

La única diferencia entre ambos escenarios fue el endpoint evaluado:

```text
Baseline:   /benchmark/baseline
Optimizado: /benchmark/optimizado
```

No se modificó el código de la aplicación durante las mediciones.

---

## 4. Diferencia entre las implementaciones

### Versión baseline

La versión baseline crea una lista con 5000 registros, la recorre, filtra los elementos que contienen `99`, construye una nueva lista y devuelve el resultado en cada solicitud.

### Versión optimizada

La versión optimizada prepara el resultado una sola vez al iniciar el servicio y reutiliza una lista inmutable en cada solicitud.

Las dos versiones devuelven exactamente los mismos datos. Por ello, la comparación mide una diferencia de implementación y no una diferencia funcional.

---

## 5. Resultados por ejecución

| Versión | Run | p95 | p99 | RPS | Errores | Checks |
|---|---|---:|---:|---:|---:|---:|
| Baseline | run1 | 2.75 ms | 4.08 ms | 16.78 | 0.00 % | 100.00 % |
| Baseline | run2 | 2.57 ms | 3.31 ms | 16.79 | 0.00 % | 100.00 % |
| Baseline | run3 | 2.21 ms | 2.64 ms | 16.79 | 0.00 % | 100.00 % |
| Optimizado | run1 | 2.39 ms | 3.02 ms | 16.77 | 0.00 % | 100.00 % |
| Optimizado | run2 | 1.64 ms | 2.12 ms | 16.80 | 0.00 % | 100.00 % |
| Optimizado | run3 | 1.56 ms | 1.89 ms | 16.81 | 0.00 % | 100.00 % |

---

## 6. Resultados resumidos

| Métrica | Baseline promedio | Optimizado promedio | Variación |
|---|---:|---:|---:|
| p95 | 2.51 ms | 1.86 ms | **−25.85 %** |
| p99 | 3.34 ms | 2.34 ms | **−29.85 %** |
| RPS | 16.79 | 16.79 | **+0.03 %** |
| Errores HTTP | 0.00 % | 0.00 % | Sin cambios |
| Checks exitosos | 100.00 % | 100.00 % | Sin cambios |

### Gráfico comparativo del p95

La siguiente figura presenta el p95 obtenido en las tres ejecuciones de cada versión:

![Comparación del p95 por ejecución](grafico_p95.png)

La versión optimizada presenta valores menores en las tres ejecuciones. Sin embargo, la diferencia entre su primera corrida y las dos siguientes confirma la variabilidad identificada en el análisis estadístico.

---

## 7. Variabilidad

| Métrica | Baseline | Optimizado |
|---|---:|---:|
| Desviación del p95 | 0.27 ms | 0.46 ms |
| Coeficiente de variación del p95 | 10.76 % | 24.73 % |
| Desviación del p99 | 0.72 ms | 0.60 ms |
| Coeficiente de variación del p99 | 21.56 % | 25.64 % |
| Desviación de RPS | 0.00 | 0.02 |

El p95 optimizado presenta una desviación de 0.46 ms. Aunque el valor absoluto es pequeño, representa aproximadamente el 24.73 % de su promedio. Esto indica una variabilidad relativa considerable entre las tres ejecuciones.

---

## 8. Análisis técnico

### 8.1 ¿Qué métrica cambió más?

La métrica con mayor mejora fue el p99, que disminuyó en 29.85 %. El p95 también presentó una reducción importante de 25.85 %.

Esto indica que la versión optimizada reduce tanto la latencia habitual de los usuarios lentos como la cola de respuestas más tardías.

### 8.2 ¿La mejora fue consistente en las tres ejecuciones?

La versión optimizada obtuvo mejores resultados que el promedio baseline. Sin embargo, la primera ejecución optimizada registró un p95 de 2.39 ms, mientras que la segunda y tercera obtuvieron 1.64 ms y 1.56 ms.

Esa diferencia eleva la variabilidad relativa del p95 optimizado. Por ello, la mejora es prometedora, pero todavía no puede considerarse completamente estable con solo tres ejecuciones.

### 8.3 ¿Hubo errores?

No se registraron errores HTTP en ninguna ejecución. Los checks también tuvieron un cumplimiento del 100 % en todas las corridas. La reducción de latencia no se consiguió sacrificando la funcionalidad o la confiabilidad.

### 8.4 ¿Por qué el RPS casi no cambió?

El RPS se mantuvo alrededor de 16.79 solicitudes por segundo en ambas versiones.

Esto se explica principalmente por el `sleep(1)` configurado en cada iteración. La pausa de un segundo domina el ciclo de cada usuario virtual, por lo que una reducción de menos de un milisegundo en el tiempo del endpoint no produce un incremento importante del throughput.

Por tanto, en este experimento la optimización se refleja principalmente en la latencia y no en las solicitudes por segundo.

### 8.5 ¿Existe evidencia suficiente para aceptar la optimización?

La versión optimizada cumple varios criterios positivos:

- reduce el p95 más del 20 %;
- reduce el p99;
- no aumenta los errores;
- mantiene 100 % de checks exitosos;
- conserva el throughput.

Sin embargo, el coeficiente de variación del p95 optimizado es aproximadamente 24.73 %. Debido a esta variabilidad, todavía no existe evidencia suficiente para aceptar el cambio de manera definitiva.

---

## 9. Matriz de decisión

| Condición | Resultado observado | Evaluación |
|---|---|---|
| p95 disminuye al menos 20 % | Disminuyó 25.85 % | Cumple |
| p99 disminuye | Disminuyó 29.85 % | Cumple |
| Los errores no aumentan | Se mantuvieron en 0 % | Cumple |
| Checks superiores al 99 % | Se obtuvo 100 % | Cumple |
| Throughput no empeora | Se mantuvo estable | Cumple |
| Resultados estables entre runs | Variabilidad de p95 optimizado: 24.73 % | No cumple totalmente |

### Decisión

**Repetir el benchmark antes de aceptar definitivamente la versión optimizada.**

La implementación optimizada muestra una mejora relevante de latencia, pero se requieren ejecuciones adicionales para confirmar que el resultado es estable y no depende del ruido del sistema.

---

## 10. Recomendación técnica

Se recomienda:

1. conservar la versión optimizada como candidata;
2. repetir el benchmark con al menos cinco ejecuciones por versión;
3. realizar un calentamiento previo antes de comenzar a registrar resultados;
4. cerrar aplicaciones que generen carga en segundo plano;
5. mantener la misma máquina, versión del código y configuración;
6. comparar la mediana de las ejecuciones adicionales;
7. evaluar un escenario sin `sleep(1)` para observar mejor el efecto sobre el throughput;
8. registrar CPU, memoria y actividad de la JVM durante las pruebas.

No se recomienda descartar la optimización, porque presenta una reducción clara de p95 y p99 sin errores. La decisión correcta es aumentar la evidencia antes de incorporarla como cambio definitivo.

---

## 11. Evidencias

```text
evidencia/sesion30/
├── benchmark_baseline.js
├── benchmark_optimizado.js
├── resultados_baseline/
│   ├── run1.json
│   ├── run1.txt
│   ├── run2.json
│   ├── run2.txt
│   ├── run3.json
│   └── run3.txt
├── resultados_optimizado/
│   ├── run1.json
│   ├── run1.txt
│   ├── run2.json
│   ├── run2.txt
│   ├── run3.json
│   └── run3.txt
├── analizar_benchmark.py
├── resumen_benchmark.csv
├── salida-analisis.txt
└── REPORTE_SESION30.md
```

---

## 12. Comandos principales

### Ejecutar baseline

```powershell
& "C:\\Program Files\\k6\\k6.exe" run `
  -e BASE_URL=http://localhost:8080 `
  --no-color `
  --summary-export `
  .\\evidencia\\sesion30\\resultados_baseline\\run1.json `
  .\\evidencia\\sesion30\\benchmark_baseline.js
```

### Ejecutar optimizado

```powershell
& "C:\\Program Files\\k6\\k6.exe" run `
  -e BASE_URL=http://localhost:8080 `
  --no-color `
  --summary-export `
  .\\evidencia\\sesion30\\resultados_optimizado\\run1.json `
  .\\evidencia\\sesion30\\benchmark_optimizado.js
```

### Ejecutar el análisis

```powershell
python -X utf8 `
  .\\evidencia\\sesion30\\analizar_benchmark.py
```

---

## 13. Conclusión técnica

El benchmark permitió comparar las versiones baseline y optimizada bajo la misma carga y mediante tres ejecuciones por versión.

La versión optimizada redujo el p95 promedio de 2.51 ms a 1.86 ms, equivalente a una mejora de 25.85 %. El p99 disminuyó de 3.34 ms a 2.34 ms, lo que representa una mejora de 29.85 %.

Ambas versiones mantuvieron 0 % de errores HTTP y 100 % de checks exitosos. El RPS permaneció estable alrededor de 16.79 solicitudes por segundo, principalmente por la pausa de un segundo incorporada en cada iteración.

A pesar de las mejoras de latencia, el p95 optimizado presentó una variabilidad relativa de aproximadamente 24.73 %. Por esta razón, la evidencia actual no es suficiente para recomendar la adopción definitiva del cambio.

La versión optimizada debe mantenerse como candidata y el benchmark debe repetirse con más ejecuciones y calentamiento controlado. Si la reducción del p95 se mantiene sin aumentar los errores y con menor variabilidad, la optimización podrá aceptarse con mayor confianza.