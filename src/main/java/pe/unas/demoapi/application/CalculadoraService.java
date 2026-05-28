package pe.unas.demoapi.application;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculadoraService {
    
    public int sumaar(int a, int b) {
        return a + b;
    }
    public int restar(int a, int b) {
        return a - b;
    }
    public int multiplicar(int a, int b) {
        return a * b;
    }
    public int dividir(int a, int b) {
        return a / b;
    }

    
}
