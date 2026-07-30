package com.examen.medio.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.medio.application.EnsayoService;
import com.examen.medio.domain.Ensayo;

@RestController
@RequestMapping("/api/ensayo")
public class EnsayoController {
    private final EnsayoService servicio;

    public EnsayoController(EnsayoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/consulta")
    public List<Ensayo> consultar() {
        return servicio.listarEnsayos();
    }
}
