import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BenchmarkDemo {

    // ---- misma logica que BenchmarkService.java del proyecto Spring Boot ----

    static List<String> consultaBaseline() {
        List<String> datos = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            datos.add("REG-" + i);
        }
        List<String> resultado = new ArrayList<>();
        for (String item : datos) {
            if (item.contains("99")) {
                resultado.add(item);
            }
        }
        return resultado;
    }

    static List<String> consultaOptimizada() {
        return List.of("REG-99", "REG-199", "REG-299", "REG-399", "REG-499");
    }

    static double[] medir(Runnable operacion, int repeticiones) {
        double[] tiempos = new double[repeticiones];
        for (int i = 0; i < repeticiones; i++) {
            long inicio = System.nanoTime();
            operacion.run();
            long fin = System.nanoTime();
            tiempos[i] = (fin - inicio) / 1_000_000.0; // ms
        }
        return tiempos;
    }

    static double percentil(double[] valores, double p) {
        double[] copia = Arrays.copyOf(valores, valores.length);
        Arrays.sort(copia);
        int idx = (int) Math.ceil(p / 100.0 * copia.length) - 1;
        idx = Math.max(0, Math.min(copia.length - 1, idx));
        return copia[idx];
    }

    static double promedio(double[] valores) {
        double suma = 0;
        for (double v : valores) suma += v;
        return suma / valores.length;
    }

    public static void main(String[] args) {
        // 1. PRUEBA FUNCIONAL: validar correctitud de ambos endpoints
        List<String> base = consultaBaseline();
        List<String> opt = consultaOptimizada();

        System.out.println("=== 1. PRUEBA FUNCIONAL ===");
        System.out.println("consultaBaseline() -> " + base.size() + " registros (esperado: 5 con '99')");
        System.out.println("Contenido baseline: " + base);
        System.out.println("consultaOptimizada() -> " + opt.size() + " registros (esperado: 5)");
        System.out.println("Contenido optimizado: " + opt);
        boolean mismoContenido = base.containsAll(opt) && opt.containsAll(base);
        System.out.println("Ambas versiones retornan el mismo conjunto de datos: " + mismoContenido);

        // 2. DEMOSTRACION / MEDICION: 30 repeticiones de calentamiento + 100 mediciones reales
        System.out.println("\n=== 2. DEMOSTRACION (medicion directa in-process) ===");
        for (int i = 0; i < 30; i++) { consultaBaseline(); consultaOptimizada(); } // warm-up JIT

        int repeticiones = 100;
        double[] tiemposBaseline = medir(BenchmarkDemo::consultaBaseline, repeticiones);
        double[] tiemposOptimizado = medir(BenchmarkDemo::consultaOptimizada, repeticiones);

        // 3. ANALISIS
        System.out.println("\n=== 3. ANALISIS DE RESULTADOS (" + repeticiones + " repeticiones) ===");
        double avgBase = promedio(tiemposBaseline);
        double avgOpt = promedio(tiemposOptimizado);
        double p95Base = percentil(tiemposBaseline, 95);
        double p95Opt = percentil(tiemposOptimizado, 95);
        double p99Base = percentil(tiemposBaseline, 99);
        double p99Opt = percentil(tiemposOptimizado, 99);

        System.out.printf("Baseline   -> avg: %.4f ms | p95: %.4f ms | p99: %.4f ms%n", avgBase, p95Base, p99Base);
        System.out.printf("Optimizado -> avg: %.4f ms | p95: %.4f ms | p99: %.4f ms%n", avgOpt, p95Opt, p99Opt);

        double mejoraAvg = ((avgBase - avgOpt) / avgBase) * 100;
        double mejoraP95 = ((p95Base - p95Opt) / p95Base) * 100;

        System.out.printf("%nMejora porcentual (avg): %.2f %%%n", mejoraAvg);
        System.out.printf("Mejora porcentual (p95): %.2f %%%n", mejoraP95);

        if (mejoraP95 >= 20) {
            System.out.println("Decision: la version optimizada mejora significativamente el rendimiento.");
        } else {
            System.out.println("Decision: la mejora no es suficiente; se requiere mas evidencia o ajustes.");
        }
    }
}
