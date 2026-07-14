package com.pruebas_de.rendimiento.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RendimientoService {

    private final List<String> productos = List.of(
            "Laptop", "Mouse", "Teclado", "Monitor", "Impresora"
    );

    private final List<String> productosOptimizados = productos.stream()
            .map(String::toUpperCase)
            .toList();

    public List<String> listarBase(Integer cantidad) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<String> resultado = productos.stream()
                .map(String::toUpperCase)
                .toList();

        if (cantidad != null && cantidad > 0) {
            return expandirLista(resultado, cantidad);
        }
        return resultado;
    }

    public List<String> listarOptimizado(Integer cantidad) {
        if (cantidad != null && cantidad > 0) {
            return expandirLista(productosOptimizados, cantidad);
        }
        return productosOptimizados;
    }

    private List<String> expandirLista(List<String> base, int cantidad) {
        List<String> extendida = new ArrayList<>(cantidad);
        for (int i = 0; i < cantidad; i++) {
            extendida.add(base.get(i % base.size()) + " #" + (i + 1));
        }
        return extendida;
    }
}
