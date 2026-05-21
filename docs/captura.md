18. Evidencia de entrega

•	Captura de terminal con BUILD SUCCESS.
![alt text](image-2.png)
•	Captura de target/site/jacoco/index.html.
![alt text](image-4.png)
•	Captura de pruebas agregadas en el editor.
![alt text](image-3.png)
•	Commit en GitHub o Pull Request.

•	Breve interpretación: qué método tenía menor cobertura y cómo se mejoró.

Estado Inicial (Baja Cobertura): Al principio, solo se probaba el "camino feliz" de cobertura alta. JaCoCo marcaba en rojo los flujos de cobertura MEDIA, BAJA y en amarillo las excepciones no auditadas. El método esAceptable no tenía ninguna prueba.

Estrategia Aplicada: Se expandió el banco de pruebas con JUnit 5 usando particiones de equivalencia y análisis de límites. Se añadieron aserciones para los retornos esperados (assertEquals) y se validaron los flujos de error y excepciones (assertThrows).  

Impacto y Calidad: Tras ejecutar Maven, el proyecto superó con éxito el Quality Gate mínimo del 70% configurado en el pom.xml. Se alcanzó un 100% de cobertura en instrucciones y ramas (Branch Coverage), garantizando que todo el código está protegido contra futuros errores.  