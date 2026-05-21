# Reporte JaCoCo - Cobertura de Pruebas Unitarias e Integración

## 1. Interpretación de Colores en JaCoCo

El reporte de JaCoCo utiliza un sistema de codificación por colores para visualizar el estado de cobertura del código:

- **Verde**: Código cubierto por pruebas unitarias e integración. Indica que las líneas fueron ejecutadas durante la ejecución de los tests.
- **Rojo**: Código no ejecutado por pruebas. Líneas que no fueron ejecutadas durante ninguna prueba, indicando falta de cobertura.
- **Amarillo**: Ramas de código parcialmente cubiertas. Se utiliza principalmente en condicionales (if/else, switch) donde algunas rutas lógicas fueron probadas pero no todas.

---

## 2. Resumen General de Cobertura

| Métrica | Valor | Evaluación |
|---------|-------|------------|
| Cobertura de Instrucciones | 87% (207/237) | Excelente |
| Instrucciones Faltantes | 30 de 237 | 13% sin cobertura |
| Cobertura de Ramas | 70% (14/20) | Buena, necesita mejora |
| Ramas Faltantes | 6 de 20 | 30% de ramas incompletas |
| Total de Clases | 11 | - |
| Total de Métodos | 33 | - |
| Total de Líneas Ejecutables | 67 | - |

**Interpretación**: El proyecto mantiene una cobertura general del 87%, lo que indica que la mayoría del código ha sido probado. Sin embargo, la cobertura de ramas (70%) sugiere que existen caminos lógicos condicionales que no han sido completamente ejercitados en las pruebas.

---

## 3. Análisis Detallado por Paquete

### 3.1 Paquete: pe.unas.demoapi.application

Este paquete contiene la lógica de negocio y servicios de aplicación.

| Métrica | Valor | Detalle |
|---------|-------|--------|
| Cobertura General | 92% | Excelente |
| Instrucciones Cubiertas | 156 | Muy buena cobertura |
| Instrucciones Faltantes | 12 | 8% sin cobertura |
| Cobertura de Ramas | 70% (14/20) | Parcialmente cubierto |
| Ramas Faltantes | 6 | Condicionales no probados |
| Clases Testeadas | 6 | Todas incluidas |
| Métodos Testeados | 20 | 100% testeados |
| Líneas Cubiertas | 43 | - |
| Líneas Faltantes | 4 | Casos extremos no cubiertos |

**Análisis**: Este paquete presenta la mejor cobertura del proyecto (92%). La lógica de negocio está bien respaldada por pruebas. Las ramas incompletas (70%) indican que existen condiciones condicionales (if/else) cuyos casos no han sido completamente probados. Esto puede incluir manejo de excepciones, validaciones de entrada o casos límite específicos que no fueron considerados en los tests actuales.

**Líneas de código sin cobertura típicamente incluyen**:
- Excepciones específicas no lanzadas
- Validaciones con múltiples condiciones no combinadas
- Caminos alternativos en lógica condicional compleja

---

### 3.2 Paquete: pe.unas.demoapi.presentation

Este paquete contiene los controladores REST y la capa de presentación de la aplicación.

| Métrica | Valor | Detalle |
|---------|-------|--------|
| Cobertura General | 78% | Buena |
| Instrucciones Cubiertas | 48 | Cobertura moderada |
| Instrucciones Faltantes | 13 | 21% sin cobertura |
| Cobertura de Ramas | n/a | Sin análisis de ramas |
| Clases (Controladores) | 4 | Parcialmente testeados |
| Métodos Testeados | 11 | 2 métodos sin pruebas |
| Líneas Cubiertas | 21 | - |
| Líneas Faltantes | 3 | - |

**Análisis**: La capa de presentación muestra una cobertura del 78%, siendo la segunda en importancia. Los controladores REST no están completamente probados. Los elementos faltantes típicamente incluyen:

- Manejo de excepciones y errores en los endpoints
- Validación de entrada de datos incompleta
- Algunos métodos HTTP (GET, POST, PUT, DELETE) sin pruebas
- Transformación de datos a respuestas HTTP

**Recomendación**: Se recomienda ampliar los tests de integración para asegurar que todos los endpoints son validados.

---

### 3.3 Paquete: pe.unas.demoapi

Este paquete contiene la clase principal de arranque de la aplicación Spring Boot.

