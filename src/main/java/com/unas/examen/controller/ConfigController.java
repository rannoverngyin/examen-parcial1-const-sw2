package com.unas.examen.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("aplicacion", "examen-parcial1-const-sw2");
        info.put("version", "1.0.0");
        info.put("perfil_activo", activeProfile);
        info.put("java_version", System.getProperty("java.version"));
        info.put("curso", "Construccion de Software II - UNAS");
        info.put("sesion", "Sesion 20 - Contenedores y Despliegue");
        return ResponseEntity.ok(info);
    }
}
