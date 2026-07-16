# Reporte técnico - Sesión 27

## 1. Escenario
- **Endpoint Base:** `/rendimiento/productos/base`
- **Endpoint Optimizado:** `/rendimiento/productos/optimizado`
- **Repeticiones:** 5 de calentamiento + 30 mediciones por endpoint
- **Equipo/entorno:** Windows 11 - Localhost puerto 8082
- **Commit evaluado:** Desarrollo local S27

## 2. Hipótesis inicial
La versión base presenta un cuello de botella artificial introducido mediante `Thread.sleep(200)` que bloquea el hilo de ejecución principal durante 200 ms por cada petición, además de repetir el procesamiento de strings innecesariamente. La versión optimizada solucionará esto precalculando la lista inmutable en memoria.

## 3. Resultados

| Versión | Promedio | Mediana | p95 | Errores |
| :--- | :---: | :---: | :---: | :---: |
| **Base** | 215.02 ms | 215.37 ms | 222.27 ms | 0 |
| **Optimizada** | 13.41 ms | 13.69 ms | 14.46 ms | 0 |

- **Mejora porcentual del p95:** 93.50 % de reducción del tiempo de respuesta.

## 4. Conclusión
El cuello de botella principal residía enteramente en el backend debido al retraso simulado en el hilo de la JVM. Al eliminar este procesamiento ineficiente y preparar el recurso estático en el servicio, el tiempo del percentil 95 disminuyó drásticamente (de 222.27 ms a 14.46 ms) sin generar errores funcionales, cumpliendo con creces el criterio de éxito.
