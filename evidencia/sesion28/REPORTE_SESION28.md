# Reporte técnico - Sesión 28

## 1. Escenario
- **Endpoint:** `/carga/productos`
- **Equipo y sistema operativo:** Windows 11 Localhost
- **Perfil/configuración:** Delay Base = 120ms | Delay Optimizado = 20ms
- **Etapas de carga:** 0 -> 10 VUs -> 30 VUs -> 0
- **Umbrales:** Errores < 1%, checks > 99%, p95 < 500 ms

## 2. Resultados

| Versión | p50 | p95 | p99 | RPS | Errores |
| :--- | :---: | :---: | :---: | :---: | :---: |
| **Base (120ms)** | 127.89 ms | 129.52 ms | 134.91 ms | 15.27 req/s | 0.00 % |
| **Optimizada (20ms)** | 32.30 ms | 33.24 ms | 34.61 ms | 16.59 req/s | 0.00 % |

- **Mejora del p95:** **74.34 %** de reducción de la latencia en condiciones de alta concurrencia.

## 3. Hallazgos
- **Síntoma principal:** El tiempo de procesamiento por petición se degrada o mejora de manera directamente proporcional a la latencia configurada artificialmente en el thread pool.
- **Hipótesis de cuello de botella:** Bloqueo síncrono del hilo de trabajo (`Thread.sleep`). Bajo 30 usuarios virtuales, el servidor responde rápido debido a que tiene hilos libres, pero al disminuir el retardo a 20ms, la liberación de hilos es mucho más rápida maximizando la estabilidad del CPU.
- **Evidencia que la sustenta:** Los archivos `resumen-base.json` y `resumen-optimizado.json` evidencian una caída de p95 de más de 96 ms.

## 4. Decisión
Los thresholds de checks, errores y tiempos de respuesta se cumplieron perfectamente en ambas pasadas. Se recomienda migrar la configuración de producción al parámetro optimizado de menor delay para reducir drásticamente los tiempos percibidos.
