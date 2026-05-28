package pe.unas.demoapi.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class InvestigadorService {

    private final List<String> investigadores = new ArrayList<>();

    public InvestigadorService() {
        investigadores.add("Yanac");
        investigadores.add("Ulises");
    }


    public List<String> listarInvestigadores(){
        return investigadores;
    }


    public int totalInvestigadores(){
        return investigadores.size();
    }

    

}
