package com.gonzalo.api_cs2.application;

import org.springframework.stereotype.Service;
import com.gonzalo.api_cs2.domain.ActivoDiagnostico;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosticoTIService {
    public List<ActivoDiagnostico> listarActivos(){
        return List.of(
            new ActivoDiagnostico("021", "Sistema de Red", "Red", "OPERATIVO"),
            new ActivoDiagnostico("022", "Sistema web UNAS", "Software", "OPERATIVO"),
            new ActivoDiagnostico("023", "Red Empresarial 2008", "Red", "OBSOLETO"),
            new ActivoDiagnostico("024", "sistema facturacion UNAS 2001", "Software", "OBSOLETO"),
            new ActivoDiagnostico("025", "Sistema de Facturacion UNAS 2026", "Software", "EN_VALIDACION")
        );
    }
        public long contarPorEstado (String estado){
            return listarActivos().stream()
            .filter(a -> a.getEstado().equalsIgnoreCase(estado))
            .count();
        }
        public Map<String, Object> resumen(){
            List<ActivoDiagnostico> activo = listarActivos();
            return Map.of(
                "totalActivos", activo.size(),
                "operativos", contarPorEstado("OPERATIVO"),
                "obsoletos", contarPorEstado("OBSOLETO"),
                "enValidacion", contarPorEstado("EN_VALIDACION"),
                "estado", "OK"
            );
        }
    }

