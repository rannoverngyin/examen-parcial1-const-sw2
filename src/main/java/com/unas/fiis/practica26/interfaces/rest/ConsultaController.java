package com.unas.fiis.practica26.interfaces.rest;

import com.unas.fiis.practica26.application.dto.ReporteOptimizacion;
import com.unas.fiis.practica26.application.service.InicializarBaseDatosUseCase;
import com.unas.fiis.practica26.application.service.OptimizarConsultasUseCase;
import com.unas.fiis.practica26.domain.model.Medicion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    private final InicializarBaseDatosUseCase inicializarUseCase;
    private final OptimizarConsultasUseCase optimizarUseCase;

    public ConsultaController(InicializarBaseDatosUseCase inicializarUseCase,
                              OptimizarConsultasUseCase optimizarUseCase) {
        this.inicializarUseCase = inicializarUseCase;
        this.optimizarUseCase = optimizarUseCase;
    }

    @GetMapping("/inicializar")
    public ResponseEntity<Map<String, String>> inicializar() {
        String mensaje = inicializarUseCase.ejecutar();
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @GetMapping("/medir-antes")
    public ResponseEntity<List<Medicion>> medirAntes() {
        List<Medicion> resultados = optimizarUseCase.medirAntes();
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/optimizar")
    public ResponseEntity<ReporteOptimizacion> optimizar() {
        ReporteOptimizacion reporte = optimizarUseCase.optimizar();
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/medir-despues")
    public ResponseEntity<List<Medicion>> medirDespues() {
        List<Medicion> resultados = optimizarUseCase.medirDespues();
        return ResponseEntity.ok(resultados);
    }

}
