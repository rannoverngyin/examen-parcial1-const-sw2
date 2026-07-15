package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BenchmarkService {

    private static final int TOTAL_REGISTROS = 5000;

    /*
     * La respuesta optimizada se calcula una sola vez.
     * Después se reutiliza en cada solicitud.
     */
    private final List<String> resultadoOptimizado;

    public BenchmarkService() {
        this.resultadoOptimizado =
                List.copyOf(generarResultado());
    }

    /*
     * Versión baseline:
     * crea 5000 registros y los filtra nuevamente
     * en cada solicitud.
     */
    public List<String> consultaBaseline() {
        List<String> datos =
                new ArrayList<>(TOTAL_REGISTROS);

        for (int i = 0; i < TOTAL_REGISTROS; i++) {
            datos.add("REG-" + i);
        }

        List<String> resultado =
                new ArrayList<>();

        for (String item : datos) {
            if (item.contains("99")) {
                resultado.add(item);
            }
        }

        return resultado;
    }

    /*
     * Versión optimizada:
     * devuelve la respuesta inmutable ya preparada.
     */
    public List<String> consultaOptimizada() {
        return resultadoOptimizado;
    }

    private List<String> generarResultado() {
        List<String> resultado =
                new ArrayList<>();

        for (int i = 0; i < TOTAL_REGISTROS; i++) {
            String item = "REG-" + i;

            if (item.contains("99")) {
                resultado.add(item);
            }
        }

        return resultado;
    }
}