| Métrica | Valor | Detalle |
|---------|-------|--------|
| Cobertura General | 37% | Baja |
| Instrucciones Cubiertas | 3 | Mínima cobertura |
| Instrucciones Faltantes | 5 | 63% sin cobertura |
| Ramas | n/a | Sin condicionales |
| Clases | 1 | ExamenParcial1ConstSw2Application |
| Métodos | 2 | 1 sin prueba |
| Líneas Cubiertas | 3 | - |
| Líneas Faltantes | 2 | - |

**Análisis**: La baja cobertura en este paquete es normal y esperada. La clase principal de la aplicación (ExamenParcial1ConstSw2Application) típicamente contiene solo el método main() que inicializa el contexto de Spring. Estas clases de arranque generalmente no se prueban de forma exhaustiva en tests unitarios convencionales.

---

## 4. Identificación de Áreas Críticas

### 4.1 Ramas No Cubiertas

El 30% de ramas sin cobertura (6 de 20) indica que existen decisiones condicionales cuyas rutas no han sido completamente ejercitadas:

- Condicionales if/else con múltiples caminos lógicos
- Expresiones ternarias no evaluadas en todos los casos
- Switch statements con casos no probados
- Especialmente crítico en el paquete pe.unas.demoapi.application

### 4.2 Métodos sin Cobertura en la Capa de Presentación

Se han identificado 2 métodos en los controladores REST sin pruebas unitarias:

- Endpoints específicos no testeados
- Manejo de errores HTTP incompleto
- Casos de error en la transformación de datos no validados

### 4.3 Instrucciones No Ejecutadas

30 de 237 instrucciones no fueron ejecutadas durante las pruebas:

- Código potencialmente muerto o no alcanzable
- Excepciones específicas no lanzadas
- Caminos de error no probados
- Casos límite no considerados

---

## 5. Recomendaciones Estratégicas de Mejora

### 5.1 Objetivo Inmediato: 80% de Cobertura de Ramas

Para mejorar la cobertura de ramas a un mínimo del 80%:

1. Revisar cada condicional en pe.unas.demoapi.application
2. Crear pruebas que evalúen explícitamente ambas condiciones (verdadero y falso)
3. Incluir pruebas para combinaciones de condiciones complejas
4. Priorizar validaciones de entrada que incluyan múltiples caminos

### 5.2 Objetivo Secundario: Cobertura Completa de Controladores

Para lograr cobertura completa en la capa de presentación:

1. Desarrollar tests de integración para los 2 métodos sin cobertura
2. Incluir casos de prueba para:
   - Solicitudes válidas (HTTP 200)
   - Errores de validación (HTTP 400)
   - Errores de autorización (HTTP 403)
   - Recursos no encontrados (HTTP 404)
   - Errores del servidor (HTTP 500)
3. Validar transformación de datos en respuestas

### 5.3 Objetivo Final: 90%+ de Cobertura General

Para alcanzar una cobertura superior al 90%:

1. Ejecutar herramientas de análisis de cobertura regularmente
2. Identificar código muerto o no alcanzable
3. Documentar intenciones detrás del código no cubierto
4. Establecer estándares de cobertura mínima por paquete
5. Incorporar verificaciones de cobertura en el pipeline CI/CD

---

## 6. Información Técnica del Reporte

| Aspecto | Descripción |
|--------|-------------|
| Herramienta | JaCoCo (Java Code Coverage) |
| Versión | 0.8.12.202403310830 |
| Comando Ejecutado | ./mvnw clean test |
| Ubicación del Reporte | target/site/jacoco/index.html |
| Formato | HTML, XML y CSV |

JaCoCo es una herramienta de cobertura de código independiente que proporciona análisis detallado de cuáles líneas de código fueron ejecutadas durante las pruebas unitarias e integración.

---

## 7. Conclusiones

El proyecto mantiene un nivel de cobertura de pruebas aceptable (87%), con mayor fortaleza en la capa de lógica de negocio (92% en application). Sin embargo, existen oportunidades de mejora significativas:

1. La cobertura de ramas puede incrementarse mediante pruebas más exhaustivas de condicionales
2. La capa de presentación requiere pruebas adicionales de integración
3. Se recomienda establecer objetivos de cobertura mínima del 85% por paquete

Con estas mejoras, el proyecto alcanzaría una cobertura robusta que garantizaría mayor confiabilidad y mantenibilidad del código.

