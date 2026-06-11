package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;

import java.util.*;

    @Service
    public class HerramientaService {
        public List<String> listar (){
            return List.of("Git", "GitHub");

        }

    }
