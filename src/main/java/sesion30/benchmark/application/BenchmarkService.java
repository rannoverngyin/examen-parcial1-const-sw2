package sesion30.benchmark.application;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BenchmarkService {

    public List<String> consultaBaseline() {

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

    public List<String> consultaOptimizada() {

        return List.of(
                "REG-99",
                "REG-199",
                "REG-299",
                "REG-399",
                "REG-499"
        );
    }
}