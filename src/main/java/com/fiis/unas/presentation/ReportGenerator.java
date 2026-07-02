package com.fiis.unas.presentation;

import com.fiis.unas.domain.vo.BenchmarkResult;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class ReportGenerator {

    public void generateReport(List<BenchmarkResult> results, List<String> explainPlan) {
        String filePath = "docs/REPORTE_SESION25.md";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("# Reporte Sesión 25 — ORM vs SQL directo en Spring Boot (Clean Architecture)");
            pw.println();
            pw.println("## 1. Objetivo");
            pw.println("Comparar consultas ORM (Spring Data JPA) y SQL directo (JDBC) en un catálogo académico, ");
            pw.println("identificando diferencias de rendimiento, mantenibilidad y control sobre las consultas.");
            pw.println();
            pw.println("## 2. Consultas evaluadas");
            pw.println("- Cursos por ciclo (filtrada por ciclo = 7)");
            pw.println("- Conteo por docente (Mg. Yanac)");
            pw.println();
            pw.println("## 3. Resultados");
            pw.println("| Consulta | Filas | Tiempo (ms) | Observación |");
            pw.println("|---|---:|---:|---|");

            for (BenchmarkResult r : results) {
                String obs = observacion(r.getConsulta(), r.getTiempoMs());
                pw.printf("| %s | %d | %.4f | %s |%n",
                        r.getConsulta(), r.getFilas(), r.getTiempoMs(), obs);
            }

            pw.println();
            pw.println("## 4. Interpretación");
            pw.println("ORM (Spring Data JPA) ofrece mayor productividad y mantenibilidad: ");
            pw.println("las consultas se escriben en JPQL o Criteria API, el mapeo objeto-relacional es automático ");
            pw.println("y los cambios de esquema requieren menos modificaciones en código. ");
            pw.println("SQL directo (JDBC) ofrece mayor control sobre la consulta y, en general, ");
            pw.println("mejor rendimiento en operaciones masivas o consultas altamente optimizadas, ");
            pw.println("ya que no tiene la sobrecarga del mapeo ORM ni la generación automática de SQL.");
            pw.println();
            pw.println("### ¿Cuándo conviene ORM?");
            pw.println("- Aplicaciones con dominio complejo y muchas relaciones entre entidades.");
            pw.println("- Equipos que priorizan mantenibilidad y rapidez de desarrollo.");
            pw.println("- Cuando se necesita cambiar de motor de base de datos con facilidad.");
            pw.println();
            pw.println("### ¿Cuándo conviene SQL directo?");
            pw.println("- Consultas con alto volumen de datos donde cada milisegundo cuenta.");
            pw.println("- Reportes complejos con joins y agregaciones muy específicas.");
            pw.println("- Operaciones batch que requieren control fino del SQL generado.");
            pw.println();
            pw.println("## 5. Plan de ejecución (EXPLAIN)");
            pw.println("```");
            for (String line : explainPlan) {
                pw.println(line);
            }
            pw.println("```");
            pw.println();
            pw.println("## 6. Impacto del índice");
            pw.println("El índice en la columna `docente` y `ciclo` permite búsquedas por índice (INDEX SCAN) ");
            pw.println("en lugar de escaneo completo de tabla (TABLE SCAN). Esto reduce drásticamente ");
            pw.println("el tiempo de consulta filtrada, especialmente al crecer el volumen de datos.");
            pw.println();
            pw.println("## 7. Reflexión técnica");
            pw.println("- **Ventaja ORM en mantenibilidad**: el código queda desacoplado del SQL nativo; ");
            pw.println("  los cambios de esquema se gestionan a nivel de entidades.");
            pw.println("- **SQL directo más conveniente**: en consultas con alta demanda de rendimiento ");
            pw.println("  o cuando se requiere sintaxis específica del motor no soportada por el ORM.");
            pw.println("- **Impacto del índice**: reduce el costo de las búsquedas filtradas de O(n) a O(log n).");
            pw.println("- **COUNT eficiente**: COUNT en SQL se ejecuta a nivel motor sin transferir filas; ");
            pw.println("  contar en Java requiere traer todos los registros a memoria.");
            pw.println("- **Riesgo de inyección SQL**: concatenar texto del usuario en SQL directo permite ");
            pw.println("  inyección SQL. ORM con parámetros tipados (PreparedStatement / JPQL parametrizado) mitiga este riesgo.");
            pw.println();
            pw.println("## 8. Evidencias");
            pw.println("- Código fuente organizado en Clean Architecture (domain, application, infrastructure, presentation)");
            pw.println("- Proyecto Spring Boot con Spring Data JPA y JDBC");
            pw.println("- Base de datos H2 embebida generada con 5000 registros");
            pw.println("- Este reporte generado automáticamente");

            System.out.println("Reporte generado: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("Error al generar reporte", e);
        }
    }

    private String observacion(String consulta, double tiempoMs) {
        if (consulta.startsWith("ORM")) {
            return "Incluye overhead de mapeo objeto-relacional";
        }
        return "SQL directo, sin overhead de mapeo";
    }
}
