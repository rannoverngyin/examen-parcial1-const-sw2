# Informe Técnico: Configuración Dinámica y Perfiles de Entorno en Spring Boot

[cite_start]**Curso:** Construcción de Software II [cite: 3]  
[cite_start]**Unidad:** Unidad III: Variabilidad y configuración del software [cite: 3]  
[cite_start]**Institución:** Universidad Nacional Agraria de la Selva (UNAS) [cite: 14]  
[cite_start]**Facultad:** Facultad de Ingeniería en Informática y Sistemas (FIIS) [cite: 14]  

---

## 1. Introducción
[cite_start]El presente documento detalla la implementación de variabilidad de software y externalización de configuraciones en una API REST construida con Spring Boot, siguiendo principios estructurales de *Clean Architecture*[cite: 3, 5]. [cite_start]El objetivo principal consiste en lograr que el artefacto de software generado sea agnóstico al ecosistema de despliegue, permitiendo alterar su comportamiento en tiempo de ejecución sin requerir una nueva compilación o modificación directa del código fuente[cite: 12, 102].

---

## [cite_start]2. Configuración de Entornos (`src/main/resources`) [cite: 25]

[cite_start]Para separar los entornos de ejecución, se crearon archivos de propiedades específicos siguiendo la convención de nomenclatura de Spring Boot[cite: 2, 16]:

### 2.1. [cite_start]Perfil de Desarrollo (`application-dev.properties`) 
[cite_start]Configuración local predeterminada para el espacio de trabajo del desarrollador:
```properties
app.entorno=dev
app.mensaje=Entorno de desarrollo FIIS
app.version=1.0-DEV
app.soporte=soporte-dev@unas.edu.pe