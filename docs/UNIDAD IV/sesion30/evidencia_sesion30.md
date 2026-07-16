# Reporte Sesión 30: Benchmarking y análisis de resultados

## 1. Objetivo
Comparar el rendimiento de `/benchmark/baseline` y `/benchmark/optimizado` bajo condiciones equivalentes.

## 2. Ambiente de prueba
- **Equipo:** ThinkPad Laptop
- **Sistema operativo:** Microsoft Windows 10 Pro (Versión 10.0.19045.6466)
- **Java:** SE 24.0.2 (compilado con nivel de compatibilidad Java 17)
- **Spring Boot:** 3.5.14
- **Herramienta de carga:** k6 v2.1.0
- **Fecha y hora:** 16 de Julio de 2026

## 3. Diseño del benchmark
- **Usuarios virtuales:** 20 VUs (máximo)
- **Duración:** 1m 30s (15s de ramp-up, 1m de hold, 15s de ramp-down)
- **Ramp-up:** 15s
- **Número de repeticiones:** 3 por escenario
- **Endpoints evaluados:**
  - `/benchmark/baseline`
  - `/benchmark/optimizado`

## 4. Resultados resumidos
| Versión | p95 promedio | p99 promedio | RPS promedio | Error rate |
|---|---:|---:|---:|---:|
| Baseline | 5.27 ms | 5.27 ms* | 16.72 | 0.00% |
| Optimizado | 2.83 ms | 2.83 ms* | 16.73 | 0.00% |

*\*Nota: Al no haberse recolectado el percentil p99 de forma diferenciada en la configuración del resumen, se muestra el valor del p95 como referencia.*

## 5. Análisis
- **¿Qué métrica cambió más?**
  - El tiempo de respuesta del percentil 95 (p95) promedio, el cual se redujo a casi la mitad, pasando de **5.27 ms** en Baseline a **2.83 ms** en Optimizado, logrando una mejora del **46.33%**.
- **¿La mejora fue consistente en las 3 ejecuciones?**
  - Sí, la mejora fue muy consistente. En todas las ejecuciones, la versión optimizada arrojó menores tiempos p95 que la baseline (p95 de 2.95ms, 2.63ms, 2.91ms vs 5.93ms, 4.76ms, 5.12ms).
- **¿Hubo errores?**
  - No, el error rate fue del 0.00% en ambos escenarios, lo que significa que el 100% de las solicitudes se completaron exitosamente con estado HTTP 200.
- **¿Existe evidencia suficiente para recomendar el cambio?**
  - Sí, la mejora promedio del p95 es del **46.33%**, lo cual supera con creces el umbral mínimo del 20% necesario para justificar técnicamente la optimización.

## 6. Conclusión técnica
Se recomienda implementar y mantener de forma definitiva la versión optimizada en producción. Los resultados del benchmark muestran que reduce significativamente la latencia del percentil 95 en un 46.33% sin afectar la estabilidad del servicio ni generar errores bajo el nivel de carga evaluado.

## 7. Evidencias
- **Capturas y gráficos de ejecución:** 
  - Gráfico de comparación: [grafico_p95.png](file:///c:/Users/ThikPad/Desktop/MESE%20CICLO%20VII/CONSTRUCCION%20II/examen/examen-parcial1-const-sw2/docs/UNIDAD%20IV/sesion30/grafico_p95.png)
  - Archivos JSON y TXT generados en `docs/UNIDAD IV/sesion30/resultados_baseline/` y `docs/UNIDAD IV/sesion30/resultados_optimizado/`
- **CSV generado:** [resumen_benchmark.csv](file:///c:/Users/ThikPad/Desktop/MESE%20CICLO%20VII/CONSTRUCCION%20II/examen/examen-parcial1-const-sw2/docs/UNIDAD%20IV/sesion30/resumen_benchmark.csv)
- **Comandos usados:**
  - **Baseline:**
    ```powershell
    k6 run --summary-export "docs/UNIDAD IV/sesion30/resultados_baseline/run1.json" "scripts/s30_benchmark_baseline.js" | Tee-Object -FilePath "docs/UNIDAD IV/sesion30/resultados_baseline/run1.txt"
    k6 run --summary-export "docs/UNIDAD IV/sesion30/resultados_baseline/run2.json" "scripts/s30_benchmark_baseline.js" | Tee-Object -FilePath "docs/UNIDAD IV/sesion30/resultados_baseline/run2.txt"
    k6 run --summary-export "docs/UNIDAD IV/sesion30/resultados_baseline/run3.json" "scripts/s30_benchmark_baseline.js" | Tee-Object -FilePath "docs/UNIDAD IV/sesion30/resultados_baseline/run3.txt"
    ```
  - **Optimizado:**
    ```powershell
    k6 run --summary-export "docs/UNIDAD IV/sesion30/resultados_optimizado/run1.json" "scripts/s30_benchmark_optimizado.js" | Tee-Object -FilePath "docs/UNIDAD IV/sesion30/resultados_optimizado/run1.txt"
    k6 run --summary-export "docs/UNIDAD IV/sesion30/resultados_optimizado/run2.json" "scripts/s30_benchmark_optimizado.js" | Tee-Object -FilePath "docs/UNIDAD IV/sesion30/resultados_optimizado/run2.txt"
    k6 run --summary-export "docs/UNIDAD IV/sesion30/resultados_optimizado/run3.json" "scripts/s30_benchmark_optimizado.js" | Tee-Object -FilePath "docs/UNIDAD IV/sesion30/resultados_optimizado/run3.txt"
    ```
- **Commit y rama:**
  - **Rama:** `feature/sesion29-escenarios-avanzados-tapullima_meselemias`
  - **Commit:** `4bf3e8c809aaa3f4f1f4b80d28e2fd887df3227e`

## 8. Scripts de Benchmark utilizados

### Script de carga para Baseline (`scripts/s30_benchmark_baseline.js`)
```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '15s', target: 20 },
    { duration: '1m', target: 20 },
    { duration: '15s', target: 0 }
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1000']
  }
};

export default function () {
  const res = http.get('http://localhost:8080/benchmark/baseline');
  check(res, {
    'baseline status 200': r => r.status === 200,
    'baseline respuesta no vacia': r => r.body.length > 0
  });
  sleep(1);
}
```

### Script de carga para Optimizado (`scripts/s30_benchmark_optimizado.js`)
```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '15s', target: 20 },
    { duration: '1m', target: 20 },
    { duration: '15s', target: 0 }
  ],
  thresholds: {
    http_req_failed: ['rate<0.05'],
    http_req_duration: ['p(95)<1000']
  }
};

export default function () {
  const res = http.get('http://localhost:8080/benchmark/optimizado');
  check(res, {
    'optimizado status 200': r => r.status === 200,
    'optimizado respuesta no vacia': r => r.body.length > 0
  });
  sleep(1);
}
```
