package pe.unas.demoapi.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.unas.demoapi.application.HerramientaService;

import java.util.List;

    @RestController
    public class HerramientaController{
        private final HerramientaService service;
        public HerramientaController(HerramientaService service){
            this.service = service;
        }
        @GetMapping("/herramientas")
        public List<String> listar (){
            return service.listar();
        }
    }
