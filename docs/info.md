
# Reporte técnico - Sesión 28

## Pruebas de carga y análisis de métricas

**Estudiante:** Carlos Rojas  
**Curso:** Construcción de Software II  
**Sesión:** 28  
**Herramienta de carga:** k6  
**Tecnología evaluada:** Spring Boot  

---

## 1. Escenario

### Endpoint evaluado

![alt text](image.png)


smoke

![alt text](image-2.png)

load.js 


con propierties de 120 ms


![alt text](image-4.png)

con propierties de 20 ms

![alt text](image-3.png)


## 3. Hallazgos

- **Síntoma principal:**  
  La versión base presentó una latencia claramente mayor que la versión optimizada.  
  En la versión base, el p95 fue de aproximadamente **137.23 ms**, mientras que en la versión optimizada bajó a aproximadamente **36.80 ms**.

- **Hipótesis de cuello de botella:**  
  El principal cuello de botella se encuentra en el retraso configurado en el backend mediante:


## 4. Decisión

Los thresholds principales se cumplieron en las ejecuciones realizadas:

- Checks mayores al 99 %: **cumplido**, con 100 %.
- Errores HTTP menores al 1 %: **cumplido**, con 0 %.
- p95 menor de 500 ms: **cumplido** en ambas versiones.
  - Base: aproximadamente 137.23 ms.
  - Optimizada: aproximadamente 36.80 ms.


