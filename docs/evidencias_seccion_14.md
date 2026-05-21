# 18. Evidencia de entrega

## 1. Captura de terminal con BUILD SUCCESS

![alt text](image.png)

---

## 2. Captura de `target/site/jacoco/index.html`

![alt text](image-1.png)

---

## 3. Captura de pruebas agregadas en el editor

![alt text](image-2.png)

---

## 4. Commit en GitHub o Pull Request

![Commit GitHub](ruta-de-tu-imagen.png)

---

## 5. Breve interpretación

El método que tenía menor cobertura inicialmente fue `clasificarCobertura()`, porque solo se probaba el caso de cobertura alta (`ALTA`).

Para mejorar la cobertura se agregaron pruebas para:

- Cobertura media (`MEDIA`)
- Cobertura baja (`BAJA`)
- Valores inválidos menores a 0
- Valores inválidos mayores a 100

Después de agregar estas pruebas, el reporte de JaCoCo mostró una mejora significativa en la cobertura de líneas, métodos y ramas, reduciendo el código no cubierto.